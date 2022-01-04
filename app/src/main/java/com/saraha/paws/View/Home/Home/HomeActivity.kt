package com.saraha.paws.View.Home.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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
import com.saraha.paws.Util.AppSharedPreference
import com.saraha.paws.View.SplashView.MainSplash.SplashActivity
import com.saraha.paws.View.AccountViews.Profile.ProfileFragment
import com.saraha.paws.View.ShowFacts.DisplayFactsFragment
import com.saraha.paws.View.AnimalViews.ViewAnimals.ViewAnimalsFragment
import com.saraha.paws.View.CharityViews.ViewCharities.ViewCharitiesFragment
import com.saraha.paws.View.Home.HomeServices.HomeServicesFragment
import com.saraha.paws.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding: ActivityHomeBinding

    val sharedPref = AppSharedPreference()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        getUserInformation()

        setupToolbarAndSliderDrawer(savedInstanceState)

        setContentView(binding.root)
    }

    //Function to get user information from Firestore and save in SharedPref
    private fun getUserInformation(){
        viewModel.getUserDataFromFirebase()

        viewModel.livedataUser.observe(this){
            if (it.name.isNotEmpty()){
                it.photoUrl?.let { photo -> sharedPref.write("pName", photo) }
            }
        }
    }

    //Function to setup Toolbar and Slider Menu
    private fun setupToolbarAndSliderDrawer(savedInstanceState: Bundle?) {
        val mainToolbar = binding.toolbarHome
        setSupportActionBar(mainToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(false)

        mainToolbar.setNavigationIcon(R.drawable.ic_menu_24)

        setSlider(savedInstanceState, mainToolbar)
    }

    //Function to set Slider menu properties
    private fun setSlider(savedInstanceState: Bundle?, mainToolbar: Toolbar) {
        val mainSlider = binding.homeSlider

        mainSliderContent(mainSlider, savedInstanceState)
        mainSlider.onDrawerItemClickListener = { _, drawerItem, _ ->
            when(drawerItem.identifier){
                0.toLong() -> {
                    displayFragment(HomeServicesFragment())
                    mainToolbar.title = "Rescues"
                }
                1.toLong() -> {
                    displayFragment(ProfileFragment())
                    mainToolbar.title = "Profile"
                }
                2.toLong() -> {
                    displayFragment(ViewCharitiesFragment())
                    mainToolbar.title = "Charities"
                }
                3.toLong() -> {
                    displayFragment(DisplayFactsFragment())
                    mainToolbar.title = "Fun Facts"
                }
                7.toLong() -> signOutFromAccount()
                else -> {
                    displayFragment(HomeServicesFragment())
                    mainToolbar.title = "Rescues"
                }
            }
            false
        }
        mainSlider.setSelection(0)

        mainToolbar.setNavigationOnClickListener {
            mainSlider.drawerLayout?.open()
        }
    }

    //Function to set Slider menu content
    private fun mainSliderContent(mainSlider: MaterialDrawerSliderView, savedInstance: Bundle?) {
        mainSliderHeader(mainSlider, savedInstance)

        val itemHome = PrimaryDrawerItem().apply {
            nameRes = R.string.resuces_home; iconRes = R.drawable.home_rescue_icon; identifier = 0
        }
        val itemProfile = PrimaryDrawerItem().apply {
            nameRes = R.string.profile_home; iconRes = R.drawable.home_profile_icon; identifier = 1
        }
        val itemCharity = PrimaryDrawerItem().apply {
            nameRes = R.string.charity_home; iconRes = R.drawable.home_charity_icon; identifier = 2
        }
        val itemFacts = SecondaryDrawerItem().apply {
            nameRes = R.string.fun_fact_home; iconRes = R.drawable.home_facts_icon; identifier = 3
        }
//        val itemAlbum = SecondaryDrawerItem().apply {
//            nameRes = R.string.photo_album_home; iconRes = R.drawable.ic_album_24; identifier = 4
//        }
        val itemSignOut = SecondaryDrawerItem().apply {
            nameRes = R.string.sign_out_home; iconRes = R.drawable.home_signout_icon; identifier = 7
        }

        mainSlider.itemAdapter.add(
            itemHome, itemProfile, itemCharity,
            DividerDrawerItem(),
            itemFacts,
            DividerDrawerItem(),
            itemSignOut
        )
    }

    //Function to set slider menu header content
    private fun mainSliderHeader(mainSlider: MaterialDrawerSliderView, savedInstanceState: Bundle?){
        val currentUser = Firebase.auth.currentUser
        mainSlider.headerView = AccountHeaderView(this).apply {
            attachToSliderView(mainSlider) // attach to the slider
            addProfiles(
                ProfileDrawerItem().apply {
                    descriptionText = currentUser?.email ?: ""; identifier = 102
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

    //Function to replace a fragment view
    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.HomeFrameLayout, fragment).commit()
    }
}