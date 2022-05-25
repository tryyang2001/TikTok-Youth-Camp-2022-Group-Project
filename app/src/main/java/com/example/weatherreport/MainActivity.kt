package com.example.weatherreport

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import java.util.Calendar
 
class MainActivity : AppCompatActivity() {
    private var btnPressed = true
    val frgRegionInfo = RegionInformation()
    val frgSingaporeMap = SingaporeMap()
    lateinit var txtAppTitle : TextView
    lateinit var txtRegion : TextView
    lateinit var txtTemp : TextView
    lateinit var txtWeatherCondition : TextView
    lateinit var imgWeatherCondition : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectBackgroundAndTextColors()
        onClickButtonChangeFragmentDisplay()
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
}