package com.saraha.paws.View.CharityViews.AddEditCharity.Fragment

import android.app.Activity
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.Charity
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityActivity
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityViewModel
import com.saraha.paws.View.MapView.MapsActivity
import com.saraha.paws.databinding.FragmentAddEditCharityPage2Binding


class AddEditCharityPage2Fragment : Fragment() {

    private lateinit var viewModel: AddEditCharityViewModel
    lateinit var binding: FragmentAddEditCharityPage2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEditCharityPage2Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as AddEditCharityActivity)[AddEditCharityViewModel::class.java]

        //Get passed data from activity to set in fragment
        val charity = this.arguments?.getSerializable("charity")
        if (charity != null) setValues(charity as Charity)

        onFieldFocus()

        binding.imageViewAddCharityLocation.setOnClickListener {
            val intent = Intent(this.requireContext(), MapsActivity::class.java)
            startActivityForResult(intent, 5)
        }

        return binding.root
    }

    //Function to set User input when returning to fragment
    private fun setValues(charity: Charity) {
        if (charity.stcPay.isNotEmpty()){  binding.editTextAddCharityPay.setText(charity.stcPay) }
        if (charity.facebookUrl.isNotEmpty()){  binding.editTextAddCharityFacebook.setText(charity.facebookUrl) }
        if (charity.instagramUrl.isNotEmpty()){  binding.editTextAddCharityInstagram.setText(charity.instagramUrl) }
    }

    //Function to handle response from action result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5 && resultCode == Activity.RESULT_OK){
            val lat = data?.getDoubleExtra("Lat", 0.0) ?: 0.0
            val lon = data?.getDoubleExtra("Lon", 0.0)  ?: 0.0

            val geoCoder = Geocoder(this.requireContext()).getFromLocation(lat, lon, 1)
            val address = geoCoder[0].getAddressLine(0)
            binding.editTextAddCharityLocation.setText(address)
            viewModel.setCharityLocation(LatLng(lat, lon))
        }
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.editTextAddCharityPay.addTextChangedListener {
            viewModel.validateMobile(binding.editTextAddCharityPay, 4)
        }
        binding.editTextAddCharityFacebook.addTextChangedListener {
            viewModel.validateLink(binding.editTextAddCharityFacebook, 5)
        }
        binding.editTextAddCharityInstagram.addTextChangedListener {
            viewModel.validateLink(binding.editTextAddCharityInstagram, 6)
        }
    }




}