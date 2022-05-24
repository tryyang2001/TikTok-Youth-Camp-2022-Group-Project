package com.example.weatherreport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams

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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private fun slideUp(view : View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(0F, 0F, view.height.toFloat(), 0F)
        animate.duration = 300
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    private fun slideDown(view : View) {
        val animate = TranslateAnimation(0F, 0F, 0F, view.height.toFloat())
        animate.duration = 300
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    private fun onClick(view : View) {
        /* TODO: on clicking a zone from the map, sends correct API request to the server and update
            corresponding data (such as the text views in activity_main.xml and RegionInformation.xml
         */
        when(view.id) {
            R.id.btnWestZone -> {
                Toast.makeText(activity?.applicationContext, "West Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnWestZone2 -> {
                Toast.makeText(activity?.applicationContext, "West Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnNorthZone -> {
                Toast.makeText(activity?.applicationContext, "North Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnNorthEastZone -> {
                Toast.makeText(activity?.applicationContext, "North East Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnNorthEastZone2 -> {
                Toast.makeText(activity?.applicationContext, "North East Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnEastZone -> {
                Toast.makeText(activity?.applicationContext, "East Zone", Toast.LENGTH_SHORT).show()
            }
            R.id.btnCentralZone -> {
                Toast.makeText(activity?.applicationContext, "Central Zone", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
        createClickableMap(view)
        // Inflate the layout for this fragment
        return view
    }

    /**
     * This function will handle the onClickEvenHandler of each button implemented on top of the map.
     */
    private fun createClickableMap(view: View) {
        val btnWest = view.findViewById<Button>(R.id.btnWestZone)
        val btnWest2 = view.findViewById<Button>(R.id.btnWestZone2)
        val btnNorth = view.findViewById<Button>(R.id.btnNorthZone)
        val btnNorthEast = view.findViewById<Button>(R.id.btnNorthEastZone)
        val btnNorthEast2 = view.findViewById<Button>(R.id.btnNorthEastZone2)
        val btnEast = view.findViewById<Button>(R.id.btnEastZone)
        val btnCentral = view.findViewById<Button>(R.id.btnCentralZone)
        val btnShowMap = activity?.findViewById<Button>(R.id.btnShowMap)
        btnWest.setOnClickListener { this.onClick(btnWest) }
        btnWest2.setOnClickListener { this.onClick(btnWest2) }
        btnNorth.setOnClickListener { this.onClick(btnNorth) }
        btnNorthEast.setOnClickListener { this.onClick(btnNorthEast) }
        btnNorthEast2.setOnClickListener { this.onClick(btnNorthEast2) }
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