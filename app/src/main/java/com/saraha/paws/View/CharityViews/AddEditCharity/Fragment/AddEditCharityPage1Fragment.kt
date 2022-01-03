package com.saraha.paws.View.CharityViews.AddEditCharity.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.Charity
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityViewModel
import com.saraha.paws.databinding.FragmentAddEditCharityPage1Binding
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.core.widget.addTextChangedListener
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityActivity
import com.squareup.picasso.Picasso

class AddEditCharityPage1Fragment : Fragment() {
    //View model and binding lateinit property
    private lateinit var viewModel: AddEditCharityViewModel
    lateinit var binding: FragmentAddEditCharityPage1Binding
    //image data lateinit property
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

    //Function to select a photo from device
    fun selectPhoto(){
        binding.floatingActionButtonAddCharityPhoto.setOnClickListener {
            val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(takePictureIntent, 2)
        }
    }

    //Function to handle response from action result
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
        if (user.photo.isNotEmpty()){ Picasso.get().load(user.photo).into(binding.imageViewCharityPhoto)}
        if (user.email.isNotEmpty()){  binding.editTextAddCharityEmail.setText(user.email) }
        if (user.name.isNotEmpty()){  binding.editTextAddCharityName.setText(user.name) }
        if (user.founder.isNotEmpty()){  binding.editTextAddCharityFounder.setText(user.founder) }
        if (user.mobile.isNotEmpty()){  binding.editTextAddCharityMobile.setText(user.mobile) }
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.editTextAddCharityEmail.addTextChangedListener {
            viewModel.validateEmail(binding.editTextAddCharityEmail,0)
        }
        binding.editTextAddCharityMobile.addTextChangedListener {
            viewModel.validateMobile(binding.editTextAddCharityMobile, 1)
        }
        binding.editTextAddCharityName.addTextChangedListener {
            viewModel.validateText(binding.editTextAddCharityName, 2)
        }
        binding.editTextAddCharityFounder.addTextChangedListener {
            viewModel.validateText(binding.editTextAddCharityFounder, 3)
        }
    }

}