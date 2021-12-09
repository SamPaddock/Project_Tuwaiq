package com.saraha.paws.View.RegisterAccount.Fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.RegisterAccount.RegisterViewModel
import com.saraha.paws.databinding.FragmentRegisterPage1Binding

class RegisterPage1Fragment : Fragment() {

    val viewModel: RegisterViewModel by viewModels()
    lateinit var binding: FragmentRegisterPage1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterPage1Binding.inflate(inflater, container, false)

        onFieldFocus()

        return binding.root
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.edittextRegisterConfirmPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateConfirmPassword()
        }
        binding.edittextRegisterPassword.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validatePassword()
        }
        binding.editTextRegisterEmail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateEmail()
        }

    }

    //Check email textField and handle use cases
    private fun validateEmail() {
        val (result, isValid) = UserHelper().emailVerification(binding.editTextRegisterEmail)
        handleTextFields(binding.editTextRegisterEmail,result.string,0,isValid)
    }

    //Check password textField and handle use cases
    private fun validatePassword() {
        val (result, isValid) = UserHelper().passwordValidation(binding.edittextRegisterPassword,null)
        handleTextFields(binding.edittextRegisterPassword,result.string,1,isValid)
    }

    //Check confirmed password textField and handle use cases
    private fun validateConfirmPassword() {
        val (result, isValid) = UserHelper().passwordValidation(
            binding.edittextRegisterConfirmPassword,
            binding.edittextRegisterPassword)
        handleTextFields(binding.edittextRegisterConfirmPassword,result.string,2,isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            v.error = msg
        } else {
            v.error = null
            when (index){
                0 -> viewModel.setEmailFromPage1(v.text.toString())
                1 -> viewModel.setPasswordValidation(isValid)
                2 -> viewModel.setConfirmedPasswordValidation(isValid)
                else -> return
            }
        }
    }


}