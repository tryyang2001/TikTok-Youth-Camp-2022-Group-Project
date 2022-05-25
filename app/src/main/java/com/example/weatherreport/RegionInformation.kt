package com.example.weatherreport

import android.media.Image
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegionInformation.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegionInformation : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        /*TODO: Change the text display for txtDate, txtMorningTemp, txtAfternoonTemp, txtNightTemp,
           txtNext1Date, txtNext2Date, txtNext3Date, txtNext4Date, and image view photos:
           imgMorningWeatherCondition, imgAfternoonWeatherCondition, imgNightWeatherCondition,
           imgNext1DateCondition, imgNext2DateCondition, imgNext3DateCondition, imgNext4DateCondition
        */

        val view = inflater.inflate(R.layout.fragment_region_information, container, false)

        val rotation = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.rotation)
        rotation.fillAfter=true
        val rocking = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.rocking)
        rocking.fillAfter=true
        rocking.fillBefore=true
        val bouncing = AnimationUtils.loadAnimation(activity?.applicationContext, R.anim.bouncing)
        bouncing.fillAfter=true
        bouncing.fillBefore=true

        val imgMorningWeatherCondition = view.findViewById(R.id.imgMorningWeatherCondition) as ImageView
        val imgAfternoonWeatherCondition = view.findViewById(R.id.imgAfternoonWeatherCondition) as ImageView
        val imgNightWeatherCondition = view.findViewById(R.id.imgNightWeatherCondition) as ImageView
        val imgNext1DateCondition = view.findViewById(R.id.imgNext1Condition) as ImageView
        val imgNext2DateCondition = view.findViewById(R.id.imgNext2Condition) as ImageView
        val imgNext3DateCondition = view.findViewById(R.id.imgNext3Condition) as ImageView
        val imgNext4DateCondition = view.findViewById(R.id.imgNext4Condition) as ImageView


        thread(start = true){
            if(imgMorningWeatherCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.sunny, null)?.constantState){
                imgMorningWeatherCondition.startAnimation(rotation)
            }
            if(imgMorningWeatherCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.cloudy, null)?.constantState){
                imgMorningWeatherCondition.startAnimation(bouncing)
            }
        }
        thread(start = true){
            if(imgAfternoonWeatherCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.sunny, null)?.constantState){
                imgAfternoonWeatherCondition.startAnimation(rotation)
            }
            if(imgAfternoonWeatherCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.cloudy, null)?.constantState){
                imgAfternoonWeatherCondition.startAnimation(bouncing)
            }
        }
        thread(start = true){
            if(imgNightWeatherCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.fair_moon, null)?.constantState){
                imgNightWeatherCondition.startAnimation(rocking)
            }
            if(imgNightWeatherCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.cloudy, null)?.constantState){
                imgNightWeatherCondition.startAnimation(bouncing)
            }
        }
        thread(start = true){
            if(imgNext1DateCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.sunny, null)?.constantState){
                imgNext1DateCondition.startAnimation(rotation)
            }
            if(imgNext1DateCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.cloudy, null)?.constantState){
                imgNext1DateCondition.startAnimation(bouncing)
            }
        }
        thread(start = true){
            if(imgNext2DateCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.sunny, null)?.constantState){
                imgNext2DateCondition.startAnimation(rotation)
            }
            if(imgNext2DateCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.cloudy, null)?.constantState){
                imgNext2DateCondition.startAnimation(bouncing)
            }
        }
        thread(start = true){
            if(imgNext3DateCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.sunny, null)?.constantState){
                imgNext3DateCondition.startAnimation(rotation)
            }
            if(imgNext3DateCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.cloudy, null)?.constantState){
                imgNext3DateCondition.startAnimation(bouncing)
            }
        }
        thread(start = true){
            if(imgNext4DateCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.sunny, null)?.constantState){
                imgNext4DateCondition.startAnimation(rotation)
            }
            if(imgNext4DateCondition.drawable?.constantState==ResourcesCompat.getDrawable(resources, R.drawable.cloudy, null)?.constantState){
                imgNext4DateCondition.startAnimation(bouncing)
            }
        }



        // Inflate the layout for this fragment
        return view
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