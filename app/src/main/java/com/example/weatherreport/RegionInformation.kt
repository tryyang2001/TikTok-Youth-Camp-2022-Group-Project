package com.example.weatherreport

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlin.concurrent.thread

class RegionInformation : Fragment() {
    enum class ForecastType { THUNDERY, RAINY, FAIR_MOON, FAIR_SUN, CLOUDY }
    private lateinit var viewModel : RegionInfoViewModel
    private lateinit var txtDate : TextView
    private lateinit var txtMorningWeatherCondition : TextView
    private lateinit var txtAfternoonWeatherCondition : TextView
    private lateinit var txtEveningWeatherCondition : TextView
    private lateinit var txtNightWeatherCondition : TextView
    private lateinit var txtNext1Date : TextView
    private lateinit var txtNext2Date : TextView
    private lateinit var txtNext3Date : TextView
    private lateinit var txtNext4Date : TextView
    private lateinit var imgMorningWeatherCondition : ImageView
    private lateinit var imgAfternoonWeatherCondition : ImageView
    private lateinit var imgEveningWeatherCondition : ImageView
    private lateinit var imgNightWeatherCondition : ImageView
    private lateinit var imgNext1DateCondition : ImageView
    private lateinit var imgNext2DateCondition : ImageView
    private lateinit var imgNext3DateCondition : ImageView
    private lateinit var imgNext4DateCondition : ImageView
    private lateinit var txtNext1DateTemp : TextView
    private lateinit var txtNext2DateTemp : TextView
    private lateinit var txtNext3DateTemp : TextView
    private lateinit var txtNext4DateTemp : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(RegionInfoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_region_information, container, false)
        getTextViewAndImageView(view)
        renderingUiFromViewModel()
        createAnimationForWeatherIcons()
        return view
    }

    /**
     * Initialize each text view and image view with their corresponding id.
     */
    private fun getTextViewAndImageView(view : View) {
        txtDate = view.findViewById(R.id.txtDate)

        txtMorningWeatherCondition = view.findViewById(R.id.txtMorningWeatherCondition)
        imgMorningWeatherCondition = view.findViewById(R.id.imgMorningWeatherCondition)

        txtAfternoonWeatherCondition = view.findViewById(R.id.txtAfternoonWeatherCondition)
        imgAfternoonWeatherCondition = view.findViewById(R.id.imgAfternoonWeatherCondition)

        txtEveningWeatherCondition = view.findViewById(R.id.txtEveningWeatherCondition)
        imgEveningWeatherCondition = view.findViewById(R.id.imgEveningWeatherCondition)

        txtNightWeatherCondition = view.findViewById(R.id.txtNightWeatherCondition)
        imgNightWeatherCondition = view.findViewById(R.id.imgNightWeatherCondition)

        txtNext1Date = view.findViewById(R.id.txtNext1Date)
        imgNext1DateCondition = view.findViewById(R.id.imgNext1Condition)
        txtNext1DateTemp = view.findViewById(R.id.txtNext1DateTemp)

        txtNext2Date = view.findViewById(R.id.txtNext2Date)
        imgNext2DateCondition = view.findViewById(R.id.imgNext2Condition)
        txtNext2DateTemp = view.findViewById(R.id.txtNext2DateTemp)

        txtNext3Date = view.findViewById(R.id.txtNext3Date)
        imgNext3DateCondition = view.findViewById(R.id.imgNext3Condition)
        txtNext3DateTemp = view.findViewById(R.id.txtNext3DateTemp)

        txtNext4Date = view.findViewById(R.id.txtNext4Date)
        imgNext4DateCondition = view.findViewById(R.id.imgNext4Condition)
        txtNext4DateTemp = view.findViewById(R.id.txtNext4DateTemp)
    }

    /**
     * Renders Ui to ensure the text and image shown are up-to-date.
     */
    fun renderingUiFromViewModel() {
        txtDate.text = viewModel.txtDate

        txtMorningWeatherCondition.text = viewModel.weatherCondition[0].txt
        determineWeatherIcon(viewModel.weatherCondition[0].txt, imgMorningWeatherCondition)

        txtAfternoonWeatherCondition.text = viewModel.weatherCondition[1].txt
        determineWeatherIcon(viewModel.weatherCondition[1].txt, imgAfternoonWeatherCondition)

        txtEveningWeatherCondition.text = viewModel.weatherCondition[2].txt
        determineWeatherIcon(viewModel.weatherCondition[2].txt, imgEveningWeatherCondition, true)

        txtNightWeatherCondition.text = viewModel.weatherCondition[3].txt
        determineWeatherIcon(viewModel.weatherCondition[3].txt, imgNightWeatherCondition, true)

        txtNext1Date.text = viewModel.nextDate[0].txt
        imgNext1DateCondition.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, viewModel.nextDate[0].img, null))
        imgNext1DateCondition.tag = viewModel.nextDate[0].tag
        txtNext1DateTemp.text = viewModel.nextDate[0].dateTemp

        txtNext2Date.text = viewModel.nextDate[1].txt
        imgNext2DateCondition.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, viewModel.nextDate[1].img, null))
        imgNext2DateCondition.tag = viewModel.nextDate[1].tag
        txtNext2DateTemp.text = viewModel.nextDate[1].dateTemp

        txtNext3Date.text = viewModel.nextDate[2].txt
        imgNext3DateCondition.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, viewModel.nextDate[2].img, null))
        imgNext3DateCondition.tag = viewModel.nextDate[2].tag
        txtNext3DateTemp.text = viewModel.nextDate[2].dateTemp

        txtNext4Date.text = viewModel.nextDate[3].txt
        imgNext4DateCondition.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, viewModel.nextDate[3].img, null))
        imgNext4DateCondition.tag = viewModel.nextDate[3].tag
        txtNext4DateTemp.text = viewModel.nextDate[3].dateTemp
    }

    /*********************************
     * Animation related function    *
     *********************************/
    fun createAnimationForWeatherIcons() {
        val rotation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.rotation)
        val rocking = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.rocking)
        val bouncing = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.bouncing)
        val drifting = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.drifting)

        rotation.fillAfter = true
        rotation.fillBefore = true
        rocking.fillAfter = true
        rocking.fillBefore = true
        bouncing.fillAfter = true
        bouncing.fillBefore = true
        drifting.fillAfter = true
        drifting.fillBefore = true

        thread(start = true) {
            when (imgMorningWeatherCondition.tag) {
                ForecastType.FAIR_SUN -> imgMorningWeatherCondition.startAnimation(rotation)
                ForecastType.CLOUDY -> imgMorningWeatherCondition.startAnimation(bouncing)
                ForecastType.RAINY, ForecastType.THUNDERY -> imgMorningWeatherCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            when (imgAfternoonWeatherCondition.tag) {
                ForecastType.FAIR_SUN -> imgAfternoonWeatherCondition.startAnimation(rotation)
                ForecastType.CLOUDY -> imgAfternoonWeatherCondition.startAnimation(bouncing)
                ForecastType.RAINY, ForecastType.THUNDERY -> imgAfternoonWeatherCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            when (imgEveningWeatherCondition.tag) {
                ForecastType.FAIR_SUN -> imgEveningWeatherCondition.startAnimation(rotation)
                ForecastType.FAIR_MOON -> imgEveningWeatherCondition.startAnimation(rocking)
                ForecastType.CLOUDY -> imgEveningWeatherCondition.startAnimation(bouncing)
                ForecastType.RAINY, ForecastType.THUNDERY -> imgEveningWeatherCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            when (imgNightWeatherCondition.tag) {
                ForecastType.FAIR_MOON -> imgNightWeatherCondition.startAnimation(rocking)
                ForecastType.CLOUDY -> imgNightWeatherCondition.startAnimation(bouncing)
                ForecastType.RAINY, ForecastType.THUNDERY -> imgNightWeatherCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            when (imgNext1DateCondition.tag) {
                ForecastType.FAIR_SUN -> imgNext1DateCondition.startAnimation(rotation)
                ForecastType.CLOUDY -> imgNext1DateCondition.startAnimation(bouncing)
                ForecastType.RAINY, ForecastType.THUNDERY -> imgNext1DateCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            when (imgNext2DateCondition.tag) {
                ForecastType.FAIR_SUN -> imgNext2DateCondition.startAnimation(rotation)
                ForecastType.CLOUDY -> imgNext2DateCondition.startAnimation(bouncing)
                ForecastType.RAINY, ForecastType.THUNDERY -> imgNext2DateCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            when (imgNext3DateCondition.tag) {
                ForecastType.FAIR_SUN -> imgNext3DateCondition.startAnimation(rotation)
                ForecastType.CLOUDY -> imgNext3DateCondition.startAnimation(bouncing)
                ForecastType.RAINY, ForecastType.THUNDERY -> imgNext3DateCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            when (imgNext4DateCondition.tag) {
                ForecastType.FAIR_SUN -> imgNext4DateCondition.startAnimation(rotation)
                ForecastType.CLOUDY -> imgNext4DateCondition.startAnimation(bouncing)
                ForecastType.RAINY, ForecastType.THUNDERY -> imgNext4DateCondition.startAnimation(drifting)
            }
        }
    }

    /**************************************
     * Helper functions                   *
     **************************************/

    /**
     * Helper function to update image drawable and its tag.
     */
    private fun determineWeatherIcon(forecast: String, imageView : ImageView, isNight: Boolean? = false) {
        when (forecast) {
            "Thundery" -> {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.thundery, null))
                imageView.tag = ForecastType.THUNDERY
            }
            "Cloudy" -> {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.cloudy, null))
                imageView.tag = ForecastType.CLOUDY
            }
            "Fair" -> {
                if (isNight == true) {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.fair_moon, null))
                    imageView.tag = ForecastType.FAIR_MOON
                } else {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.sunny, null))
                    imageView.tag = ForecastType.FAIR_SUN
                }
            }
            "Rainy" -> {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.rainy, null))
                imageView.tag = ForecastType.RAINY
            }
        }
    }
}
