package com.saraha.paws.Util

enum class FirebaseExceptionMsg(val reason: String){
    ERROR_INVALID_CUSTOM_TOKEN("The custom token format is incorrect. Please check the documentation."),
    ERROR_INVALID_CREDENTIAL("The supplied auth credential is malformed or has expired."),
    ERROR_INVALID_EMAIL("The email address is badly formatted."),
    ERROR_WRONG_PASSWORD("The password is incorrect"),
    ERROR_EMAIL_ALREADY_IN_USE("The email address is already registered"),
    ERROR_USER_DISABLED("The user account has been disabled"),
    ERROR_USER_NOT_FOUND("There is no account with these credentials. The user may have been deleted."),
    ERROR_WEAK_PASSWORD("The given password is weak.") ,
    ERROR_NETWORK("A network error has occurred. Check your network connectivity.")
}