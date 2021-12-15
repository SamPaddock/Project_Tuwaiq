package com.saraha.paws.View.SplashView.Splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saraha.paws.View.AccountViews.LoginAccount.LoginActivity
import com.saraha.paws.View.AccountViews.RegisterAccount.RegisterActivity
import com.saraha.paws.databinding.ActivitySplashBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        binding.buttonRedirectToLogin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        binding.buttonRedirectToRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        setContentView(binding.root)
    }
}