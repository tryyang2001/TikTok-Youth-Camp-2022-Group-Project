package com.example.weatherreport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.view.animation.AnimationUtils
import androidx.core.content.res.ResourcesCompat
import kotlin.concurrent.thread

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegionInformation.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegionInformation : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var txtDate : TextView
    lateinit var txtMorningTemp : TextView
    lateinit var txtAfternoonTemp : TextView
    lateinit var txtNightTemp : TextView
    lateinit var txtNext1Date : TextView
    lateinit var txtNext2Date : TextView
    lateinit var txtNext3Date : TextView
    lateinit var txtNext4Date : TextView
    lateinit var imgMorningWeatherCondition : ImageView
    lateinit var imgAfternoonWeatherCondition : ImageView
    lateinit var imgNightWeatherCondition : ImageView
    lateinit var imgNext1DateCondition : ImageView
    lateinit var imgNext2DateCondition : ImageView
    lateinit var imgNext3DateCondition : ImageView
    lateinit var imgNext4DateCondition : ImageView
    private val mainActivity = MainActivity()
    private val parser4day = mainActivity.getFourDayParser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_region_information, container, false)
        getTextViewAndImageView(view)
        update4DaysWeather()
        createAnimationForWeatherIcons()
        // Inflate the layout for this fragment
        return view
    }

    private fun createAnimationForWeatherIcons() {
        val rotation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.rotation)
        rotation.fillAfter = true
        val rocking = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.rocking)
        rocking.fillAfter = true
        rocking.fillBefore = true
        val bouncing = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.bouncing)
        bouncing.fillAfter = true
        bouncing.fillBefore = true

        thread(start = true) {
            if (imgMorningWeatherCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sunny,
                    null
                )?.constantState
            ) {
                imgMorningWeatherCondition.startAnimation(rotation)
            }
            if (imgMorningWeatherCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.cloudy,
                    null
                )?.constantState
            ) {
                imgMorningWeatherCondition.startAnimation(bouncing)
            }
        }
        thread(start = true) {
            if (imgAfternoonWeatherCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sunny,
                    null
                )?.constantState
            ) {
                imgAfternoonWeatherCondition.startAnimation(rotation)
            }
            if (imgAfternoonWeatherCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.cloudy,
                    null
                )?.constantState
            ) {
                imgAfternoonWeatherCondition.startAnimation(bouncing)
            }
        }
        thread(start = true) {
            if (imgNightWeatherCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.fair_moon,
                    null
                )?.constantState
            ) {
                imgNightWeatherCondition.startAnimation(rocking)
            }
            if (imgNightWeatherCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.cloudy,
                    null
                )?.constantState
            ) {
                imgNightWeatherCondition.startAnimation(bouncing)
            }
        }
        thread(start = true) {
            if (imgNext1DateCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sunny,
                    null
                )?.constantState
            ) {
                imgNext1DateCondition.startAnimation(rotation)
            }
            if (imgNext1DateCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.cloudy,
                    null
                )?.constantState
            ) {
                imgNext1DateCondition.startAnimation(bouncing)
            }
        }
        thread(start = true) {
            if (imgNext2DateCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sunny,
                    null
                )?.constantState
            ) {
                imgNext2DateCondition.startAnimation(rotation)
            }
            if (imgNext2DateCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.cloudy,
                    null
                )?.constantState
            ) {
                imgNext2DateCondition.startAnimation(bouncing)
            }
        }
        thread(start = true) {
            if (imgNext3DateCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sunny,
                    null
                )?.constantState
            ) {
                imgNext3DateCondition.startAnimation(rotation)
            }
            if (imgNext3DateCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.cloudy,
                    null
                )?.constantState
            ) {
                imgNext3DateCondition.startAnimation(bouncing)
            }
        }
        thread(start = true) {
            if (imgNext4DateCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.sunny,
                    null
                )?.constantState
            ) {
                imgNext4DateCondition.startAnimation(rotation)
            }
            if (imgNext4DateCondition.drawable?.constantState == ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.cloudy,
                    null
                )?.constantState
            ) {
                imgNext4DateCondition.startAnimation(bouncing)
            }
        }
    }

    private fun update4DaysWeather() {
        txtNext1Date.text = parser4day?.getDate(0)
        txtNext2Date.text = parser4day?.getDate(1)
        txtNext3Date.text = parser4day?.getDate(2)
        txtNext4Date.text = parser4day?.getDate(3)
        val txtNext1DateForecast = parser4day?.getGeneralForecast(0)
        val txtNext2DateForecast = parser4day?.getGeneralForecast(1)
        val txtNext3DateForecast = parser4day?.getGeneralForecast(2)
        val txtNext4DateForecast = parser4day?.getGeneralForecast(3)
        if (txtNext1DateForecast != null) {
            determineWeatherIcon(txtNext1DateForecast, imgNext1DateCondition)
        }
        if (txtNext2DateForecast != null) {
            determineWeatherIcon(txtNext2DateForecast, imgNext2DateCondition)
        }
        if (txtNext3DateForecast != null) {
            determineWeatherIcon(txtNext3DateForecast, imgNext3DateCondition)
        }
        if (txtNext4DateForecast != null) {
            determineWeatherIcon(txtNext4DateForecast, imgNext4DateCondition)
        }
    }

    private fun determineWeatherIcon(forecast: String, imageView : ImageView) {
        when (forecast) {
            "Thundery" -> imageView.setBackgroundResource(R.drawable.thundery)
            "Cloudy" -> imageView.setBackgroundResource(R.drawable.cloudy)
            "Fair" -> imageView.setBackgroundResource(R.drawable.sunny)
            "Rainy" -> imageView.setBackgroundResource(R.drawable.rainy)
        }
    }

    private fun getTextViewAndImageView(view : View) {
        txtDate = view.findViewById(R.id.txtDate)
        txtMorningTemp = view.findViewById(R.id.txtMorningTemp)
        txtAfternoonTemp = view.findViewById(R.id.txtAfternoonTemp)
        txtNightTemp = view.findViewById(R.id.txtNightTemp)
        txtNext1Date = view.findViewById(R.id.txtNext1Date)
        txtNext2Date = view.findViewById(R.id.txtNext2Date)
        txtNext3Date = view.findViewById(R.id.txtNext3Date)
        txtNext4Date = view.findViewById(R.id.txtNext4Date)
        imgMorningWeatherCondition = view.findViewById(R.id.imgMorningWeatherCondition)
        imgAfternoonWeatherCondition = view.findViewById(R.id.imgAfternoonWeatherCondition)
        imgNightWeatherCondition = view.findViewById(R.id.imgNightWeatherCondition)
        imgNext1DateCondition = view.findViewById(R.id.imgNext1Condition)
        imgNext2DateCondition = view.findViewById(R.id.imgNext2Condition)
        imgNext3DateCondition = view.findViewById(R.id.imgNext3Condition)
        imgNext4DateCondition = view.findViewById(R.id.imgNext4Condition)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegionInformation.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegionInformation().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}