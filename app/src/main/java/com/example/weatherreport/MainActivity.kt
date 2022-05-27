package com.example.weatherreport

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar
import kotlin.concurrent.thread
import com.example.weatherreport.network.types.FourDayForecast
import com.example.weatherreport.network.types.TwentyFourHourForecast
import com.example.weatherreport.network.parsers.FourDayParser
import com.example.weatherreport.network.parsers.TwentyFourHourParser
import com.example.weatherreport.network.NetworkModule
import com.example.weatherreport.network.WeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate


class MainActivity : AppCompatActivity() {
    private var btnPressed = true
    lateinit var frgRegionInfo: RegionInformation
    lateinit var frgSingaporeMap: SingaporeMap
    lateinit var txtAppTitle: TextView
    lateinit var txtRegion: TextView
    lateinit var txtTemp: TextView
    lateinit var txtWeatherCondition: TextView
    lateinit var imgWeatherCondition: ImageView
    private lateinit var twentyFourHourParser: TwentyFourHourParser
    private lateinit var fourDayParser: FourDayParser
    private lateinit var viewModel: RegionInfoViewModel
    lateinit var btnShowMap: Button
    private val DEGREE = "°"
    private val THUNDERY = 0
    private val RAINY = 1
    private val FAIR_MOON = 2
    private val FAIR_SUN = 3
    private val CLOUDY = 4

