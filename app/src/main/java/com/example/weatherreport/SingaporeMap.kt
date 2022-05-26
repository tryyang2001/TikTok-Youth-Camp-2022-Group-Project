package com.example.weatherreport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.weatherreport.network.parsers.TwentyFourHourParser
import java.util.Calendar

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
    private lateinit var parser24h : TwentyFourHourParser
    private val DEGREE = "°"
    private lateinit var viewModel : RegionInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(requireActivity()).get(RegionInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_singapore_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parser24h = (activity as MainActivity).getTwentyFourHourParser()
        setMapButtonOnClickListener(view)
    }

    private fun onClick(view : View) {
        viewModel.txtDate = parser24h.getCurrentDate()
        (activity as MainActivity).txtTemp.text = parser24h.getGeneralAvgTemperature().toString() + DEGREE
        (activity as MainActivity).txtWeatherCondition.text = determineWeatherTextDescription(parser24h.getGeneralForecast())
        var morningForecast : String? = null
        var afternoonForecast : String? = null
        var nightForecast : String? = null
        when(view.id) {
            R.id.btnWestZone -> {
                (activity as MainActivity).txtRegion.text = "West Region"
                (activity as MainActivity).txtWeatherCondition.text = parser24h.getForecastCategory(parser24h.getCurrentWestForecast())
                morningForecast = parser24h.getMorningWestForecast()
                afternoonForecast = parser24h.getNoonWestForecast()
                nightForecast = parser24h.getNightWestForecast()
                Toast.makeText(activity?.applicationContext, "West Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnNorthZone -> {
                (activity as MainActivity).txtRegion.text = "North Region"
                (activity as MainActivity).txtWeatherCondition.text = parser24h.getForecastCategory(parser24h.getCurrentNorthForecast())
                morningForecast = parser24h.getMorningNorthForecast()
                afternoonForecast = parser24h.getNoonNorthForecast()
                nightForecast = parser24h.getNightNorthForecast()
                Toast.makeText(activity?.applicationContext, "North Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnSouthZone -> {
                (activity as MainActivity).txtRegion.text = "South Region"
                (activity as MainActivity).txtWeatherCondition.text = parser24h.getForecastCategory(parser24h.getCurrentSouthForecast())
                morningForecast = parser24h.getMorningSouthForecast()
                afternoonForecast = parser24h.getNoonSouthForecast()
                nightForecast = parser24h.getNightSouthForecast()
                Toast.makeText(activity?.applicationContext, "North East Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnEastZone -> {
                (activity as MainActivity).txtRegion.text = "East Region"
                (activity as MainActivity).txtWeatherCondition.text = parser24h.getForecastCategory(parser24h.getCurrentEastForecast())
                morningForecast = parser24h.getMorningEastForecast()
                afternoonForecast = parser24h.getNoonEastForecast()
                nightForecast = parser24h.getNightEastForecast()
                Toast.makeText(activity?.applicationContext, "East Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnCentralZone -> {
                (activity as MainActivity).txtRegion.text = "Central Region"
                (activity as MainActivity).txtWeatherCondition.text = parser24h.getForecastCategory(parser24h.getCurrentCentralForecast())
                morningForecast = parser24h.getMorningCentralForecast()
                afternoonForecast = parser24h.getNoonCentralForecast()
                nightForecast = parser24h.getNightCentralForecast()
                Toast.makeText(activity?.applicationContext, "Central Zone", Toast.LENGTH_SHORT).show()
            }
        }
        update24HourInfo(morningForecast, afternoonForecast, nightForecast)
        (activity as MainActivity).btnShowMap.performClick() //to change the fragment display
    }

    private fun update24HourInfo(morningForecast : String?, afternoonForecast : String?, nightForecast : String?) {
        viewModel.imgMorningWeatherCondition = determineWeatherIcon(morningForecast)
        viewModel.imgAfternoonWeatherCondition = determineWeatherIcon(afternoonForecast)
        viewModel.imgNightWeatherCondition = determineWeatherIcon(nightForecast)
        viewModel.txtMorningWeatherCondition = determineWeatherTextDescription(morningForecast)
        viewModel.txtAfternoonWeatherCondition = determineWeatherTextDescription(afternoonForecast)
        viewModel.txtNightWeatherCondition = determineWeatherTextDescription(nightForecast)
    }

    private fun determineWeatherTextDescription(forecast: String?) : String {
        return parser24h.getForecastCategory(forecast).toString()
    }

    private fun determineWeatherIcon(forecast: String?) : Int{
        println(parser24h.getForecastCategory(forecast))
        when (parser24h.getForecastCategory(forecast)) {
            "Thundery" -> return R.drawable.thundery
            "Cloudy" -> return R.drawable.cloudy
            "Fair" -> {
                val currTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                return if (currTime >= 19 || currTime <= 6) {
                    R.drawable.fair_moon
                } else {
                    R.drawable.sunny
                }
            }
            "Rainy" -> return R.drawable.rainy
        }
        return 0
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