package com.saraha.paws.View.EditProfile

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.User
import com.saraha.paws.R
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.databinding.ActivityEditProfileBinding
import com.squareup.picasso.Picasso

class EditProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityEditProfileBinding
    lateinit var user: User
    lateinit var list: List<String>
    lateinit var imgData: Uri
    val viewModel: EditProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)

        val data = intent.getSerializableExtra("user")
        Log.d(TAG,"EditProfileActivity: - onCreate: - : ${data}")
        if (data != null) {
            user = data as User
            Log.d(TAG,"EditProfileActivity: - onCreate: - : ${data.email}")
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

    private fun verifyCharityFormFields() {
        if (user.email.isNotEmpty() && user.name.isNotEmpty() && user.mobile.isNotEmpty()
            && user.group.isNotEmpty() && user.photoUrl?.isNotEmpty() == true) {
            viewModel.setPhotoInFireStorage(user.photoUrl!!)
            viewModel.postedPhotoLiveData.observe(this) {
                if (it.isNotEmpty()) {
                    updateUserInformation(it)
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.all_required), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserInformation(photo: String) {
        viewModel.editUserInFirebase(
            hashMapOf(
                "name" to user.name,
                "email" to user.email,
                "mobile" to user.mobile,
                "group" to user.group,
                "photoUrl" to photo
            )
        )

        viewModel.editUserLiveData.observe(this){
            if (it){
                Toast.makeText(
                    this,
                    getString(R.string.successful_edit_user),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.failure_edit_user),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

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

    //Set onOutOfFocus on textFields
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

    private fun validateMobile() {
        val mobile = binding.editTextLayoutEditProfileMobile
        val (result, isValid) = UserHelper().mobileValidation(mobile.text.toString())
        handleTextFields(mobile,result.string,1,isValid)
    }

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

    private fun setValue(user: User) {
        Log.d(TAG,"EditProfileActivity: - onCreate: - : ${user.email}")
        binding.editTextEditProfileEmail.setText(user.email)
        binding.editTextEditProfileName.setText(user.name)
        binding.editTextLayoutEditProfileMobile.setText(user.mobile)
        binding.autoCompleteEditGroup.setText(user.group)
        if (user.photoUrl?.trim()?.isNotEmpty() == true) Picasso.get().load(user.photoUrl)
            .into(binding.imageViewEditProfile)
    }
}