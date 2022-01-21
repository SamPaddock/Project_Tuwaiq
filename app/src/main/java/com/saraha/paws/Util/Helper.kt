package com.saraha.paws.Util

import android.Manifest

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



}