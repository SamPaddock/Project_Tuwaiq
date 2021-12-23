package com.saraha.paws.View.SplashView.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.CallbackManager
import com.saraha.paws.View.AccountViews.LoginAccount.LoginActivity
import com.saraha.paws.View.AccountViews.RegisterAccount.RegisterActivity
import com.saraha.paws.databinding.ActivitySplashBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        //Handle on login click
        binding.buttonRedirectToLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        //Handle on register click
        binding.buttonRedirectToRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        setContentView(binding.root)
    }

}