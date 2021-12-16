package com.saraha.paws.View.AccountViews.Profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.saraha.paws.Model.User
import com.saraha.paws.R
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityActivity
import com.saraha.paws.View.AccountViews.EditProfile.EditProfileActivity
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    lateinit var binding: FragmentProfileBinding
    lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as HomeActivity)[ProfileViewModel::class.java]

        setHasOptionsMenu(true)

        binding.fabCreateCharity.setOnClickListener {
            val intent = Intent(this.context, AddEditCharityActivity::class.java)
            intent.putExtra("type", "Add")
            this.context?.startActivity(intent)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        getUserInformation()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu_item,menu)
        menu.findItem(R.id.edit_item)?.setOnMenuItemClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
            true
        }
        super.onCreateOptionsMenu(menu,inflater)
    }

    private fun getUserInformation(){
        viewModel.getUserDataFromFirebase()

        viewModel.livedataUser.observe(viewLifecycleOwner){
            if (it.email.isNotEmpty()){
                user = it
                setUserData(user)
            }
        }
    }

    private fun setUserData(user: User) {
        binding.textViewProfileEmail.setText(user.email)
        binding.textViewProfileGroup.setText(user.group)
        binding.textViewProfileMobile.setText(user.mobile)
        binding.textViewProfileName.setText(user.name)
        if (user.photoUrl?.trim()?.isNotEmpty() == true){
            Picasso.get().load(user.photoUrl).placeholder(R.mipmap.ic_launcher)
                .into(binding.imageViewUserProfile)
        }
    }

}