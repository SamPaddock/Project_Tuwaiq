package com.saraha.paws.View.SplashView.MainSplash

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.saraha.paws.R
import com.saraha.paws.Util.*
import com.saraha.paws.Util.Helper.Companion.appPermissionList
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.View.SplashView.Splash.MainActivity
import com.saraha.paws.databinding.ActivityMainBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var networkDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Initialize Shared Preference
        AppSharedPreference.init(applicationContext)
        Log.d(TAG,"SplashActivity: - onCreate: - : ${AppSharedPreference.prefs}")

        checkNetworkStatus()

        checkLocationPermission()

        setContentView(binding.root)
    }

    private fun checkNetworkStatus() {
        if (NetworkStatus().isOnline(this) && this.hasPermissions(appPermissionList[0])) {
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

    private fun checkLocationPermission(){
        val fineLocation = this.hasPermissions(appPermissionList[0])
        val coarseLocation = this.hasPermissions(appPermissionList[1])

        if ( !fineLocation && !coarseLocation) {
            ActivityCompat.requestPermissions(this, appPermissionList, 4)
        } else {
            checkNetworkStatus()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 4){
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    getString(R.string.location_required_msg),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            checkNetworkStatus()
        }
    }
}