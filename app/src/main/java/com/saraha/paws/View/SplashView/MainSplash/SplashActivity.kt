package com.saraha.paws.View.SplashView.MainSplash

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saraha.paws.R
import com.saraha.paws.Util.AppSharedPreference
import com.saraha.paws.Util.NetworkStatus
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.View.SplashView.Splash.MainActivity
import com.saraha.paws.databinding.ActivityMainBinding
import com.saraha.paws.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var networkDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Initialize Shared Preference
        AppSharedPreference.init(applicationContext)

        checkNetworkStatus()

        setContentView(binding.root)
    }

    private fun checkNetworkStatus() {
        if (NetworkStatus().isOnline(this)) {
            startApp()
        } else {
            networkMissingDialog()
        }
    }

    private fun networkMissingDialog(){
        binding.LoadingDotsMain.visibility = View.GONE

        networkDialog = AlertDialog.Builder(this).setTitle("No internet Connection")
            .setMessage("Please turn on internet connection to continue")
            .setNeutralButton("Refresh") { dialog, which -> checkNetworkStatus() }
        networkDialog.create().show()
    }

    private fun startApp(){
        binding.LoadingDotsMain.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            //Verify if user has logged in before then redirect to home or login activity
            if (Firebase.auth.currentUser?.uid?.isNotEmpty() == true){
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, 2000)
    }
}