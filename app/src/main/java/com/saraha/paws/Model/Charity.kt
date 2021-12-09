package com.saraha.paws.Model

import java.io.Serializable

data class Charity (
    var cid: String,
    var name: String,
    var founder: String,
    var email: String,
    var mobile: String,
    var facebookUrl: String,
    var instagramUrl: String,
): Serializable