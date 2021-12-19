package com.saraha.paws.View.AccountViews.EditProfile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.User
import com.saraha.paws.R
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.Util.*
import com.saraha.paws.databinding.ActivityEditProfileBinding
import com.squareup.picasso.Picasso

class EditProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditProfileBinding
    lateinit var user: User
    lateinit var list: List<String>
    lateinit var imgData: Uri
    val viewModel: EditProfileViewModel by viewModels()

    val sharedPref = AppSharedPreference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        //get data from intent and check if null then set in view
        val data = intent.getSerializableExtra("user")
        if (data != null) {
            user = data as User
            setValue(user)
        }

        setGroupDropDownMenu()

        onFieldFocus()

        binding.fabUploadNewImage.setOnClickListener {
            val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(takePictureIntent, 2)
        }

        binding.buttonSaveProfileEdit.setOnClickListener {
            verifyCharityFormFields()
        }

        setContentView(binding.root)
    }

    //Function the verifies data inputted by user, then uploading image in FireStorage
    private fun verifyCharityFormFields() {
        if (user.isAllDataEmpty()) {
            viewModel.setPhotoInFireStorage(user.photoUrl!!)
            viewModel.postedPhotoLiveData.observe(this) {
                if (it.isNotEmpty()) { updateUserInformation(it) }
            }
        } else {
            this.toast(getString(R.string.all_required))
        }
    }

    //Function to update user data in FireStore
    private fun updateUserInformation(photo: String) {
        viewModel.editUserInFirebase(user.getHashMap(photo))

        viewModel.editUserLiveData.observe(this){
            if (it){
                sharedPref.write("uName", user.name)
                sharedPref.write("eName", user.email)
                sharedPref.write("mName", user.mobile)
                sharedPref.write("tName", user.type)
                sharedPref.write("gName", user.group)
                sharedPref.write("pName", user.photoUrl!!)
                this.toast(getString(R.string.successful_edit_user))
                finish()
            } else {
                this.toast(getString(R.string.failure_edit_user))
                finish()
            }
        }
    }

    //Function to handle response to activity result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            2 -> if (resultCode == RESULT_OK) {
                imgData = data?.data!!
                binding.imageViewEditProfile.setImageURI(imgData)
                user.photoUrl = imgData.toString()
            }
        }
    }

    //Function to set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.editTextEditProfileEmail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateEmail()
        }
        binding.editTextEditProfileName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateName()
        }
        binding.editTextLayoutEditProfileMobile.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) validateMobile()
        }
        binding.autoCompleteEditGroup.setOnItemClickListener { parent, view, position, id ->
            user.group = list[position]
        }
    }

    //Function to set data in dropdown menu
    private fun setGroupDropDownMenu() {
        viewModel.setGroupData().observe(this) {
            //Set dropdown list adapter with list of charity groups
            list = it
            binding.autoCompleteEditGroup
                .setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, list))
        }
    }

    //Check email textField and handle use cases
    private fun validateEmail() {
        val email = binding.editTextEditProfileEmail
        val (result, isValid) = UserHelper()
            .emailVerification(email.text.toString())
        handleTextFields(email,result.string,0,isValid)
    }

    //Check mobile textField and handle use cases
    private fun validateMobile() {
        val mobile = binding.editTextLayoutEditProfileMobile
        val (result, isValid) = UserHelper().mobileValidation(mobile.text.toString())
        handleTextFields(mobile,result.string,1,isValid)
    }

    //Check name textField and handle use cases
    private fun validateName() {
        val name = binding.editTextEditProfileName
        val (result, isValid) = UserHelper().fieldVerification(name.text.toString())
        handleTextFields(name,result.string,2,isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            v.error = msg
        } else {
            v.error = null
            when (index){
                0 -> user.email = v.text.toString()
                1 -> user.mobile = v.text.toString()
                2 -> user.name = v.text.toString()
                else -> return
            }
        }
    }

    //Function to set data from intent to textFields
    private fun setValue(user: User) {
        binding.editTextEditProfileEmail.setText(user.email)
        binding.editTextEditProfileName.setText(user.name)
        binding.editTextLayoutEditProfileMobile.setText(user.mobile)
        binding.autoCompleteEditGroup.setText(user.group)
        if (user.photoUrl?.trim()?.isNotEmpty() == true) Picasso.get().load(user.photoUrl)
            .into(binding.imageViewEditProfile)
    }
}