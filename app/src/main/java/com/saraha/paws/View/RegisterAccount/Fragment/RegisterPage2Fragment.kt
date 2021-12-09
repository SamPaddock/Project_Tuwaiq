package com.saraha.paws.View.RegisterAccount.Fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.RegisterAccount.RegisterViewModel
import com.saraha.paws.databinding.FragmentRegisterPage2Binding

class RegisterPage2Fragment : Fragment() {

    val viewModel: RegisterViewModel by viewModels()
    lateinit var binding: FragmentRegisterPage2Binding
    lateinit var list: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterPage2Binding.inflate(inflater, container, false)

        onFieldFocus()

        list = mutableListOf("Mercy Paws", "Paws EP")

        binding.autoCompleteRegisterGroup
            .setAdapter(ArrayAdapter(context!!, android.R.layout.simple_list_item_1, list))

        return binding.root
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.edittextRegisterName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateName()
        }
        binding.edittextRegisterMobile.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateMobile()
        }
        binding.autoCompleteRegisterGroup.setOnItemClickListener { parent, view, position, id ->
            var selection = list.get(position)
            validateGroup(selection)
        }
    }

    private fun validateGroup(selection: String) {
        viewModel.setGroupFromPage2(selection)
        Log.d(ContentValues.TAG,"RegisterPage1Fragment: - validateConfirmPassword: - : ${selection}")

    }

    private fun validateMobile() {
        val mobile = binding.edittextRegisterMobile
        val (result, isValid) = UserHelper().mobileValidation(mobile)
        handleTextFields(mobile,result.string,1,isValid)
    }

    private fun validateName() {
        val name = binding.edittextRegisterName
        val (result, isValid) = UserHelper().fieldVerification(name)
        handleTextFields(name,result.string,0,isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            v.error = msg
        } else {
            v.error = null
            when (index){
                0 -> viewModel.setNameFromPage2(v.text.toString())
                1 -> viewModel.setMobileFromPage2(v.text.toString())
                else -> return
            }
        }
    }


}