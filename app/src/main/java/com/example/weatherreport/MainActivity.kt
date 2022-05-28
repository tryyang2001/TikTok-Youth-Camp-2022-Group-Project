package com.example.weatherreport

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModelProvider
import com.example.weatherreport.network.WeatherApiService
import com.example.weatherreport.network.parsers.ForecastParser
import com.example.weatherreport.network.parsers.FourDayParser
import com.example.weatherreport.network.parsers.TwentyFourHourParser
import com.example.weatherreport.network.types.FourDayForecast
import com.example.weatherreport.network.types.TwentyFourHourForecast
import java.time.LocalDate
import java.util.Calendar
import kotlin.concurrent.thread
import kotlinx.coroutines.launch
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

class MainActivity : AppCompatActivity() {
    enum class Period { MORNING, NOON, EVENING, NIGHT }
    enum class ForecastType { THUNDERY, RAINY, FAIR_MOON, FAIR_SUN, CLOUDY }
    companion object {
        private const val DEGREE = "°"
    }
    private lateinit var frgRegionInfo: RegionInformation
    private lateinit var viewModel: RegionInfoViewModel
    private lateinit var frgSingaporeMap: SingaporeMap
    private lateinit var twentyFourHourParser: TwentyFourHourParser
    private lateinit var fourDayParser: FourDayParser
    private lateinit var txtAppTitle: TextView
    lateinit var txtRegion: TextView
    lateinit var txtTemp: TextView
    lateinit var txtWeatherCondition: TextView
    lateinit var imgWeatherCondition: ImageView
    lateinit var btnShowMap: Button
    private var btnPressed = true

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
        fetchApiData()
    }

    private fun fetchApiData() {
        @OptIn(DelicateCoroutinesApi::class)
        GlobalScope.launch(Dispatchers.Main) {
            val dateTime: String = LocalDate.now().toString() + "T00:00:00"

            val twentyFourHourRes = WeatherApiService.getInstance().getTwentyFourHourForecast(dateTime)
            val twentyFourHourResBody = twentyFourHourRes.body()!!

            val fourDayRes = WeatherApiService.getInstance().getFourDayForecast()
            val fourDayResBody = fourDayRes.body()!!

            uiRefreshCallback(twentyFourHourResBody, fourDayResBody)
        }
    }

    /**
     * This function will display different activity backgrounds according to the current time it
     * detected. In addition, it will change some text colors to either BLACK or WHITE according to
     * the current time provided.
     */
    private fun selectBackgroundAndTextColors() {
        val currTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
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
            when (imgWeatherCondition.tag) {
                ForecastType.FAIR_SUN -> {
                    val rotation = AnimationUtils.loadAnimation(applicationContext, R.anim.rotation)
                    rotation.fillAfter = true
                    imgWeatherCondition.startAnimation(rotation)
                }
                ForecastType.FAIR_MOON -> {
                    val rocking = AnimationUtils.loadAnimation(applicationContext, R.anim.rocking)
                    rocking.fillAfter = true
                    rocking.fillBefore = true
                    imgWeatherCondition.startAnimation(rocking)
                }
                ForecastType.CLOUDY -> {
                    val bouncing = AnimationUtils.loadAnimation(applicationContext, R.anim.bouncing)
                    bouncing.fillAfter = true
                    bouncing.fillBefore = true
                    imgWeatherCondition.startAnimation(bouncing)
                }
                ForecastType.RAINY, ForecastType.THUNDERY -> {
                    val drifting = AnimationUtils.loadAnimation(applicationContext, R.anim.drifting)
                    drifting.fillAfter = true
                    drifting.fillBefore = true
                    imgWeatherCondition.startAnimation(drifting)
                }
            }
        }
    }

    /**********************************
     * API related functions          *
     **********************************/

    private fun uiRefreshCallback(twentyFourHourResBody: TwentyFourHourForecast.Response,
                                  fourDayResBody: FourDayForecast.Response) {
        twentyFourHourParser = TwentyFourHourParser(twentyFourHourResBody)
        fourDayParser = FourDayParser(fourDayResBody)

        updateTwentyFourHourUi(twentyFourHourParser)
        updateFourDayUi(fourDayParser)
    }

    private fun updateTwentyFourHourUi(twentyFourHourParser: TwentyFourHourParser) {
        txtRegion.text = getString(R.string.country_name)

        val temperature = twentyFourHourParser.getGeneralAvgTemperature().toString() + DEGREE
        txtTemp.text = temperature

        txtWeatherCondition.text = determineCurrentForecastDescription(twentyFourHourParser.getGeneralForecast())
        determineCurrentWeatherIcon(twentyFourHourParser.getGeneralForecast())

        viewModel.txtDate = twentyFourHourParser.getCurrentDate()

        val morningEastForecast = twentyFourHourParser.getForecast(ForecastParser.Period.MORNING, ForecastParser.Region.EAST)
        viewModel.weatherCondition[0].txt = twentyFourHourParser.getForecastCategory(morningEastForecast)
        determineWeatherIcon(Period.MORNING, morningEastForecast)

        val noonEastForecast = twentyFourHourParser.getForecast(ForecastParser.Period.NOON, ForecastParser.Region.EAST)
        viewModel.weatherCondition[1].txt = twentyFourHourParser.getForecastCategory(noonEastForecast)
        determineWeatherIcon(Period.NOON, noonEastForecast)

        val eveningEastForecast = twentyFourHourParser.getForecast(ForecastParser.Period.EVENING, ForecastParser.Region.EAST)
        viewModel.weatherCondition[2].txt = twentyFourHourParser.getForecastCategory(eveningEastForecast)
        determineWeatherIcon(Period.EVENING, eveningEastForecast)

        val nightEastForecast = twentyFourHourParser.getForecast(ForecastParser.Period.NIGHT, ForecastParser.Region.EAST)
        viewModel.weatherCondition[3].txt = twentyFourHourParser.getForecastCategory(nightEastForecast)
        determineWeatherIcon(Period.NIGHT, nightEastForecast)

        frgRegionInfo.renderingUiFromViewModel()
        frgRegionInfo.createAnimationForWeatherIcons() //create animation for fragment
        supportFragmentManager.beginTransaction().replace(R.id.fvRegionInfo, frgRegionInfo).commitAllowingStateLoss()
        animateWeatherIcons() //create animation after the layout is shown to prevent asynchronous call
    }

    private fun updateFourDayUi(fourDayParser: FourDayParser) {
        viewModel.nextDate[0].txt = fourDayParser.getRelativeDate(0)
        viewModel.nextDate[0].img =
            determineNextDateWeatherIcon(fourDayParser.getGeneralForecast(0))
        viewModel.nextDate[0].tag = determineNextDateWeatherIconTag(fourDayParser.getGeneralForecast(0))
        viewModel.nextDate[0].dateTemp = fourDayParser.getGeneralAvgTemperature(0).toString() + DEGREE

        viewModel.nextDate[1].txt = fourDayParser.getRelativeDate(1)
        viewModel.nextDate[1].img =
            determineNextDateWeatherIcon(fourDayParser.getGeneralForecast(1))
        viewModel.nextDate[1].tag = determineNextDateWeatherIconTag(fourDayParser.getGeneralForecast(1))
        viewModel.nextDate[1].dateTemp = fourDayParser.getGeneralAvgTemperature(1).toString() + DEGREE

        viewModel.nextDate[2].txt = fourDayParser.getRelativeDate(2)
        viewModel.nextDate[2].img =
            determineNextDateWeatherIcon(fourDayParser.getGeneralForecast(2))
        viewModel.nextDate[2].tag = determineNextDateWeatherIconTag(fourDayParser.getGeneralForecast(2))
        viewModel.nextDate[2].dateTemp = fourDayParser.getGeneralAvgTemperature(2).toString() + DEGREE

        viewModel.nextDate[3].txt = fourDayParser.getRelativeDate(3)
        viewModel.nextDate[3].img =
            determineNextDateWeatherIcon(fourDayParser.getGeneralForecast(3))
        viewModel.nextDate[3].tag = determineNextDateWeatherIconTag(fourDayParser.getGeneralForecast(3))
        viewModel.nextDate[3].dateTemp = fourDayParser.getGeneralAvgTemperature(3).toString() + DEGREE

        frgRegionInfo.renderingUiFromViewModel()
        frgRegionInfo.createAnimationForWeatherIcons() //create animation for fragment
        supportFragmentManager.beginTransaction().replace(R.id.fvRegionInfo, frgRegionInfo).commitAllowingStateLoss()
        animateWeatherIcons() //create animation after the layout is shown to prevent asynchronous call
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
        return twentyFourHourParser.getForecastCategory(forecast)
    }

    /**
     * Helper function to update the image drawable and tag at the top part of the app.
     */
    private fun determineCurrentWeatherIcon(forecast: String) {
        when (twentyFourHourParser.getForecastCategory(forecast)) {
            "Thundery" -> {
                imgWeatherCondition.setImageDrawable(ResourcesCompat.getDrawable(this.resources, R.drawable.thundery, null))
                imgWeatherCondition.tag = ForecastType.THUNDERY
            }
            "Rainy" -> {
                imgWeatherCondition.setImageDrawable(ResourcesCompat.getDrawable(this.resources, R.drawable.rainy, null))
                imgWeatherCondition.tag = ForecastType.RAINY
            }
            "Fair" -> {
                val currTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                if (currTime >= 18 || currTime <= 6) {
                    imgWeatherCondition.setImageDrawable(ResourcesCompat.getDrawable(this.resources, R.drawable.fair_moon, null))
                    imgWeatherCondition.tag = ForecastType.FAIR_MOON
                } else {
                    imgWeatherCondition.setImageDrawable(ResourcesCompat.getDrawable(this.resources, R.drawable.sunny, null))
                    imgWeatherCondition.tag = ForecastType.FAIR_SUN
                }
            }
            "Cloudy" -> {
                imgWeatherCondition.setImageDrawable(ResourcesCompat.getDrawable(this.resources, R.drawable.cloudy, null))
                imgWeatherCondition.tag = ForecastType.CLOUDY
            }
        }
    }

    /**
     * Helper function to determine the tag of the image for the next 4 days.
     */
    private fun determineNextDateWeatherIconTag(forecast: String) : Int {
        when (fourDayParser.getForecastCategory(forecast)) {
            "Thundery" -> return ForecastType.THUNDERY.ordinal
            "Rainy" -> return ForecastType.RAINY.ordinal
            "Fair" ->return ForecastType.FAIR_SUN.ordinal //no moon for next 4 days forecast
            "Cloudy" -> return ForecastType.CLOUDY.ordinal
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
            "Fair" -> return R.drawable.sunny //no moon for next 4 days forecast
            "Cloudy" -> return R.drawable.cloudy
        }
        return 0
    }

    /**
     * Helper function to update the drawable id of the weather in viewModel.
     */
    private fun determineWeatherIcon(period: Period, forecast: String) {
        when (twentyFourHourParser.getForecastCategory(forecast)) {
            "Thundery" -> viewModel.weatherCondition[period.ordinal].img = R.drawable.thundery
            "Rainy" -> viewModel.weatherCondition[period.ordinal].img = R.drawable.rainy
            "Fair" -> viewModel.weatherCondition[period.ordinal].img = R.drawable.sunny
            "Cloudy" -> viewModel.weatherCondition[period.ordinal].img = R.drawable.cloudy
        }
    }
}