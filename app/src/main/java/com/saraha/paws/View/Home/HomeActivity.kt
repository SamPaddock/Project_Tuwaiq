package com.saraha.paws.View.Home

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.descriptionText
import com.mikepenz.materialdrawer.model.interfaces.iconRes
import com.mikepenz.materialdrawer.model.interfaces.nameRes
import com.mikepenz.materialdrawer.widget.AccountHeaderView
import com.mikepenz.materialdrawer.widget.MaterialDrawerSliderView
import com.saraha.paws.R
import com.saraha.paws.View.MainSplash.SplashActivity
import com.saraha.paws.View.Profile.ProfileFragment
import com.saraha.paws.View.ShowFacts.DisplayFactsFragment
import com.saraha.paws.View.ViewAnimals.ViewAnimalsFragment
import com.saraha.paws.View.ViewCharities.ViewCharitiesFragment
import com.saraha.paws.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setupToolbarAndSliderDrawer(savedInstanceState)

        setContentView(binding.root)
    }

    private fun setupToolbarAndSliderDrawer(savedInstanceState: Bundle?) {
        val mainToolbar = binding.toolbarHome
        setSupportActionBar(mainToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(false)

        mainToolbar.setNavigationIcon(R.drawable.ic_menu_24)

        setSlider(savedInstanceState, mainToolbar)
    }

    private fun setSlider(savedInstanceState: Bundle?, mainToolbar: Toolbar) {
        val mainSlider = binding.homeSlider

        mainSliderContent(mainSlider, savedInstanceState)
        mainSlider.onDrawerItemClickListener = { _, drawerItem, _ ->
            Log.d(TAG,"HomeActivity: - setSlider: - : ${drawerItem.identifier}")
            when(drawerItem.identifier){
                0.toLong() -> displayFragment(ViewAnimalsFragment())
                1.toLong() -> displayFragment(ProfileFragment())
                2.toLong() -> displayFragment(ViewCharitiesFragment())
                3.toLong() -> displayFragment(DisplayFactsFragment())
                7.toLong() -> signOutFromAccount()
                else -> displayFragment(ViewAnimalsFragment())
            }
            false
        }
        mainToolbar.setNavigationOnClickListener {
            mainSlider.drawerLayout?.open()
        }
    }

    private fun mainSliderContent(mainSlider: MaterialDrawerSliderView, savedInstance: Bundle?) {
        mainSliderHeader(mainSlider, savedInstance)

        val itemHome = PrimaryDrawerItem().apply {
            nameRes = R.string.resuces_home
            iconRes = R.drawable.ic_pets_type
            identifier = 0
        }
        val itemProfile = PrimaryDrawerItem().apply {
            nameRes = R.string.profile_home
            iconRes = R.drawable.ic_person_24
            identifier = 1
        }
        val itemCharity = PrimaryDrawerItem().apply {
            nameRes = R.string.charity_home
            iconRes = R.drawable.ic_charity_24
            identifier = 2
        }
        val itemFacts = SecondaryDrawerItem().apply {
            nameRes = R.string.fun_fact_home
            iconRes = R.drawable.ic_fact_24
            identifier = 3
        }
        val itemAlbum = SecondaryDrawerItem().apply {
            nameRes = R.string.photo_album_home
            iconRes = R.drawable.ic_album_24
            identifier = 4
        }
        val itemSignOut = SecondaryDrawerItem().apply {
            nameRes = R.string.sign_out_home
            iconRes = R.drawable.ic_exit_24
            identifier = 7
        }

        mainSlider.itemAdapter.add(
            itemHome,
            itemProfile,
            itemCharity,
            DividerDrawerItem(),
            itemFacts,
            itemAlbum,
            DividerDrawerItem(),
            itemSignOut
        )
    }

    private fun mainSliderHeader(mainSlider: MaterialDrawerSliderView, savedInstanceState: Bundle?){
        val currentUser = Firebase.auth.currentUser
        mainSlider.headerView = AccountHeaderView(this).apply {
            attachToSliderView(mainSlider) // attach to the slider
            addProfiles(
                ProfileDrawerItem().apply {
                    descriptionText = currentUser?.email ?: ""
                    identifier = 102
                }
            )
            onAccountHeaderListener = { view, profile, current ->
                displayFragment(ProfileFragment())
                false
            }
            withSavedInstance(savedInstanceState)
        }
    }

    //Function to signOut of application
    private fun signOutFromAccount(){
        Firebase.auth.signOut()
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.HomeFrameLayout, fragment).commit()
    }
}