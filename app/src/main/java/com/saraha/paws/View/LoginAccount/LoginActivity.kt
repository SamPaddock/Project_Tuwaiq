package com.saraha.paws.View.LoginAccount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.saraha.paws.R
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.View.RegisterAccount.RegisterViewModel
import com.saraha.paws.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.buttonLogin.setOnClickListener {
            if (checkFields()) onLoginButtonClick()
        }

        setContentView(binding.root)
    }

    private fun checkFields(): Boolean {
        val email = binding.editTextLoginEmail
        val password = binding.editTextLoginPassword
        val warningTextField = binding.textViewLoginWarning
        if (email.text?.isNotEmpty() == true && password.text?.isNotEmpty() == true){
            warningTextField.setText("")
            return true
        } else {
            warningTextField.setText(getString(R.string.login_required))
            return false
        }
    }

    private fun onLoginButtonClick(){
        val email = binding.editTextLoginEmail.text.toString()
        val password = binding.editTextLoginPassword.text.toString()

        viewModel.signInUserInFirebase(email,password)

        viewModel.loginInResponseLiveData.observe(this){
            if (it){
                binding.textViewLoginWarning.setText("")
                this.startActivity(Intent(this,HomeActivity::class.java))
                this.finish()
            } else {
                binding.textViewLoginWarning.setText(getString(R.string.login_warning))
            }
        }

    }
}