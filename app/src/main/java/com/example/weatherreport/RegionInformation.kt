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
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar
import kotlin.concurrent.thread

class RegionInformation : Fragment() {
    lateinit var txtDate : TextView
    lateinit var txtMorningWeatherCondition : TextView
    lateinit var txtAfternoonWeatherCondition : TextView
    lateinit var txtEveningWeatherCondition : TextView
    lateinit var txtNightWeatherCondition : TextView
    lateinit var txtNext1Date : TextView
    lateinit var txtNext2Date : TextView
    lateinit var txtNext3Date : TextView
    lateinit var txtNext4Date : TextView
    lateinit var imgMorningWeatherCondition : ImageView
    lateinit var imgAfternoonWeatherCondition : ImageView
    lateinit var imgEveningWeatherCondition : ImageView
    lateinit var imgNightWeatherCondition : ImageView
    lateinit var imgNext1DateCondition : ImageView
    lateinit var imgNext2DateCondition : ImageView
    lateinit var imgNext3DateCondition : ImageView
    lateinit var imgNext4DateCondition : ImageView
    lateinit var txtNext1DateTemp : TextView
    lateinit var txtNext2DateTemp : TextView
    lateinit var txtNext3DateTemp : TextView
    lateinit var txtNext4DateTemp : TextView
    lateinit var viewModel : RegionInfoViewModel
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
        txtAfternoonWeatherCondition = view.findViewById(R.id.txtAfternoonWeatherCondition)
        txtEveningWeatherCondition = view.findViewById(R.id.txtEveningWeatherCondition)
        txtNightWeatherCondition = view.findViewById(R.id.txtNightWeatherCondition)
        txtNext1Date = view.findViewById(R.id.txtNext1Date)
        txtNext2Date = view.findViewById(R.id.txtNext2Date)
        txtNext3Date = view.findViewById(R.id.txtNext3Date)
        txtNext4Date = view.findViewById(R.id.txtNext4Date)
        imgMorningWeatherCondition = view.findViewById(R.id.imgMorningWeatherCondition)
        imgAfternoonWeatherCondition = view.findViewById(R.id.imgAfternoonWeatherCondition)
        imgEveningWeatherCondition = view.findViewById(R.id.imgEveningWeatherCondition)
        imgNightWeatherCondition = view.findViewById(R.id.imgNightWeatherCondition)
        imgNext1DateCondition = view.findViewById(R.id.imgNext1Condition)
        imgNext2DateCondition = view.findViewById(R.id.imgNext2Condition)
        imgNext3DateCondition = view.findViewById(R.id.imgNext3Condition)
        imgNext4DateCondition = view.findViewById(R.id.imgNext4Condition)
        txtNext1DateTemp = view.findViewById(R.id.txtNext1DateTemp)
        txtNext2DateTemp = view.findViewById(R.id.txtNext2DateTemp)
        txtNext3DateTemp = view.findViewById(R.id.txtNext3DateTemp)
        txtNext4DateTemp = view.findViewById(R.id.txtNext4DateTemp)
    }

    /**
     * Renders Ui to ensure the text and image shown are up-to-date.
     */
    fun renderingUiFromViewModel() {
        txtDate.text = viewModel.txtDate
        txtMorningWeatherCondition.text = viewModel.txtMorningWeatherCondition
        txtAfternoonWeatherCondition.text = viewModel.txtAfternoonWeatherCondition
        txtEveningWeatherCondition.text = viewModel.txtEveningWeatherCondition
        txtNightWeatherCondition.text = viewModel.txtNightWeatherCondition
        determineWeatherIcon(viewModel.txtMorningWeatherCondition, imgMorningWeatherCondition)
        determineWeatherIcon(viewModel.txtAfternoonWeatherCondition, imgAfternoonWeatherCondition)
        determineWeatherIcon(viewModel.txtEveningWeatherCondition, imgEveningWeatherCondition, true)
        determineWeatherIcon(viewModel.txtNightWeatherCondition, imgNightWeatherCondition, true)
        txtNext1Date.text = viewModel.txtNext1Date
        txtNext2Date.text = viewModel.txtNext2Date
        txtNext3Date.text = viewModel.txtNext3Date
        txtNext4Date.text = viewModel.txtNext4Date
        imgNext1DateCondition.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, viewModel.imgNext1DateWeatherCondition, null))
        imgNext1DateCondition.tag = viewModel.imgNext1DateTag
        imgNext2DateCondition.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, viewModel.imgNext2DateWeatherCondition, null))
        imgNext2DateCondition.tag = viewModel.imgNext2DateTag
        imgNext3DateCondition.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, viewModel.imgNext3DateWeatherCondition, null))
        imgNext3DateCondition.tag = viewModel.imgNext3DateTag
        imgNext4DateCondition.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, viewModel.imgNext4DateWeatherCondition, null))
        imgNext4DateCondition.tag = viewModel.imgNext4DateTag
        txtNext1DateTemp.text = viewModel.txtNext1DateTemp
        txtNext2DateTemp.text = viewModel.txtNext2DateTemp
        txtNext3DateTemp.text = viewModel.txtNext3DateTemp
        txtNext4DateTemp.text = viewModel.txtNext4DateTemp
    }

    /*********************************
     * Animation related function    *
     *********************************/

    fun createAnimationForWeatherIcons() {
        val rotation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.rotation)
        rotation.fillAfter = true
        val rocking = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.rocking)
        rocking.fillAfter = true
        rocking.fillBefore = true
        val bouncing = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.bouncing)
        bouncing.fillAfter = true
        bouncing.fillBefore = true
        val drifting = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.drifting)
        drifting.fillAfter = true
        drifting.fillBefore = true
        thread(start = true) {
            if (imgMorningWeatherCondition.tag == FAIR_SUN) {
                imgMorningWeatherCondition.startAnimation(rotation)
            }
            else if (imgMorningWeatherCondition.tag == CLOUDY) {
                imgMorningWeatherCondition.startAnimation(bouncing)
            }
            else if (imgMorningWeatherCondition.tag == RAINY || imgMorningWeatherCondition.tag == THUNDERY) {
                imgMorningWeatherCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            if (imgAfternoonWeatherCondition.tag == FAIR_SUN) {
                imgAfternoonWeatherCondition.startAnimation(rotation)
            }
            else if (imgAfternoonWeatherCondition.tag == CLOUDY) {
                imgAfternoonWeatherCondition.startAnimation(bouncing)
            }
            else if (imgAfternoonWeatherCondition.tag == RAINY || imgAfternoonWeatherCondition.tag == THUNDERY) {
                imgAfternoonWeatherCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            if (imgEveningWeatherCondition.tag == FAIR_SUN) {
                imgEveningWeatherCondition.startAnimation(rotation)
            }
            else if (imgEveningWeatherCondition.tag == FAIR_MOON) {
                imgEveningWeatherCondition.startAnimation(rocking)
            }
            else if (imgEveningWeatherCondition.tag == CLOUDY) {
                imgEveningWeatherCondition.startAnimation(bouncing)
            }
            else if (imgEveningWeatherCondition.tag == RAINY || imgEveningWeatherCondition.tag == THUNDERY) {
                imgEveningWeatherCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            if (imgNightWeatherCondition.tag == FAIR_MOON) {
                imgNightWeatherCondition.startAnimation(rocking)
            }
            else if (imgNightWeatherCondition.tag == CLOUDY) {
                imgNightWeatherCondition.startAnimation(bouncing)
            }
            else if (imgNightWeatherCondition.tag == RAINY || imgNightWeatherCondition.tag == THUNDERY) {
                imgNightWeatherCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            if (imgNext1DateCondition.tag == FAIR_SUN) {
                imgNext1DateCondition.startAnimation(rotation)
            }
            else if (imgNext1DateCondition.tag == CLOUDY) {
                imgNext1DateCondition.startAnimation(bouncing)
            }
            else if (imgNext1DateCondition.tag == RAINY || imgNext1DateCondition.tag == THUNDERY) {
                imgNext1DateCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            if (imgNext2DateCondition.tag == FAIR_SUN) {
                imgNext2DateCondition.startAnimation(rotation)
            }
            else if (imgNext2DateCondition.tag == CLOUDY) {
                imgNext2DateCondition.startAnimation(bouncing)
            }
            else if (imgNext2DateCondition.tag == RAINY || imgNext2DateCondition.tag == THUNDERY) {
                imgNext2DateCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            if (imgNext3DateCondition.tag == FAIR_SUN) {
                imgNext3DateCondition.startAnimation(rotation)
            }
            else if (imgNext3DateCondition.tag == CLOUDY) {
                imgNext3DateCondition.startAnimation(bouncing)
            }
            else if (imgNext3DateCondition.tag == RAINY || imgNext3DateCondition.tag == THUNDERY) {
                imgNext3DateCondition.startAnimation(drifting)
            }
        }
        thread(start = true) {
            if (imgNext4DateCondition.tag == FAIR_SUN) {
                imgNext4DateCondition.startAnimation(rotation)
            }
            else if (imgNext4DateCondition.tag == CLOUDY) {
                imgNext4DateCondition.startAnimation(bouncing)
            }
            else if (imgNext4DateCondition.tag == RAINY || imgNext4DateCondition.tag == THUNDERY) {
                imgNext4DateCondition.startAnimation(drifting)
            }
        }
    }

    /**************************************
     * Helper functions                   *
     **************************************/

    /**
     * Helper function to update image drawable and its tag.
     */
    private fun determineWeatherIcon(forecast: String, imageView : ImageView, isNight: Boolean? =false) {
        when (forecast) {
            "Thundery" -> {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.thundery, null))
                imageView.tag = THUNDERY
            }
            "Cloudy" -> {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.cloudy, null))
                imageView.tag = CLOUDY
            }
            "Fair" -> {
                if (isNight == true) {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.fair_moon, null))
                    imageView.tag = FAIR_MOON
                } else {
                    imageView.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.sunny, null))
                    imageView.tag = FAIR_SUN
                }
            }
            "Rainy" -> {
                imageView.setImageDrawable(ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.rainy, null))
                imageView.tag = RAINY
            }
        }
    }
}