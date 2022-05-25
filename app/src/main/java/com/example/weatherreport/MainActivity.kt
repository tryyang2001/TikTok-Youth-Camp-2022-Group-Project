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
import java.time.LocalDateTime
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private var btnPressed = true
    val frgRegionInfo = RegionInformation()
    val frgSingaporeMap = SingaporeMap()
    lateinit var txtAppTitle : TextView
    lateinit var txtRegion : TextView
    lateinit var txtTemp : TextView
    lateinit var txtWeatherCondition : TextView
    lateinit var imgWeatherCondition : ImageView

    // API
    private val weatherAPI: WeatherAPI = NetworkModule.weatherAPI

    // 24H parser
    private var twentyFourHourParser: TwentyFourHourParser? = null

    //4 day parser
    private var fourDayParser: FourDayParser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        getTwentyFourHourData()
        getFourDayData()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectBackgroundAndTextColors()
        onClickButtonChangeFragmentDisplay()

        val weatherImg = findViewById<ImageView>(R.id.imgWeatherCondition)

        //Animation for weather image
        thread(start=true){
            if(weatherImg.drawable.constantState== ResourcesCompat.getDrawable(resources, R.drawable.sunny, null)?.constantState){
                val rotation = AnimationUtils.loadAnimation(applicationContext, R.anim.rotation)
                rotation.setFillAfter(true)
                weatherImg.startAnimation(rotation)
            }
            if(weatherImg.drawable.constantState==ResourcesCompat.getDrawable(resources, R.drawable.fair_moon, null)?.constantState){
                val rocking = AnimationUtils.loadAnimation(applicationContext, R.anim.rocking)
                rocking.fillAfter=true
                rocking.fillBefore=true
                weatherImg.startAnimation(rocking)
            }
            if(weatherImg.drawable.constantState==ResourcesCompat.getDrawable(resources, R.drawable.cloudy, null)?.constantState){
                val bouncing = AnimationUtils.loadAnimation(applicationContext, R.anim.bouncing)
                bouncing.fillAfter=true
                bouncing.fillBefore=true
                weatherImg.startAnimation(bouncing)
            }
        }

    }

    /**
     * This function handles the case when the "SHOW REGIONS" button or "HIDE REGIONS" button is
     * clicked. On clicking "SHOW REGIONS" button, a clickable Singapore map is shown, replacing
     * the weather information. On clicking "HIDE REGIONS" button, the weather information of
     * previously selected region will be shown, replacing the map.
     */
    private fun onClickButtonChangeFragmentDisplay() {
        supportFragmentManager.beginTransaction().add(R.id.fvRegionInfo, frgRegionInfo).commit()
        val btnShowMap = findViewById<Button>(R.id.btnShowMap)
        btnShowMap.setOnClickListener {
            if (btnPressed) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fvRegionInfo, frgSingaporeMap).commit()
                btnShowMap.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    bottomToTop = R.id.fvRegionInfo
                }
                btnShowMap.text = "Hide Regions"
            } else {
                supportFragmentManager.beginTransaction().replace(R.id.fvRegionInfo, frgRegionInfo)
                    .commit()
                btnShowMap.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    bottomToTop = ConstraintLayout.LayoutParams.UNSET
                }
                btnShowMap.text = "Show Regions"
            }
            btnPressed = !btnPressed
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
        if (currTime >= 19 || currTime <= 6) {
            mainLayout.setBackgroundResource(R.drawable.nightime_background)
            txtAppTitle = findViewById<TextView>(R.id.txtAppTitle)
            txtAppTitle.setTextColor(Color.parseColor("#FFFFFF"))
            txtRegion = findViewById<TextView>(R.id.txtRegion)
            txtRegion.setTextColor(Color.parseColor("#FFFFFF"))
            txtTemp = findViewById<TextView>(R.id.txtTemperature)
            txtTemp.setTextColor(Color.parseColor("#FFFFFF"))
            txtWeatherCondition = findViewById<TextView>(R.id.txtWeatherCondition)
            txtWeatherCondition.setTextColor(Color.parseColor("#FFFFFF"))
            val line = findViewById<View>(R.id.line1)
            line.setBackgroundColor(Color.parseColor("#FFFFFF"))
        } else {
            mainLayout.setBackgroundResource(R.drawable.daytime_background)
            txtAppTitle = findViewById<TextView>(R.id.txtAppTitle)
            txtAppTitle.setTextColor(Color.parseColor("#000000"))
            txtRegion = findViewById<TextView>(R.id.txtRegion)
            txtRegion.setTextColor(Color.parseColor("#000000"))
            txtTemp = findViewById<TextView>(R.id.txtTemperature)
            txtTemp.setTextColor(Color.parseColor("#000000"))
            txtWeatherCondition = findViewById<TextView>(R.id.txtWeatherCondition)
            txtWeatherCondition.setTextColor(Color.parseColor("#000000"))
        }
        imgWeatherCondition = findViewById(R.id.imgWeatherCondition)
    }

    private fun getTwentyFourHourData() {
        var currentDateTime: LocalDateTime = LocalDateTime.now()
        // Asynchronous network call through enqueue
        weatherAPI.getTwentyFourHourForecast(currentDateTime)
            .enqueue(object : Callback<TwentyFourHourForecast.Response> {
                override fun onResponse(call: Call<TwentyFourHourForecast.Response>,
                                        response: Response<TwentyFourHourForecast.Response>) {
                    val result = response.body() ?: return // null check
                    twentyFourHourParser = TwentyFourHourParser(currentDateTime, result)
                }

                override fun onFailure(call: Call<TwentyFourHourForecast.Response>, t: Throwable) {
                    Log.e("MainActivity", "getTwentyFourHourData onFailure()", t)
                }
            })
    }

    private fun getFourDayData() {
        var currentDateTime: LocalDateTime = LocalDateTime.now()
        var currentDate: LocalDate = LocalDate.now()
        // Asynchronous network call through enqueue
        weatherAPI.getFourDayForecast(currentDateTime)
            .enqueue(object : Callback<FourDayForecast.Response> {
                override fun onResponse(call: Call<FourDayForecast.Response>,
                                        response: Response<FourDayForecast.Response>) {
                    val result = response.body() ?: return // null check
                    fourDayParser = FourDayParser(currentDate, result)
                }

                override fun onFailure(call: Call<FourDayForecast.Response>, t: Throwable) {
                    Log.e("MainActivity", "getFourDayData onFailure()", t)
                }
            })
    }
}