package com.saraha.paws.View.AccountViews.EditProfile

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.User
import com.saraha.paws.R
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.Util.*
import com.saraha.paws.databinding.ActivityEditProfileBinding
import com.squareup.picasso.Picasso

class EditProfileActivity : AppCompatActivity() {
    //View model and binding lateinit property
    val viewModel: EditProfileViewModel by viewModels()
    lateinit var binding: ActivityEditProfileBinding
    //Variables that will hold intent values
    lateinit var user: User
    //Variables that hold dropdown menu content and image data from activity for result
    lateinit var list: List<String>
    lateinit var imgData: Uri
    //Variable is true if all textfields have validated and are correct
    var isUserValid = true
    //Shared preference helper class object
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

        setupToolbar()

        setGroupDropDownMenu()

        onFieldFocus()

        //intent to open photo and gallary for image selectoin
        binding.fabUploadNewImage.setOnClickListener {
            val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(takePictureIntent, 2)
        }

        binding.buttonSaveProfileEdit.setOnClickListener {
            verifyCharityFormFields()
        }

        setContentView(binding.root)
    }

    //Function to setup activity toolbar with title and back button
    private fun setupToolbar() {
        val mainToolbar = binding.toolbarEditProfile
        mainToolbar.title = "Edit Profile"
        mainToolbar.setNavigationIcon(R.drawable.ic_back_24)
        setSupportActionBar(mainToolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    //Function the verifies data inputted by user, then uploading image in FireStorage
    private fun verifyCharityFormFields() {
        if (user.isAllDataEmpty() && isUserValid) {
            binding.layoutEditView.visibility = View.VISIBLE
            binding.buttonSaveProfileEdit.isClickable = false
            if (!Patterns.WEB_URL.matcher(user.photoUrl!!).matches()){
                viewModel.setPhotoInFireStorage(user.photoUrl!!)
                viewModel.postedPhotoLiveData.observe(this) {
                    if (it.isNotEmpty()) { updateUserInformation(it) }
                }
            } else {
                updateUserInformation()
            }
        } else {
            binding.layoutEditView.visibility = View.GONE
            binding.buttonSaveProfileEdit.isClickable = true
            this.toast(getString(R.string.all_required))
        }
    }

    //Function to update user data in FireStore
    private fun updateUserInformation(photo: String? = user.photoUrl) {
        viewModel.editUserInFirebase(user.getHashMap(photo!!))

        viewModel.editUserLiveData.observe(this){
            binding.layoutEditView.visibility = View.GONE
            binding.buttonSaveProfileEdit.isClickable = true
            if (it.first){
                viewModel.setSharedPreference(user)
                this.toast(getString(R.string.successful_edit_user))
            } else {
                this.toast(getString(R.string.failure_edit_user))
                this.toast(it.second?.message.toString())
            }
            finish()
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
        binding.editTextEditProfileEmail.addTextChangedListener {
            validateEmail(binding.editTextEditProfileEmail, 0)
        }
        binding.editTextEditProfileName.addTextChangedListener {
            validateName(binding.editTextEditProfileName, 2)
        }
        binding.editTextLayoutEditProfileMobile.addTextChangedListener {
            validateMobile(binding.editTextLayoutEditProfileMobile, 1)
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
    private fun validateEmail(email: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().emailVerification(email.text.toString())
        handleTextFields(email, result.string, index, isValid)
    }

    //Check mobile textField and handle use cases
    private fun validateMobile(mobile: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().mobileValidation(mobile.text.toString())
        handleTextFields(mobile, result.string, index, isValid)
    }

    //Check name textField and handle use cases
    private fun validateName(name: TextInputEditText, index: Int) {
        val (result, isValid) = UserHelper().fieldVerification(name.text.toString())
        handleTextFields(name, result.string, index, isValid)
    }

    //Handle result of textField checks
    private fun handleTextFields(v: TextInputEditText, msg: String, index: Int, isValid: Boolean){
        if (!isValid){
            v.error = msg
            isUserValid = false
        } else {
            v.error = null
            isUserValid = true
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