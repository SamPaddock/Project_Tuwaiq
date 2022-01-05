package com.saraha.paws.Util

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso
import java.lang.Math.*


//Add double quotes to a string
fun String.addQuote(): String{
    return " \"$this\" "
}

//Check a self permission
fun Context.hasPermissions(vararg permissions: String) = permissions.all { permission ->
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

//Show toast
fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

//Download Image into ImageView
fun ImageView.loadImage(imageUrl: String) {
    Picasso.get().load(imageUrl).into(this)
}

//get current device location
@SuppressLint("MissingPermission")
fun LatLng.getCurrentLocation(context: Context, onCompletion: (Double) -> Unit){
    val locationManager = context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager

    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10000,5f){
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        val UserX = location?.latitude!!
        val UserZ = location.longitude

        val ObjectX = this.latitude
        val ObjectZ = this.longitude

        var LonZ = if ((ObjectZ > 0 && UserZ < 0) || (ObjectZ < 0 && UserZ > 0)){ UserZ + ObjectZ }
        else if (ObjectZ > UserZ) { UserZ - ObjectZ } else { ObjectZ - UserZ }

        // by subtraction the objects location longitude from the x longitude
        var LatX = if ((ObjectX > 0 && UserX < 0) || (ObjectX < 0 && UserX > 0)){ UserX + ObjectX }
        else if (ObjectX > UserX) { UserX - ObjectX } else { ObjectX - UserX }

        // and multiplaying each result to 111139
        LatX *= 111139 * cos(LonZ)
        LonZ *= 111139

        val distance = sqrt(pow(LonZ, 2.0) + pow(LatX, 2.0))

        onCompletion(distance)
    }
}

//Get address from latitude and longitude
fun LatLng.getStringAddress(context: Context): String {
    Log.d(TAG,": - getStringAddress: - : ${this.latitude}")
    val geoCoder = Geocoder(context).getFromLocation(this.latitude, this.longitude, 1)
    return if (geoCoder.isEmpty()) "unable to retrieve location" else geoCoder[0].getAddressLine(0)
}