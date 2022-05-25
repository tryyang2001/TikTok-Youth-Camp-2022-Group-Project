package com.example.weatherreport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.weatherreport.network.parsers.TwentyFourHourParser
import com.example.weatherreport.network.types.TwentyFourHourForecast
import java.time.LocalDate
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SingaporeMap.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingaporeMap : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private val mainActivity = MainActivity()
    private val frgRegionInformation = mainActivity.frgRegionInfo
    private val date = LocalDate.now() //TODO: replace to real date
    private val res = TwentyFourHourForecast.Response(, ) //TODO: replace to real response
    private val parser24h = TwentyFourHourParser(date, res)
    private val DEGREE = "°"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_singapore_map, container, false)
        setMapButtonOnClickListener(view)
        // Inflate the layout for this fragment
        return view
    }

    private fun onClick(view : View) {
        frgRegionInformation.txtDate.text = parser24h.getCurrentDate()
        mainActivity.txtTemp.text = parser24h.getGeneralAvgTemperature().toString() + DEGREE
        var morningForecast = ""
        var afternoonForecast = ""
        var nightForecast = ""
        when(view.id) {
            R.id.btnWestZone -> {
                val triple = updateWestZoneInfo(morningForecast, afternoonForecast, nightForecast)
                afternoonForecast = triple.first
                morningForecast = triple.second
                nightForecast = triple.third
            }
            R.id.btnNorthZone -> {
                mainActivity.txtRegion.text = "North Region"
                morningForecast = parser24h.getMorningNorthForecast()
                afternoonForecast = parser24h.getNoonNorthForecast()
                nightForecast = parser24h.getNightNorthForecast()
                frgRegionInformation.txtMorningTemp.text = "" //TODO: get value of temperature in different periods
                frgRegionInformation.txtAfternoonTemp.text = "" //TODO: get value of temperature in different periods
                frgRegionInformation.txtNightTemp.text = "" //TODO: get value of temperature in different periods
                Toast.makeText(activity?.applicationContext, "North Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnSouthZone -> {
                mainActivity.txtRegion.text = "South Region"
                morningForecast = parser24h.getMorningSouthForecast()
                afternoonForecast = parser24h.getNoonSouthForecast()
                nightForecast = parser24h.getNightSouthForecast()
                frgRegionInformation.txtMorningTemp.text = "" //TODO: get value of temperature in different periods
                frgRegionInformation.txtAfternoonTemp.text = "" //TODO: get value of temperature in different periods
                frgRegionInformation.txtNightTemp.text = "" //TODO: get value of temperature in different periods
                Toast.makeText(activity?.applicationContext, "North East Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnEastZone -> {
                mainActivity.txtRegion.text = "East Region"
                morningForecast = parser24h.getMorningEastForecast()
                afternoonForecast = parser24h.getNoonEastForecast()
                nightForecast = parser24h.getNightEastForecast()
                frgRegionInformation.txtMorningTemp.text = "" //TODO: get value of temperature in different periods
                frgRegionInformation.txtAfternoonTemp.text = "" //TODO: get value of temperature in different periods
                frgRegionInformation.txtNightTemp.text = "" //TODO: get value of temperature in different periods
                Toast.makeText(activity?.applicationContext, "East Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnCentralZone -> {
                mainActivity.txtRegion.text = "Central Region"
                morningForecast = parser24h.getMorningCentralForecast()
                afternoonForecast = parser24h.getNoonCentralForecast()
                nightForecast = parser24h.getNightCentralForecast()
                frgRegionInformation.txtMorningTemp.text = "" //TODO: get value of temperature in different periods
                frgRegionInformation.txtAfternoonTemp.text = "" //TODO: get value of temperature in different periods
                frgRegionInformation.txtNightTemp.text = "" //TODO: get value of temperature in different periods
                Toast.makeText(activity?.applicationContext, "Central Zone", Toast.LENGTH_SHORT).show()
            }
        }
        update24HourInfo(morningForecast, afternoonForecast, nightForecast)
    }

    private fun updateWestZoneInfo(
        morningForecast: String,
        afternoonForecast: String,
        nightForecast: String
    ): Triple<String, String, String> {
        var morningForecast1 = morningForecast
        var afternoonForecast1 = afternoonForecast
        var nightForecast1 = nightForecast
        mainActivity.txtRegion.text = "West Region"
        morningForecast1 = parser24h.getMorningWestForecast()
        afternoonForecast1 = parser24h.getNoonWestForecast()
        nightForecast1 = parser24h.getNightWestForecast()
        frgRegionInformation.txtMorningTemp.text =
            "" //TODO: get value of temperature in different periods
        frgRegionInformation.txtAfternoonTemp.text =
            "" //TODO: get value of temperature in different periods
        frgRegionInformation.txtNightTemp.text =
            "" //TODO: get value of temperature in different periods
        Toast.makeText(activity?.applicationContext, "West Zone", Toast.LENGTH_SHORT).show()
        return Triple(afternoonForecast1, morningForecast1, nightForecast1)
    }

    private fun update24HourInfo(morningForecast : String, afternoonForecast : String, nightForecast : String) {
        determineWeatherIcon(morningForecast, frgRegionInformation.imgMorningWeatherCondition)
        determineWeatherIcon(afternoonForecast, frgRegionInformation.imgAfternoonWeatherCondition)
        determineWeatherIcon(nightForecast, frgRegionInformation.imgNightWeatherCondition)
    }

    private fun determineWeatherIcon(forecast: String, imageView : ImageView) {
        when (forecast) {
            "Thundery" -> imageView.setBackgroundResource(R.drawable.thundery)
            "Cloudy" -> imageView.setBackgroundResource(R.drawable.cloudy)
            "Fair" -> {
                val currTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                if (currTime >= 19 || currTime <= 6) {
                    imageView.setBackgroundResource(R.drawable.fair_moon)
                } else {
                    imageView.setBackgroundResource(R.drawable.sunny)
                }
            }
            "Rainy" -> imageView.setBackgroundResource(R.drawable.rainy)
        }
    }

    /**
     * This function will handle the onClickEvenHandler of each button implemented on top of the map.
     */
    private fun setMapButtonOnClickListener(view: View) {
        val btnWest = view.findViewById<Button>(R.id.btnWestZone)
        val btnNorth = view.findViewById<Button>(R.id.btnNorthZone)
        val btnSouth = view.findViewById<Button>(R.id.btnSouthZone)
        val btnEast = view.findViewById<Button>(R.id.btnEastZone)
        val btnCentral = view.findViewById<Button>(R.id.btnCentralZone)
        val btnShowMap = activity?.findViewById<Button>(R.id.btnShowMap)
        btnWest.setOnClickListener { this.onClick(btnWest) }
        btnNorth.setOnClickListener { this.onClick(btnNorth) }
        btnSouth.setOnClickListener { this.onClick(btnSouth) }
        btnEast.setOnClickListener { this.onClick(btnEast) }
        btnCentral.setOnClickListener { this.onClick(btnCentral) }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SingaporeMap.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                SingaporeMap().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}