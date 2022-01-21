package com.saraha.paws.Model

import java.io.Serializable

class Vendor (
    var vid: String,
    var name: String,
    var branch: String,
    var about: String,
    var latitude: Double,
    var longitude: Double,
    var link: String,
    var phone: String,
    var email: String,
    var type: String,
    var photo: String,
    var facebookUri: String? = null,
    var instagramUri: String? = null,
    var twitterUriUri: String? = null
        ): Serializable
{
    fun serviceType(): List<String>{
        return listOf("Vet", "Store")
    }
}