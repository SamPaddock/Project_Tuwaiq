package com.saraha.paws.Model

import java.io.Serializable

data class Charity (
    var cid: String? = null,
    var name: String,
    var founder: String,
    var email: String,
    var mobile: String,
    var stcPay: String,
    var latitude: Double,
    var longitude: Double,
    var photo: String,
    var facebookUrl: String,
    var instagramUrl: String
): Serializable {

    constructor(): this(null,"","","", "","",0.0,
        0.0, "", "", "")

    fun isAllDataNotEmpty(): Boolean {
        return name.isNotEmpty() && email.isNotEmpty() && mobile.isNotEmpty()
                && stcPay.isNotEmpty() && facebookUrl.isNotEmpty()
                && instagramUrl.isNotEmpty() && founder.isNotEmpty()
                && photo.isNotEmpty()
    }

    fun getHashMap(photoUrl: String): HashMap<String, Any?>{
        return hashMapOf(
            "name" to name,
            "email" to email,
            "mobile" to mobile,
            "photo" to photoUrl,
            "stcPay" to stcPay,
            "latitude" to latitude,
            "longitude" to longitude,
            "facebookUrl" to facebookUrl,
            "instagramUrl" to instagramUrl,
            "founder" to founder,
        )
    }
}