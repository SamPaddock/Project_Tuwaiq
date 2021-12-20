package com.saraha.paws.View.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import com.saraha.paws.Util.NetworkStatus
import com.saraha.paws.View.SplashView.MainSplash.SplashActivity
import com.saraha.paws.View.AccountViews.Profile.ProfileFragment
import com.saraha.paws.View.ShowFacts.DisplayFactsFragment
import com.saraha.paws.View.AnimalViews.ViewAnimals.ViewAnimalsFragment
import com.saraha.paws.View.CharityViews.ViewCharities.ViewCharitiesFragment
import com.saraha.paws.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels()
    lateinit var binding: ActivityHomeBinding

    val sharedPref = AppSharedPreference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        isNetworkAvailable()

        getUserInformation()

        displayFragment(ViewAnimalsFragment())

        setupToolbarAndSliderDrawer(savedInstanceState)

        setContentView(binding.root)
    }

    //Function to check network status and show/hide noNetwork view
    private fun isNetworkAvailable(): Boolean {
        return if (!NetworkStatus().isOnline(this)) {
            binding.HomeFrameLayout.visibility = View.GONE
            binding.noConnectionLayout.visibility = View.VISIBLE
            false
        } else {
            binding.HomeFrameLayout.visibility = View.VISIBLE
            binding.noConnectionLayout.visibility = View.GONE
            true
        }
    }

    //Function to get user information from Firestore and save in SharedPref
    private fun getUserInformation(){
        viewModel.getUserDataFromFirebase()

        viewModel.livedataUser.observe(this){
            if (it.email.isNotEmpty()){
                sharedPref.write("uName", it.name)
                sharedPref.write("eName", it.email)
                sharedPref.write("mName", it.mobile)
                sharedPref.write("tName", it.type)
                sharedPref.write("gName", it.group)
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

    //Function to set Slider menu content
    private fun mainSliderContent(mainSlider: MaterialDrawerSliderView, savedInstance: Bundle?) {
        mainSliderHeader(mainSlider, savedInstance)

        val itemHome = PrimaryDrawerItem().apply {
            nameRes = R.string.resuces_home; iconRes = R.drawable.ic_pets_type; identifier = 0
        }
        val itemProfile = PrimaryDrawerItem().apply {
            nameRes = R.string.profile_home; iconRes = R.drawable.ic_person_24; identifier = 1
        }
        val itemCharity = PrimaryDrawerItem().apply {
            nameRes = R.string.charity_home; iconRes = R.drawable.ic_charity_24; identifier = 2
        }
        val itemFacts = SecondaryDrawerItem().apply {
            nameRes = R.string.fun_fact_home; iconRes = R.drawable.ic_fact_24; identifier = 3
        }
        val itemAlbum = SecondaryDrawerItem().apply {
            nameRes = R.string.photo_album_home; iconRes = R.drawable.ic_album_24; identifier = 4
        }
        val itemSignOut = SecondaryDrawerItem().apply {
            nameRes = R.string.sign_out_home; iconRes = R.drawable.ic_exit_24; identifier = 7
        }

        mainSlider.itemAdapter.add(
            itemHome, itemProfile, itemCharity,
            DividerDrawerItem(),
            itemFacts, itemAlbum,
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
        if (!isNetworkAvailable()){
            binding.buttonRefreshMain.setOnClickListener {
                displayFragment(fragment)
            }
        } else {
            supportFragmentManager.beginTransaction().replace(R.id.HomeFrameLayout, fragment).commit()
        }
    }
}