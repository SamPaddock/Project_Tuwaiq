package com.saraha.paws.Util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.saraha.paws.Model.Charity
import com.saraha.paws.R
import java.lang.Exception

class Helper {

    companion object {
        val appPermissionList = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    //Function returns static dropdown menu list
    fun getStatusList(): List<String>{
        return listOf("All", "Adopted", "Fostered", "For Adoption", "Vet: pre-check"
            , "Vet: post-check", "Vet: post-surgery", "Action: Rescue")
    }

    //Function returns static dropdown menu list
    fun getTypeList(): List<String>{
        return listOf("All", "Cat", "Dog", "Bird", "Aqua", "Rabbit", "Farm Animal", "Other")
    }

    //Function to handle to link icon click
    fun openLink(context: Context, link: String){
        // create an Intent to open a weblink
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }

}