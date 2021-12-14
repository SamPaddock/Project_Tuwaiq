package com.saraha.paws.Model

import android.content.ContentValues.TAG
import android.util.Log
import java.io.Serializable

data class Animal (
    var aid: String? = null,
    var name: String,
    var type: String,
    var location: String,
    var age: String,
    var states: String,
    var gender: String,
    var color: String,
    var personality: String,
    var grooming: String,
    var medical: String,
    var photoUrl: String
    ): Serializable
{

    fun isAllDataNotEmpty(): Boolean {
        Log.d(TAG,"Animal: - isAllDataNotEmpty: - name: ${name}")
        Log.d(TAG,"Animal: - isAllDataNotEmpty: - type: ${type}")
        Log.d(TAG,"Animal: - isAllDataNotEmpty: - location: ${location}")
        Log.d(TAG,"Animal: - isAllDataNotEmpty: - age: ${age}")
        Log.d(TAG,"Animal: - isAllDataNotEmpty: - states: ${states}")
        Log.d(TAG,"Animal: - isAllDataNotEmpty: - gender: ${gender}")
        Log.d(TAG,"Animal: - isAllDataNotEmpty: - color: ${color}")
        Log.d(TAG,"Animal: - isAllDataNotEmpty: - personality: ${personality}")
        Log.d(TAG,"Animal: - isAllDataNotEmpty: - grooming: ${grooming}")
        Log.d(TAG,"Animal: - isAllDataNotEmpty: - medical: ${medical}")
        Log.d(TAG,"Animal: - isAllDataNotEmpty: - photoUrl: ${photoUrl}")
        return (name.isNotEmpty() && type.isNotEmpty() && location.isNotEmpty() && age.isNotEmpty()
                && gender.isNotEmpty() && color.isNotEmpty() && personality.isNotEmpty()
                && grooming.isNotEmpty() && medical.isNotEmpty() && photoUrl.isNotEmpty()
                && states.isNotEmpty())
    }

    fun getHashMap(photo: String): HashMap<String, String?>{
        return hashMapOf(
            "name" to name,
            "type" to type,
            "location" to location,
            "age" to age,
            "states" to states,
            "gender" to gender,
            "color" to color,
            "personality" to personality,
            "grooming" to grooming,
            "medical" to medical,
            "photoUrl" to photo,
        )
    }

}