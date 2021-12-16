package com.saraha.paws.View.SplashView.MainSplash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saraha.paws.R
import com.saraha.paws.Util.AppSharedPreference
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.View.SplashView.Splash.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppSharedPreference.init(applicationContext)

        Handler(Looper.getMainLooper()).postDelayed({
            if (Firebase.auth.currentUser?.uid?.isNotEmpty() == true){
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, 2000)

    }
}