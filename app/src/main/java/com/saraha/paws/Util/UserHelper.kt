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

    fun fieldVerification(editText: TextInputEditText): Pair<inputHandler,Boolean>{
        return if (editText.text?.isEmpty() == true){
            Pair(inputHandler.Required, false)
        } else {
            Pair(inputHandler.Correct, true)
        }
    }

    //Check email textField and handle use cases
    fun emailVerification(email: TextInputEditText): Pair<inputHandler,Boolean> {
        //verify email is not empty and the email is in a correct format
        return if (email.text?.isEmpty() == true) {
            Pair(inputHandler.Required, false)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            Pair(inputHandler.IncorrectEmail, false)
        } else {
            Pair(inputHandler.Correct, true)
        }
    }

    fun passwordValidation(password: TextInputEditText, toMatchPassword: TextInputEditText?): Pair<inputHandler,Boolean>{
        //Password rules:
        //A password must have at least eight characters.
        //A password consists of only letters and digits.
        //A password must contain at least two digits.
        return if (toMatchPassword == null){
            if (password.text?.isEmpty() == true) {
                Pair(inputHandler.Required, false)
            } else if (password.text?.matches("([A-Za-z0-9]*)(\\D*\\d){2,}".toRegex()) != true){
                Pair(inputHandler.IncorrectPassword, false)
            } else {
                Pair(inputHandler.Correct, true)
            }
        }else if (!password.text?.toString().equals(toMatchPassword.text.toString())){
            Pair(inputHandler.Mismatch, false)
        } else {
            Pair(inputHandler.Correct, true)
        }

    }

    fun mobileValidation(mobile: TextInputEditText): Pair<inputHandler,Boolean>{
        return if (mobile.text?.isEmpty() == true) {
            Pair(inputHandler.Required, false)
        } else if (mobile.text?.length in 9..12) {
            Pair(inputHandler.Correct, true)
        } else {
            Pair(inputHandler.IncorrectMobile, false)
        }

    }
}