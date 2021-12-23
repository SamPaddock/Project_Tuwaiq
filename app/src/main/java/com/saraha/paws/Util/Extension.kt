package com.saraha.paws.Util

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso


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

fun LatLng.getStringAddress(context: Context): String {
    val geoCoder = Geocoder(context).getFromLocation(this.latitude, this.longitude, 1)
    return geoCoder[0].getAddressLine(0)
}