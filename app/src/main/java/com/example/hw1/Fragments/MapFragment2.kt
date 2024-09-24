package com.example.hw1.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hw1.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.openlocationcode.OpenLocationCode // Import the Open Location Code library

class MapFragment2 : Fragment(), OnMapReadyCallback {
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var plusCode: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_map2, container, false)

        // Get Plus Code or latitude/longitude from arguments
        arguments?.let {
            plusCode = it.getString("plus_code", null)
            if (plusCode != null) {
                // Decode Plus Code into latitude and longitude
                val codeArea = OpenLocationCode.decode(plusCode!!)
                latitude = codeArea.centerLatitude
                longitude = codeArea.centerLongitude
            } else {
                latitude = it.getDouble("latitude", 0.0)
                longitude = it.getDouble("longitude", 0.0)
            }
        }
        findViews(v)
        initViews(savedInstanceState)
        return v
    }

    private fun initViews(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    private fun findViews(v: View?) {
        mapView = v!!.findViewById(R.id.score_MAP)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val location = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(location).title("Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        fun newInstance(plusCode: String): MapFragment2 {
            val fragment = MapFragment2()
            val args = Bundle()
            args.putString("plus_code", plusCode)
            fragment.arguments = args
            return fragment
        }

    }
}