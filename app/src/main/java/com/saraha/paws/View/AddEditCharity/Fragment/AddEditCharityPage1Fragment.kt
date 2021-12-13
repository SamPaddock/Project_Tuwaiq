package com.saraha.paws.View.AddEditCharity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.Charity
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.AddEditCharity.AddEditCharityViewModel
import com.saraha.paws.View.RegisterAccount.RegisterActivity
import com.saraha.paws.databinding.FragmentAddEditCharityPage1Binding
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import com.saraha.paws.View.AddEditCharity.AddEditCharityActivity

class AddEditCharityPage1Fragment : Fragment() {

    private lateinit var viewModel: AddEditCharityViewModel
    lateinit var binding: FragmentAddEditCharityPage1Binding

    lateinit var imgData: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditCharityPage1Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as AddEditCharityActivity)[AddEditCharityViewModel::class.java]

        //Get passed data from activity to set in fragment
        val charity = this.arguments?.getSerializable("charity")
        if (charity != null) setValues(charity as Charity)

        onFieldFocus()

        selectPhoto()

        return binding.root
    }

    fun selectPhoto(){
        binding.floatingActionButtonAddCharityPhoto.setOnClickListener {
            val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(takePictureIntent, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            2 -> if (resultCode == RESULT_OK) {
                imgData = data?.data!!
                binding.imageViewCharityPhoto.setImageURI(imgData)
                viewModel.setCharityPhoto(imgData)
            }
        }
    }

    //Function to set User input when returning to fragment
    private fun setValues(user: Charity) {
        if (user.email.isNotEmpty()){  binding.editTextAddCharityEmail.setText(user.email) }
        if (user.name.isNotEmpty()){  binding.editTextAddCharityName.setText(user.name) }
        if (user.founder.isNotEmpty()){  binding.editTextAddCharityFounder.setText(user.founder) }
        if (user.mobile.isNotEmpty()){  binding.editTextAddCharityMobile.setText(user.mobile) }
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.editTextAddCharityEmail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateEmail()
        }
        binding.editTextAddCharityName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateName()
        }
        binding.editTextAddCharityFounder.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateFounder()
        }
        binding.editTextAddCharityMobile.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateMobile()
        }
    }

    //Check email textField and handle use cases
    private fun validateEmail() {
        val email = binding.editTextAddCharityEmail
        val (result, isValid) = UserHelper()
            .emailVerification(email.text.toString())
        handleTextFields(email,result.string,0,isValid)
    }

    private fun validateMobile() {
        val mobile = binding.editTextAddCharityMobile
        val (result, isValid) = UserHelper().mobileValidation(mobile.text.toString())
        handleTextFields(mobile,result.string,1,isValid)
    }

    private fun validateName() {
        val string = binding.editTextAddCharityName
        val (result, isValid) = UserHelper().fieldVerification(string.text.toString())
        handleTextFields(string,result.string,2,isValid)
    }

    private fun validateFounder() {
        val string = binding.editTextAddCharityFounder
        val (result, isValid) = UserHelper().fieldVerification(string.text.toString())
        handleTextFields(string,result.string,3,isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            v.error = msg
        } else {
            v.error = null
            when (index){
                0 -> viewModel.setCharityEmail(v.text.toString())
                1 -> viewModel.setCharityMobile(v.text.toString())
                2 -> viewModel.setCharityName(v.text.toString())
                3 -> viewModel.setCharityFounder(v.text.toString())
                else -> return
            }
        }
    }

}