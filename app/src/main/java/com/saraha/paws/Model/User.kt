package com.saraha.paws.Model

import java.io.Serializable

data class User (
    var uid: String? = null,
    var photoUrl: String? = null,
    var email: String,
    var password: String? = null,
    var name: String,
    var mobile: String,
    var group: String,
    var type: String = "User"
        ):Serializable {
            fun isAllDataEmpty(): Boolean {
                return email.isNotEmpty() && name.isNotEmpty() && mobile.isNotEmpty()
                        && group.isNotEmpty() && photoUrl?.isNotEmpty() == true
            }

            fun isRegistrationDataNotEmpty(): Boolean{
                return email.isNotEmpty() && mobile.isNotEmpty()
                        && name.isNotEmpty() && group.isNotEmpty()
            }
        }