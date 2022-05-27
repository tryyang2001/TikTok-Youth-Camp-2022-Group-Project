package com.example.weatherreport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weatherreport.network.parsers.ForecastParser
import com.example.weatherreport.network.parsers.TwentyFourHourParser
import java.util.*

class SingaporeMap : Fragment() {
    private lateinit var parser24h: TwentyFourHourParser
    private val DEGREE = "°"
    private lateinit var viewModel: RegionInfoViewModel
    private val THUNDERY = 0
    private val RAINY = 1
    private val FAIR_MOON = 2
    private val FAIR_SUN = 3
    private val CLOUDY = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    /**
     * Updates corresponding data when a button of a region is clicked.
     */
    private fun onClick(view: View) {
        viewModel.txtDate = parser24h.getCurrentDate()

        val temperatureText = parser24h.getGeneralAvgTemperature().toString() + DEGREE
        (activity as MainActivity).txtTemp.text = temperatureText

        var morningForecast: String? = null
        var afternoonForecast: String? = null
        var eveningForecast: String? = null
        var nightForecast: String? = null
        when (view.id) {
            R.id.btnWestZone -> {
                updateWestRegionCurrentInfo()
                morningForecast = parser24h.getForecast(ForecastParser.Period.MORNING, ForecastParser.Region.WEST)
                afternoonForecast = parser24h.getForecast(ForecastParser.Period.NOON, ForecastParser.Region.WEST)
                eveningForecast = parser24h.getForecast(ForecastParser.Period.EVENING, ForecastParser.Region.WEST)
                nightForecast = parser24h.getForecast(ForecastParser.Period.NIGHT, ForecastParser.Region.WEST)
            }
            R.id.btnNorthZone -> {
                updateNorthRegionCurrentInfo()
                morningForecast = parser24h.getForecast(ForecastParser.Period.MORNING, ForecastParser.Region.NORTH)
                afternoonForecast = parser24h.getForecast(ForecastParser.Period.NOON, ForecastParser.Region.NORTH)
                eveningForecast = parser24h.getForecast(ForecastParser.Period.EVENING, ForecastParser.Region.NORTH)
                nightForecast = parser24h.getForecast(ForecastParser.Period.NIGHT, ForecastParser.Region.NORTH)
            }
            R.id.btnSouthZone -> {
                updateSouthRegionCurrentInfo()
                morningForecast = parser24h.getForecast(ForecastParser.Period.MORNING, ForecastParser.Region.SOUTH)
                afternoonForecast = parser24h.getForecast(ForecastParser.Period.NOON, ForecastParser.Region.SOUTH)
                eveningForecast = parser24h.getForecast(ForecastParser.Period.EVENING, ForecastParser.Region.SOUTH)
                nightForecast = parser24h.getForecast(ForecastParser.Period.NIGHT, ForecastParser.Region.SOUTH)
            }
            R.id.btnEastZone -> {
                updateEastRegionCurrentInfo()
                morningForecast = parser24h.getForecast(ForecastParser.Period.MORNING, ForecastParser.Region.EAST)
                afternoonForecast = parser24h.getForecast(ForecastParser.Period.NOON, ForecastParser.Region.EAST)
                eveningForecast = parser24h.getForecast(ForecastParser.Period.EVENING, ForecastParser.Region.EAST)
                nightForecast = parser24h.getForecast(ForecastParser.Period.NIGHT, ForecastParser.Region.EAST)
            }
            R.id.btnCentralZone -> {
                updateCentralRegionCurrentInfo()
                morningForecast = parser24h.getForecast(ForecastParser.Period.MORNING, ForecastParser.Region.CENTRAL)
                afternoonForecast = parser24h.getForecast(ForecastParser.Period.NOON, ForecastParser.Region.CENTRAL)
                eveningForecast = parser24h.getForecast(ForecastParser.Period.EVENING, ForecastParser.Region.CENTRAL)
                nightForecast = parser24h.getForecast(ForecastParser.Period.NIGHT, ForecastParser.Region.CENTRAL)
            }
        }
        updateViewModelPeriodicWeatherCondition(morningForecast, afternoonForecast, eveningForecast, nightForecast)
        (activity as MainActivity).btnShowMap.performClick() //to change the fragment display
    }

    /**
     * Helper function to update west region current weather forecast.
     */
    private fun updateWestRegionCurrentInfo() {
        (activity as MainActivity).txtRegion.text = getString(R.string.west_region)
        (activity as MainActivity).txtWeatherCondition.text =
            parser24h.getForecastCategory(parser24h.getCurrentForecast(ForecastParser.Region.WEST))
        (activity as MainActivity).imgWeatherCondition.setImageDrawable(
            ResourcesCompat.getDrawable(
                requireActivity().resources,
                determineWeatherIconId(parser24h.getForecastCategory(parser24h.getCurrentForecast(ForecastParser.Region.WEST))),
                null
            )
        )
        (activity as MainActivity).imgWeatherCondition.tag =
            determineWeatherIconTag(parser24h.getCurrentForecast(ForecastParser.Region.WEST))
    }

    /**
     * Helper function to update north region current weather forecast.
     */
    private fun updateNorthRegionCurrentInfo() {
        (activity as MainActivity).txtRegion.text = getString(R.string.north_region)
        (activity as MainActivity).txtWeatherCondition.text =
            parser24h.getForecastCategory(parser24h.getCurrentForecast(ForecastParser.Region.NORTH))
        (activity as MainActivity).imgWeatherCondition.setImageDrawable(
            ResourcesCompat.getDrawable(
                requireActivity().resources,
                determineWeatherIconId(parser24h.getForecastCategory(parser24h.getCurrentForecast(ForecastParser.Region.NORTH))),
                null
            )
        )
        (activity as MainActivity).imgWeatherCondition.tag =
            determineWeatherIconTag(parser24h.getCurrentForecast(ForecastParser.Region.NORTH))
    }

