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
import com.saraha.paws.R
import com.saraha.paws.Util.NetworkStatus
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.Util.getStringAddress
import com.saraha.paws.Util.toast
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityActivity
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityViewModel
import com.saraha.paws.View.MapView.MapsActivity
import com.saraha.paws.databinding.FragmentAddEditCharityPage2Binding


class AddEditCharityPage2Fragment : Fragment() {
    //View model and binding lateinit property
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

        //Open map activity with pending results from activity
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

        val location = LatLng(charity.latitude, charity.longitude)
        binding.editTextAddCharityLocation.setText(location.getStringAddress(this.requireContext()))
    }

    //Function to handle response from action result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5 && resultCode == Activity.RESULT_OK && data != null){
            val lat = data.getDoubleExtra("Lat", 0.0)
            val lon = data.getDoubleExtra("Lon", 0.0)

            val geoCoder = Geocoder(this.requireContext()).getFromLocation(lat, lon, 1)
            val address = geoCoder[0].getAddressLine(0)
            binding.editTextAddCharityLocation.setText(address)

            viewModel.setCharityLocation(LatLng(lat, lon))
        } else {
            this.requireContext().toast(getString(R.string.address_error))
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