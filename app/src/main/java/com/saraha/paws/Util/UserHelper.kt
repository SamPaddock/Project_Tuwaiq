package com.saraha.paws.Util

import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

class UserHelper {

    enum class inputHandler(val string: String){
        Required("Required"),
        IncorrectEmail("Email does not follow the correct format"),
        IncorrectPassword("should be at least 8 characters, 2 digits and no symbols"),
        IncorrectMobile("should be between 9 to 12 digits"),
        Correct(""),
        Mismatch("Does not match password")
    }

    fun fieldVerification(editText: String): Pair<inputHandler,Boolean>{
        return if (editText.isEmpty() == true){
            Pair(inputHandler.Required, false)
        } else {
            Pair(inputHandler.Correct, true)
        }
    }

    //Check email textField and handle use cases
    fun emailVerification(email: String): Pair<inputHandler,Boolean> {
        //verify email is not empty and the email is in a correct format
        return if (email.isEmpty() == true) {
            Pair(inputHandler.Required, false)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Pair(inputHandler.IncorrectEmail, false)
        } else {
            Pair(inputHandler.Correct, true)
        }
    }

    fun passwordValidation(
        password: String,
        toMatchPassword: String?)
    : Pair<inputHandler,Boolean>{
        //Password rules:
        //A password must have at least eight characters.
        //A password consists of only letters and digits.
        //A password must contain at least two digits.
        return if (toMatchPassword == null){
            if (password.isEmpty() == true) {
                Pair(inputHandler.Required, false)
            } else if (password.length!! >= 8
                && !password.matches("([A-Za-z0-9]*)(\\D*\\d){2,}".toRegex())
            ){
                Pair(inputHandler.IncorrectPassword, false)
            } else {
                Pair(inputHandler.Correct, true)
            }
        }else if (!password.equals(toMatchPassword.toString())){
            Pair(inputHandler.Mismatch, false)
        } else {
            Pair(inputHandler.Correct, true)
        }

    }

    fun mobileValidation(mobile: String): Pair<inputHandler,Boolean>{
        return if (mobile.isEmpty() == true) {
            Pair(inputHandler.Required, false)
        } else if (mobile.length in 9..12) {
            Pair(inputHandler.Correct, true)
        } else {
            Pair(inputHandler.IncorrectMobile, false)
        }

    }
}