package com.saraha.paws.Util

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.google.rpc.context.AttributeContext
import com.saraha.paws.Model.Charity
import com.saraha.paws.R
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

//Get address from latitude and longitude
fun LatLng.getStringAddress(context: Context): String {
    Log.d(TAG,": - getStringAddress: - : ${this.latitude}")
    val geoCoder = Geocoder(context).getFromLocation(this.latitude, this.longitude, 1)
    return if (geoCoder.isEmpty()) "unable to retrieve location" else geoCoder[0].getAddressLine(0)
}