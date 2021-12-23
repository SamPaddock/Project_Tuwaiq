package com.saraha.paws.View.AccountViews.RegisterAccount.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.saraha.paws.Model.User
import com.saraha.paws.Util.UserHelper
import com.saraha.paws.View.AccountViews.RegisterAccount.RegisterActivity
import com.saraha.paws.View.AccountViews.RegisterAccount.RegisterViewModel
import com.saraha.paws.databinding.FragmentRegisterPage1Binding

class RegisterPage1Fragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    lateinit var binding: FragmentRegisterPage1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterPage1Binding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as RegisterActivity)[RegisterViewModel::class.java]

        //Get passed data from activity to set in fragment
        val user = this.arguments?.getSerializable("user")
        if (user != null) setValues(user as User)

        onFieldFocus()

        return binding.root
    }

    //Function to set User input when returning to fragment
    private fun setValues(user: User) {
        if (user.email.isNotEmpty()){  binding.editTextRegisterEmail.setText(user.email) }
    }

    //Set onOutOfFocus on textFields
    private fun onFieldFocus(){
        binding.edittextRegisterConfirmPassword.addTextChangedListener {
            viewModel.validateConfirmPassword(
                binding.edittextRegisterConfirmPassword, binding.edittextRegisterPassword, 2
            )
        }
        binding.edittextRegisterPassword.addTextChangedListener {
            viewModel.validatePassword(binding.edittextRegisterPassword, 1)
        }
        binding.editTextRegisterEmail.addTextChangedListener {
            viewModel.validateEmail(binding.editTextRegisterEmail, 0)
        }
    }
}