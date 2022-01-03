package com.saraha.paws.View.AccountViews.LoginAccount

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.saraha.paws.R
import com.saraha.paws.Util.FirebaseExceptionMsg
import com.saraha.paws.Util.toast
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setupToolbar()

        binding.buttonLogin.setOnClickListener { if (checkFields()) onLoginButtonClick() }

        setContentView(binding.root)
    }

    //Function to setup activity toolbar with title and back button
    private fun setupToolbar() {
        val mainToolbar = binding.toolbarLogin
        mainToolbar.title = getString(R.string.welcome_msg)
        mainToolbar.setNavigationIcon(R.drawable.ic_back_24)
        setSupportActionBar(mainToolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    //Function the verifies login input
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

    //Function the verifies login credentials then login to application
    private fun onLoginButtonClick(){
        val email = binding.editTextLoginEmail.text.toString()
        val password = binding.editTextLoginPassword.text.toString()

        isLoginIn(true)
        viewModel.signInUserInFirebase(email,password)

        viewModel.loginInResponseLiveData.observe(this){
            Log.d(TAG,"LoginActivity: - onLoginButtonClick: - : ${it}")
            isLoginIn(false)
            if (it.first && it.second == null){
                binding.textViewLoginWarning.setText("")
                this.startActivity(Intent(this,HomeActivity::class.java))
                this.finish()
            } else {
                try {
                    throw it.second!!
                } catch (e: FirebaseNetworkException){
                    this.toast(FirebaseExceptionMsg.ERROR_NETWORK.reason, Toast.LENGTH_LONG)
                } catch (e: FirebaseAuthInvalidUserException) {
                    this.toast(e.message.toString())
                } catch (e: Exception){
                    binding.textViewLoginWarning.setText(getString(R.string.login_warning))
                }
            }
        }

    }

    //Function to handle view components when login button is clicked
    private fun isLoginIn(isLogin: Boolean) {
        binding.progressBarLogin.visibility = if (isLogin) View.VISIBLE else View.GONE
        binding.buttonLogin.isClickable = !isLogin
    }
}