package com.example.weatherreport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_region_information, container, false)
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