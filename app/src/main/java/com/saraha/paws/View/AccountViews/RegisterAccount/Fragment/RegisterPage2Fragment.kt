package com.saraha.paws.View.AccountViews.RegisterAccount.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.User
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.AccountViews.RegisterAccount.RegisterActivity
import com.saraha.paws.View.AccountViews.RegisterAccount.RegisterViewModel
import com.saraha.paws.databinding.FragmentRegisterPage2Binding

class RegisterPage2Fragment : Fragment() {
    //View model and binding lateinit property
    private lateinit var viewModel: RegisterViewModel
    lateinit var binding: FragmentRegisterPage2Binding
    //list that holds the dropdown menu items
    lateinit var list: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterPage2Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as RegisterActivity)[RegisterViewModel::class.java]

        //Get passed data from activity to set in fragment
        val user = this.getArguments()?.getSerializable("user")
        if (user != null) setValues(user as User)

        onFieldFocus()

        viewModel.setGroupData().observe(this){
            //Set dropdown list adapter with list of charity groups
            list = it
            binding.autoCompleteRegisterGroup
                .setAdapter(ArrayAdapter(context!!, android.R.layout.simple_list_item_1, list))
        }

        return binding.root
    }

    //Function to set User input when returning to fragment
    private fun setValues(user: User) {
        if (user.name.isNotEmpty()){  binding.edittextRegisterName.setText(user.name) }
        if (user.mobile.isNotEmpty()){  binding.edittextRegisterMobile.setText(user.mobile) }
        if (user.group.isNotEmpty()){  binding.autoCompleteRegisterGroup.setText(user.group) }
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.edittextRegisterName.addTextChangedListener {
            viewModel.validateName(binding.edittextRegisterName, 3)
        }
        binding.edittextRegisterMobile.addTextChangedListener {
            viewModel.validateMobile(binding.edittextRegisterMobile, 4)
        }
        binding.autoCompleteRegisterGroup.setOnItemClickListener { _, _, position, _ ->
            viewModel.setGroupFromPage2(list.get(position))
        }
    }
}