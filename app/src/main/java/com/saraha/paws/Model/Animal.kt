package com.saraha.paws.Model

import java.io.Serializable

data class Animal (
    var aid: String? = null,
    var name: String,
    var type: String,
    var location: String,
    var age: String,
    var gender: String,
    var color: String,
    var personality: String,
    var grooming: String,
    var medical: String,
    var photoUrl: String
        ): Serializable