package com.saraha.paws.View.AccountViews.Profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.saraha.paws.Model.User
import com.saraha.paws.R
import com.saraha.paws.Util.AppSharedPreference
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityActivity
import com.saraha.paws.View.AccountViews.EditProfile.EditProfileActivity
import com.saraha.paws.View.Home.Home.HomeActivity
import com.saraha.paws.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    //View model and binding lateinit property
    private lateinit var viewModel: ProfileViewModel
    lateinit var binding: FragmentProfileBinding
    //Variables that will hold intent values
    lateinit var user: User
    //Shared preference helper class object
    val sharedPref = AppSharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as HomeActivity)[ProfileViewModel::class.java]

        setHasOptionsMenu(true)

        checkIfAdmin()
        //redirect to add charity activity which is only visible to an Admin user
        binding.fabCreateCharity.setOnClickListener {
            val intent = Intent(this.context, AddEditCharityActivity::class.java)
            intent.putExtra("type", "Add")
            this.context?.startActivity(intent)
        }

        return binding.root
    }

    //Function to show.hide add charity btn depending on user type
    private fun checkIfAdmin() {
        if (sharedPref.read("tName","")!! == "Admin"){
            binding.fabCreateCharity.visibility = View.VISIBLE
        } else {
            binding.fabCreateCharity.visibility = View.GONE
        }
    }

    //reload user info on resume
    override fun onResume() {
        super.onResume()
        user = viewModel.getUserInfo()
        setUserData(user)
    }

    //Function for an toolbar content and handler
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.edit_menu_item,menu)
        menu.findItem(R.id.edit_item_1)?.setOnMenuItemClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            intent.putExtra("user", user)
            startActivityForResult(intent, 8)
            true
        }
        super.onCreateOptionsMenu(menu,inflater)
    }

    //Function to set data from Firestore to textFields
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