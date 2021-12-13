package com.saraha.paws.View.RegisterAccount.Fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.User
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.RegisterAccount.RegisterActivity
import com.saraha.paws.View.RegisterAccount.RegisterViewModel
import com.saraha.paws.databinding.FragmentRegisterPage1Binding

class RegisterPage1Fragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    lateinit var binding: FragmentRegisterPage1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterPage1Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as RegisterActivity)[RegisterViewModel::class.java]

        //Get passed data from activity to set in fragment
        val user = this.arguments?.getSerializable("user")
        if (user != null) setValues(user as User)

        onFieldFocus()

        return binding.root
    }

    //Function to set User input when returning to fragment
    private fun setValues(user: User) {
        Log.d(TAG,"RegisterPage1Fragment: - setValues: - : ${user.email}")
        if (user.email.isNotEmpty()){  binding.editTextRegisterEmail.setText(user.email) }
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
        val email = binding.editTextRegisterEmail
        val (result, isValid) = UserHelper()
            .emailVerification(email.text.toString())
        handleTextFields(email,result.string,0,isValid)
    }

    //Check password textField and handle use cases
    private fun validatePassword() {
        val password = binding.edittextRegisterPassword
        val (result, isValid) = UserHelper()
            .passwordValidation(password.text.toString(),null)
        handleTextFields(password,result.string,1,isValid)
    }

    //Check confirmed password textField and handle use cases
    private fun validateConfirmPassword() {
        val confirmPassword = binding.edittextRegisterConfirmPassword
        val password = binding.edittextRegisterPassword
        val (result, isValid) = UserHelper().passwordValidation(
            confirmPassword.text.toString(),
            password.text.toString())
        handleTextFields(confirmPassword,result.string,2,isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            v.error = msg
        } else {
            v.error = null
            when (index){
                0 -> viewModel.setEmailFromPage1(v.text.toString())
                1 -> {
                    viewModel.setPasswordValidation(isValid)
                    viewModel.setPasswordFromPage1(v.text.toString())
                }
                2 -> viewModel.setConfirmedPasswordValidation(isValid)
                else -> return
            }
        }
    }

}