    /**
     * Helper function to update south region current weather forecast.
     */
    private fun updateSouthRegionCurrentInfo() {
        (activity as MainActivity).txtRegion.text = getString(R.string.south_region)
        (activity as MainActivity).txtWeatherCondition.text =
            parser24h.getForecastCategory(parser24h.getCurrentForecast(ForecastParser.Region.SOUTH))
        (activity as MainActivity).imgWeatherCondition.setImageDrawable(
            ResourcesCompat.getDrawable(
                requireActivity().resources,
                determineWeatherIconId(parser24h.getForecastCategory(parser24h.getCurrentForecast(ForecastParser.Region.SOUTH))),
                null
            )
        )
        (activity as MainActivity).imgWeatherCondition.tag =
            determineWeatherIconTag(parser24h.getCurrentForecast(ForecastParser.Region.SOUTH))
    }

    /**
     * Helper function to update east region current weather forecast.
     */
    private fun updateEastRegionCurrentInfo() {
        (activity as MainActivity).txtRegion.text = getString(R.string.east_region)
        (activity as MainActivity).txtWeatherCondition.text =
            parser24h.getForecastCategory(parser24h.getCurrentForecast(ForecastParser.Region.EAST))
        (activity as MainActivity).imgWeatherCondition.setImageDrawable(
            ResourcesCompat.getDrawable(
                requireActivity().resources,
                determineWeatherIconId(parser24h.getForecastCategory(parser24h.getCurrentForecast(ForecastParser.Region.EAST))),
                null
            )
        )
        (activity as MainActivity).imgWeatherCondition.tag =
            determineWeatherIconTag(parser24h.getCurrentForecast(ForecastParser.Region.EAST))
    }

    /**
     * Helper function to update central region current weather forecast.
     */
    private fun updateCentralRegionCurrentInfo() {
        (activity as MainActivity).txtRegion.text = getString(R.string.central_region)
        (activity as MainActivity).txtWeatherCondition.text =
            parser24h.getForecastCategory(parser24h.getCurrentForecast(ForecastParser.Region.CENTRAL))
        (activity as MainActivity).imgWeatherCondition.setImageDrawable(
            ResourcesCompat.getDrawable(
                requireActivity().resources,
                determineWeatherIconId(parser24h.getForecastCategory(parser24h.getCurrentForecast(ForecastParser.Region.CENTRAL))),
                null
            )
        )
        (activity as MainActivity).imgWeatherCondition.tag =
            determineWeatherIconTag(parser24h.getCurrentForecast(ForecastParser.Region.CENTRAL))
    }

    /**
     * Helper function to update the morning, afternoon, evening and night weather information
     * of the view model so that the information can be passed to the other fragment.
     */
    private fun updateViewModelPeriodicWeatherCondition(
        morningForecast: String?,
        afternoonForecast: String?,
        eveningForecast: String?,
        nightForecast: String?
    ) {
        viewModel.imgMorningWeatherCondition = determineWeatherIconId(morningForecast)
        viewModel.imgAfternoonWeatherCondition = determineWeatherIconId(afternoonForecast)
        viewModel.imgEveningWeatherCondition = determineWeatherIconId(eveningForecast)
        viewModel.imgNightWeatherCondition = determineWeatherIconIdForNight(nightForecast)
        viewModel.txtMorningWeatherCondition = determineWeatherTextDescription(morningForecast)
        viewModel.txtAfternoonWeatherCondition = determineWeatherTextDescription(afternoonForecast)
        viewModel.txtEveningWeatherCondition = determineWeatherTextDescription(eveningForecast)
        viewModel.txtNightWeatherCondition = determineWeatherTextDescription(nightForecast)
    }

    /**
     * Helper function to determine the weather category from the 4 types of weather categories.
     */
    private fun determineWeatherTextDescription(forecast: String?): String {
        return parser24h.getForecastCategory(forecast!!)
    }

    /**
     * Helper function to determine the image tag according to its forecast.
     */
    private fun determineWeatherIconTag(forecast: String) : Int {
        when (parser24h.getForecastCategory(forecast)) {
            "Thundery" -> return THUNDERY
            "Rainy" -> return RAINY
            "Fair" -> {
                val currTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                if (currTime >= 18 || currTime <= 6) {
                    return FAIR_MOON
                }
                return FAIR_SUN
            }
            "CLOUDY" -> return CLOUDY
            }
        return -1
    }

    /**
     * Helper function to determine the image drawable id according to its forecast.
     */
    private fun determineWeatherIconId(forecast: String?): Int {
        when (parser24h.getForecastCategory(forecast!!)) {
            "Thundery" -> return R.drawable.thundery
            "Cloudy" -> return R.drawable.cloudy
            "Fair" -> {
                val currTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                if (currTime >= 18 || currTime <= 6) {
                    return R.drawable.fair_moon
                }
                return R.drawable.sunny
            }
            "Rainy" -> return R.drawable.rainy
        }
        return 0
    }

    /**
     * Helper function to determine the image drawable for night period according to its forecast.
     */
    private fun determineWeatherIconIdForNight(forecast: String?) : Int {
        when (parser24h.getForecastCategory(forecast!!)) {
            "Thundery" -> return R.drawable.thundery
            "Cloudy" -> return R.drawable.cloudy
            "Fair" -> return R.drawable.fair_moon
            "Rainy" -> return R.drawable.rainy
        }
        return 0
    }
}