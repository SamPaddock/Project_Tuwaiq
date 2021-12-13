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
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.User
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.RegisterAccount.RegisterActivity
import com.saraha.paws.View.RegisterAccount.RegisterViewModel
import com.saraha.paws.databinding.FragmentRegisterPage2Binding

class RegisterPage2Fragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    lateinit var binding: FragmentRegisterPage2Binding
    lateinit var list: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterPage2Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as RegisterActivity)[RegisterViewModel::class.java]

        //Get passed data from activity to set in fragment
        val user = this.getArguments()?.getSerializable("user")
        if (user != null) setValues(user as User)

        onFieldFocus()

        viewModel.setGroupData().observe(this){
            //Set dropdown list adapter with list of charity groups
            list = it
            binding.autoCompleteRegisterGroup
                .setAdapter(ArrayAdapter(context!!, android.R.layout.simple_list_item_1, list))
        }

        return binding.root
    }

    //Function to set User input when returning to fragment
    private fun setValues(user: User) {
        if (user.name.isNotEmpty()){  binding.edittextRegisterName.setText(user.name) }
        if (user.mobile.isNotEmpty()){  binding.edittextRegisterMobile.setText(user.mobile) }
        if (user.group.isNotEmpty()){  binding.autoCompleteRegisterGroup.setText(user.group) }
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
            viewModel.setGroupFromPage2(list.get(position))
        }
    }

    private fun validateMobile() {
        val mobile = binding.edittextRegisterMobile
        val (result, isValid) = UserHelper().mobileValidation(mobile.text.toString())
        handleTextFields(mobile,result.string,1,isValid)
    }

    private fun validateName() {
        val name = binding.edittextRegisterName
        val (result, isValid) = UserHelper().fieldVerification(name.text.toString())
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