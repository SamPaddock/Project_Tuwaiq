package com.saraha.paws.View.MapView

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.saraha.paws.R
import com.saraha.paws.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        getCurrentLocation { lat, lon ->
            setCurrentLocation(lat, lon)
        }

        mMap.setOnMapClickListener {
            val intent = Intent()
            intent.putExtra("Lat", it.latitude)
            intent.putExtra("Lon", it.longitude)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun setCurrentLocation(lat: Double, lon: Double) {
        Log.d(ContentValues.TAG, "MapsActivity: - onMapReady: - : ${lat} - ${lon}")
        // Add a marker at your location and move the camera
        val myLocation = LatLng(lat, lon)
        mMap.addMarker(MarkerOptions().position(myLocation).title("You"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))
    }


    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onCompletion: (latitude:Double, longitude:Double) -> Unit){
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,5f){
            var location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            val latitude = location?.latitude!!
            val longitude = location?.longitude!!
            onCompletion(latitude, longitude)
        }
    }
}