    // API
    private val weatherAPI: WeatherAPI = NetworkModule.weatherAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeNecessaryElements()
        selectBackgroundAndTextColors() //change background and text colors accordingly
        onClickButtonChangeFragmentDisplay() //set button oon click listener handler
    }

    /**
     * Helper function to initialize all the necessary elements such as fragments, view model
     * and button in the main activity.
     */
    private fun initializeNecessaryElements() {
        frgRegionInfo = RegionInformation()
        frgSingaporeMap = SingaporeMap()
        viewModel = ViewModelProvider(this).get(RegionInfoViewModel::class.java)
        btnShowMap = findViewById(R.id.btnShowMap)
        initializeParserAPI()
    }

    private fun initializeParserAPI() {
        getTwentyFourHourData()
        getFourDayData()
    }

    /**
     * This function will display different activity backgrounds according to the current time it
     * detected. In addition, it will change some text colors to either BLACK or WHITE according to
     * the current time provided.
     */
    private fun selectBackgroundAndTextColors() {
        val currTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 10
        val mainLayout = findViewById<ConstraintLayout>(R.id.mainLayout)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)
        if (currTime >= 18 || currTime <= 6) {
            mainLayout.setBackgroundResource(R.drawable.night_time_background)
            constraintLayout.background = ResourcesCompat.getDrawable(this.resources, R.drawable.white_rectangle_border, null)
            txtAppTitle = findViewById(R.id.txtAppTitle)
            txtAppTitle.setTextColor(Color.parseColor("#FFFFFF"))
            txtRegion = findViewById(R.id.txtRegion)
            txtRegion.setTextColor(Color.parseColor("#FFFFFF"))
            txtTemp = findViewById(R.id.txtTemperature)
            txtTemp.setTextColor(Color.parseColor("#FFFFFF"))
            txtWeatherCondition = findViewById(R.id.txtWeatherCondition)
            txtWeatherCondition.setTextColor(Color.parseColor("#FFFFFF"))
            val line = findViewById<View>(R.id.line1)
            line.setBackgroundColor(Color.parseColor("#FFFFFF"))
        } else {
            mainLayout.setBackgroundResource(R.drawable.daytime_background)
            constraintLayout.background = ResourcesCompat.getDrawable(this.resources, R.drawable.black_rectangle_border, null)
            txtAppTitle = findViewById(R.id.txtAppTitle)
            txtAppTitle.setTextColor(Color.parseColor("#000000"))
            txtRegion = findViewById(R.id.txtRegion)
            txtRegion.setTextColor(Color.parseColor("#000000"))
            txtTemp = findViewById(R.id.txtTemperature)
            txtTemp.setTextColor(Color.parseColor("#000000"))
            txtWeatherCondition = findViewById(R.id.txtWeatherCondition)
            txtWeatherCondition.setTextColor(Color.parseColor("#000000"))
        }
        imgWeatherCondition = findViewById(R.id.imgWeatherCondition)
    }

    /**
     * This function handles the case when the "SHOW REGIONS" button or "HIDE REGIONS" button is
     * clicked. On clicking "SHOW REGIONS" button, a clickable Singapore map is shown, replacing
     * the weather information. On clicking "HIDE REGIONS" button, the weather information of
     * previously selected region will be shown, replacing the map.
     */
    private fun onClickButtonChangeFragmentDisplay() {
        supportFragmentManager.beginTransaction().add(R.id.fvRegionInfo, frgRegionInfo).commit()
        btnShowMap.setOnClickListener {
            if (btnPressed) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fvRegionInfo, frgSingaporeMap).commit()
                btnShowMap.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    bottomToTop = R.id.fvRegionInfo
                }
                btnShowMap.text = getString(R.string.hide_regions)
            } else {
                supportFragmentManager.beginTransaction().replace(R.id.fvRegionInfo, frgRegionInfo)
                    .commit()
                btnShowMap.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    bottomToTop = ConstraintLayout.LayoutParams.UNSET
                }
                btnShowMap.text = getString(R.string.show_regions)
            }
            btnPressed = !btnPressed
        }
    }

    /*******************************
     * Animation related function  *
     *******************************/

    private fun animateWeatherIcons() {
        //Animation for weather image
        thread(start = true) {
            if (imgWeatherCondition.tag == FAIR_SUN) {
                val rotation = AnimationUtils.loadAnimation(applicationContext, R.anim.rotation)
                rotation.fillAfter = true
                imgWeatherCondition.startAnimation(rotation)
            }
            if (imgWeatherCondition.tag == FAIR_MOON) {
                val rocking = AnimationUtils.loadAnimation(applicationContext, R.anim.rocking)
                rocking.fillAfter = true
                rocking.fillBefore = true
                imgWeatherCondition.startAnimation(rocking)
            }
            if (imgWeatherCondition.tag == CLOUDY) {
                val bouncing = AnimationUtils.loadAnimation(applicationContext, R.anim.bouncing)
                bouncing.fillAfter = true
                bouncing.fillBefore = true
                imgWeatherCondition.startAnimation(bouncing)
            }
        }
    }

    /**********************************
     * API related functions          *
     **********************************/

    private fun getTwentyFourHourData() {
        val date: LocalDate = LocalDate.now()
        val dateMidnightTime: String = date.toString() + "T00:00:00"
        // Asynchronous network call through enqueue
        weatherAPI.getTwentyFourHourForecast(dateMidnightTime)
            .enqueue(object : Callback<TwentyFourHourForecast.Response> {
                override fun onResponse(
                    call: Call<TwentyFourHourForecast.Response>,
                    response: Response<TwentyFourHourForecast.Response>
                ) {
                    val result = response.body() ?: return // null check
                    twentyFourHourParser = TwentyFourHourParser(result)
                    txtRegion.text = getString(R.string.country_name)
                    txtTemp.text =
                        twentyFourHourParser.getGeneralAvgTemperature().toString() + DEGREE
                    txtWeatherCondition.text =
                        determineCurrentForecastDescription(twentyFourHourParser.getGeneralForecast())
                    determineCurrentWeatherIcon(twentyFourHourParser.getGeneralForecast())
                    viewModel.txtDate = twentyFourHourParser.getCurrentDate()
                    viewModel.txtMorningWeatherCondition =
                        twentyFourHourParser.getForecastCategory(twentyFourHourParser.getMorningEastForecast())
                            .toString()
                    viewModel.txtAfternoonWeatherCondition =
                        twentyFourHourParser.getForecastCategory(twentyFourHourParser.getNoonEastForecast())
                            .toString()
                    viewModel.txtEveningWeatherCondition =
                        twentyFourHourParser.getForecastCategory(twentyFourHourParser.getEveningEastForecast())
                            .toString()
                    viewModel.txtNightWeatherCondition =
                        twentyFourHourParser.getForecastCategory(twentyFourHourParser.getNightEastForecast())
                            .toString()
                    determineMorningWeatherIcon(twentyFourHourParser.getMorningEastForecast())
                    determineAfternoonWeatherIcon(twentyFourHourParser.getNoonEastForecast())
                    determineEveningWeatherIcon(twentyFourHourParser.getEveningEastForecast())
                    determineNightWeatherIcon(twentyFourHourParser.getNightEastForecast())
                    frgRegionInfo.renderingUiFromViewModel()
                    frgRegionInfo.createAnimationForWeatherIcons() //create animation for fragment
                    supportFragmentManager.beginTransaction().replace(R.id.fvRegionInfo, frgRegionInfo).commitAllowingStateLoss()
                    animateWeatherIcons() //create animation after the layout is shown to prevent asynchronous call
                }

                override fun onFailure(call: Call<TwentyFourHourForecast.Response>, t: Throwable) {
                    Log.e("MainActivity", "getTwentyFourHourData onFailure()", t)
                }
            })
    }

    private fun getFourDayData() {
        // Asynchronous network call through enqueue
        weatherAPI.getFourDayForecast()
            .enqueue(object : Callback<FourDayForecast.Response> {
                override fun onResponse(
                    call: Call<FourDayForecast.Response>,
                    response: Response<FourDayForecast.Response>
                ) {
                    val result = response.body() ?: return // null check
                    fourDayParser = FourDayParser(result)
                    viewModel.txtNext1Date = fourDayParser.getRelativeDate(0)
                    viewModel.txtNext2Date = fourDayParser.getRelativeDate(1)
                    viewModel.txtNext3Date = fourDayParser.getRelativeDate(2)
                    viewModel.txtNext4Date = fourDayParser.getRelativeDate(3)
                    viewModel.imgNext1DateWeatherCondition =
                        determineNextDateWeatherIcon(fourDayParser.getGeneralForecast(0))
                    viewModel.imgNext1DateTag = determineNextDateWeatherIconTag(fourDayParser.getGeneralForecast(0))
                    viewModel.txtNext1DateTemp = fourDayParser.getGeneralAvgTemperature(0).toString() + DEGREE
                    viewModel.imgNext2DateWeatherCondition =
                        determineNextDateWeatherIcon(fourDayParser.getGeneralForecast(1))
                    viewModel.imgNext2DateTag = determineNextDateWeatherIconTag(fourDayParser.getGeneralForecast(1))
                    viewModel.txtNext2DateTemp = fourDayParser.getGeneralAvgTemperature(1).toString() + DEGREE
                    viewModel.imgNext3DateWeatherCondition =
                        determineNextDateWeatherIcon(fourDayParser.getGeneralForecast(2))
                    viewModel.imgNext3DateTag = determineNextDateWeatherIconTag(fourDayParser.getGeneralForecast(2))
                    viewModel.txtNext3DateTemp = fourDayParser.getGeneralAvgTemperature(2).toString() + DEGREE
                    viewModel.imgNext4DateWeatherCondition =
                        determineNextDateWeatherIcon(fourDayParser.getGeneralForecast(3))
                    viewModel.imgNext4DateTag = determineNextDateWeatherIconTag(fourDayParser.getGeneralForecast(3))
                    viewModel.txtNext4DateTemp = fourDayParser.getGeneralAvgTemperature(3).toString() + DEGREE
                    frgRegionInfo.renderingUiFromViewModel()
                    frgRegionInfo.createAnimationForWeatherIcons() //create animation for fragment
                    supportFragmentManager.beginTransaction().replace(R.id.fvRegionInfo, frgRegionInfo).commitAllowingStateLoss()
                    animateWeatherIcons() //create animation after the layout is shown to prevent asynchronous call
                }

                override fun onFailure(call: Call<FourDayForecast.Response>, t: Throwable) {
                    Log.e("MainActivity", "getFourDayData onFailure()", t)
                }
            })
    }

    /**
     * Getter for the 24h-parser
     */
    fun getTwentyFourHourParser(): TwentyFourHourParser {
        return twentyFourHourParser
    }

    /*************************************
     * Helper functions                  *
     *************************************/

    /**
     * Helper function to determine the weather category from the 4 categories of weather.
     */
    private fun determineCurrentForecastDescription(forecast: String): String {
        return twentyFourHourParser.getForecastCategory(forecast).toString()
    }

    /**
     * Helper function to update the image drawable and tag at the top part of the app.
     */
    private fun determineCurrentWeatherIcon(forecast: String) {
        when (twentyFourHourParser.getForecastCategory(forecast)) {
            "Thundery" -> {
                imgWeatherCondition.setImageDrawable(ResourcesCompat.getDrawable(this.resources, R.drawable.thundery, null))
                imgWeatherCondition.tag = THUNDERY
            }
            "Rainy" -> {
                imgWeatherCondition.setImageDrawable(ResourcesCompat.getDrawable(this.resources, R.drawable.rainy, null))
                imgWeatherCondition.tag = RAINY
            }
            "Fair" -> {
                val currTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                if (currTime >= 18 || currTime <= 6) {
                    imgWeatherCondition.setImageDrawable(ResourcesCompat.getDrawable(this.resources, R.drawable.fair_moon, null))
                    imgWeatherCondition.tag = FAIR_MOON
                } else {
                    imgWeatherCondition.setImageDrawable(ResourcesCompat.getDrawable(this.resources, R.drawable.sunny, null))
                    imgWeatherCondition.tag = FAIR_SUN
                }
            }
            "Cloudy" -> {
                imgWeatherCondition.setImageDrawable(ResourcesCompat.getDrawable(this.resources, R.drawable.cloudy, null))
                imgWeatherCondition.tag = CLOUDY
            }
        }
    }

    /**
     * Helper function to determine the tag of the image for the next 4 days.
     */
    private fun determineNextDateWeatherIconTag(forecast: String) : Int {
        when (fourDayParser.getForecastCategory(forecast)) {
            "Thundery" -> return THUNDERY
            "Rainy" -> return RAINY
            "Fair" -> {
                val currTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                if (currTime >= 17 || currTime <= 6) {
                    return FAIR_MOON
                }
                return FAIR_SUN
            }
            "Cloudy" -> CLOUDY
        }
        return -1
    }

    /**
     * Helper function to determine the drawable id of the image drawable for the next 4 days.
     */
    private fun determineNextDateWeatherIcon(forecast: String): Int {
        when (fourDayParser.getForecastCategory(forecast)) {
            "Thundery" -> return R.drawable.thundery
            "Rainy" -> return R.drawable.rainy
            "Fair" -> {
                val currTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                if (currTime >= 18 || currTime <= 6) {
                    return R.drawable.fair_moon
                }
                return R.drawable.sunny
            }
            "Cloudy" -> return R.drawable.cloudy
        }
        return 0
    }

    /**
     * Helper function to update the drawable id of the morning weather in viewModel.
     */
    private fun determineMorningWeatherIcon(forecast: String) {
        when (twentyFourHourParser.getForecastCategory(forecast)) {
            "Thundery" -> viewModel.imgMorningWeatherCondition = R.drawable.thundery
            "Rainy" -> viewModel.imgMorningWeatherCondition = R.drawable.rainy
            "Fair" -> viewModel.imgMorningWeatherCondition = R.drawable.sunny
            "Cloudy" -> viewModel.imgMorningWeatherCondition = R.drawable.cloudy
        }
    }

    /**
     * Helper function to update the drawable id of the afternoon weather in viewModel.
     */
    private fun determineAfternoonWeatherIcon(forecast: String) {
        when (twentyFourHourParser.getForecastCategory(forecast)) {
            "Thundery" -> viewModel.imgAfternoonWeatherCondition = R.drawable.thundery
            "Rainy" -> viewModel.imgAfternoonWeatherCondition = R.drawable.rainy
            "Fair" -> viewModel.imgAfternoonWeatherCondition = R.drawable.sunny
            "Cloudy" -> viewModel.imgAfternoonWeatherCondition = R.drawable.cloudy
        }
    }

    /**
     * Helper function to update the drawable id of the evening weather in viewModel.
     */
    private fun determineEveningWeatherIcon(forecast: String) {
        when (twentyFourHourParser.getForecastCategory(forecast)) {
            "Thundery" -> viewModel.imgEveningWeatherCondition = R.drawable.thundery
            "Rainy" -> viewModel.imgEveningWeatherCondition = R.drawable.rainy
            "Fair" -> viewModel.imgEveningWeatherCondition = R.drawable.fair_moon
            "Cloudy" -> viewModel.imgEveningWeatherCondition = R.drawable.cloudy
        }
    }

    /**
     * Helper function to update the drawable id of the night weather in viewModel.
     */
    private fun determineNightWeatherIcon(forecast: String) {
        when (twentyFourHourParser.getForecastCategory(forecast)) {
            "Thundery" -> viewModel.imgNightWeatherCondition = R.drawable.thundery
            "Rainy" -> viewModel.imgNightWeatherCondition = R.drawable.rainy
            "Fair" -> viewModel.imgNightWeatherCondition = R.drawable.fair_moon
            "Cloudy" -> viewModel.imgNightWeatherCondition = R.drawable.cloudy
        }
    }
}