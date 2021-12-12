package com.saraha.paws.View.Profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.saraha.paws.Model.User
import com.saraha.paws.R
import com.saraha.paws.View.AddEditCharity.AddEditCharityActivity
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.View.RegisterAccount.RegisterActivity
import com.saraha.paws.View.RegisterAccount.RegisterViewModel
import com.saraha.paws.databinding.FragmentProfileBinding
import com.saraha.paws.databinding.FragmentRegisterPage1Binding
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as HomeActivity)[ProfileViewModel::class.java]

        getUserInformation()

        binding.fabCreatCharity.setOnClickListener {
            val intent = Intent(this.context, AddEditCharityActivity::class.java)
            intent.putExtra("type", "Add")
            this.context?.startActivity(intent)
        }

        return binding.root
    }

    private fun getUserInformation(){
        viewModel.getUserDataFromFirebase()

        viewModel.livedataUser.observe(viewLifecycleOwner){
            if (it.email.isNotEmpty()){
                setUserData(it)
            }
        }
    }

    private fun setUserData(user: User) {
        binding.textViewProfileEmail.setText(user.email)
        binding.textViewProfileGroup.setText(user.group)
        binding.textViewProfileMobile.setText(user.mobile)
        binding.textViewProfileName.setText(user.name)
        Picasso.get().load(user.photoUrl).placeholder(R.mipmap.ic_launcher)
            .into(binding.imageViewUserProfile)
    }

}