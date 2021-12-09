package com.saraha.paws.Model

data class User (
    var uid: String? = null,
    var photoUrl: String? = null,
    var email: String,
    var name: String,
    var mobile: String,
    var group: String
        )