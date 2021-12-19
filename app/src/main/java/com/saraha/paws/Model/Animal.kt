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
    var photoUrl: String,
    var volunteerID: String,
    var volunteerName: String,
    var groupName: String
    ): Serializable
{

    fun isAllDataNotEmpty(): Boolean {
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