package com.saraha.paws.View.CharityViews.ViewCharityDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saraha.paws.Model.Charity
import com.saraha.paws.R
import com.saraha.paws.databinding.ActivityViewCharityDetailBinding
import android.content.Intent
import android.net.Uri
import android.view.Menu
import androidx.activity.viewModels
import com.google.android.gms.maps.model.LatLng
import com.saraha.paws.Util.AppSharedPreference
import com.saraha.paws.Util.loadImage
import com.saraha.paws.Util.toast
import com.saraha.paws.View.CharityViews.AddEditCharity.AddEditCharityActivity
import java.lang.Exception


class ViewCharityDetailActivity : AppCompatActivity() {
    //View model and binding lateinit property
    val viewModel: ViewCharityDetailViewModel by viewModels()
    lateinit var binding: ActivityViewCharityDetailBinding
    lateinit var charity: Charity
    //Shared preference helper class object
    val sharedPref = AppSharedPreference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCharityDetailBinding.inflate(layoutInflater)
        //send data from intent, if not null, then display content and set toolbar
        val data = intent.getSerializableExtra("charity")
        if (data != null){
            charity = data as Charity
            setValues(charity)
            setupToolbar()
        } else {
            this.toast(getString(R.string.view_error_general))
            finish()
        }

        setContentView(binding.root)
    }
    //Function to set toolbar and back button
    private fun setupToolbar() {
        val mainToolbar = binding.toolbarViewCharity
        mainToolbar.title = charity.name
        mainToolbar.setNavigationIcon(R.drawable.ic_back_24)
        setSupportActionBar(mainToolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    //set up item menu depending on use type with on item click listener
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (sharedPref.read("tName","")!! == "Admin"){
            menuInflater.inflate(R.menu.edit_delete_menu_items,menu)
            val editItem = menu?.findItem(R.id.edit_item)
            editItem?.setOnMenuItemClickListener {
                redirectToEditContent()
                true
            }
            val deleteItem = menu?.findItem(R.id.delete_item)
            deleteItem?.setOnMenuItemClickListener {
                deleteCharity()
                true
            }
        } else {
            menuInflater.inflate(R.menu.edit_menu_item,menu)
            val editItem = menu?.findItem(R.id.edit_item_1)
            editItem?.setOnMenuItemClickListener {
                redirectToEditContent()
                true
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    //Function to handle response from action result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == RESULT_OK){
            charity = data?.getSerializableExtra("charity") as Charity
            setValues(charity)
        }
    }

    //Function to delete a charity from firebase
    private fun deleteCharity() {
        viewModel.deleteCharityFromFirebase(charity.cid!!)
        viewModel.deleteDocumentLiveData.observe(this){
            if (it) {
                this.toast(getString(R.string.successful_delete_charity))
                finish()
            } else {
                this.toast(getString(R.string.failure_delete_charity))
            }
        }
    }

    //Function to start Add/Edit activity with result on completion
    private fun redirectToEditContent() {
        val intent = Intent(this, AddEditCharityActivity::class.java)
        intent.putExtra("type", "Edit")
        intent.putExtra("charity", charity)
        startActivityForResult(intent, 10)
    }

    //Function to set data in textviews
    private fun setValues(charity: Charity) {
        //set values
        binding.textViewDisplayCharityEmail.text = charity.email
        binding.textViewDisplayCharityFounder.text = charity.founder
        binding.textViewDisplayCharityMobile.text = charity.mobile
        //set image
        binding.imageViewDisplayCharityPhoto.loadImage(charity.photo)
        //set image buttons
        binding.imageViewSTCPayLink.setOnClickListener { openSTCPay(charity.stcPay) }
        binding.imageViewFacebookLink.setOnClickListener { openFacebook(charity.facebookUrl) }
        binding.imageViewInstaLink.setOnClickListener { openInstagram(charity.instagramUrl) }
        binding.imageViewWhatsappLink.setOnClickListener { openWhatsapp(charity.mobile) }
        binding.imageViewMail.setOnClickListener { openMail(charity) }
        binding.imageViewMaps.setOnClickListener {
            openGoogleMaps(LatLng(charity.latitude,charity.longitude))
        }
    }

    //Function to handle to mail icon click
    private fun openMail(charity: Charity){
        // create an Intent to send data to mail
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(charity.email))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject_temp))
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_intro_temp))
        startActivity(intent)
    }

    //Function to handle to whatsapp icon click
    private fun openWhatsapp(mobile: String){
        // create an Intent to send data to the whatsapp
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")
        if (intent.resolveActivity(packageManager) != null) {
            val url = "https://api.whatsapp.com/send?phone=+$mobile"
            intent.data = Uri.parse(url)
            startActivity(intent)
        }else {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp"))
            startActivity(i)
        }
    }

    //Function to handle to facebook icon click
    private fun openFacebook(Url: String){
        // create an Intent to send data to the Facebook
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.facebook.katana")
        if (intent.resolveActivity(packageManager) != null) {
            try {
                intent.data = Uri.parse(Url)
                startActivity(intent)
            } catch (e: Exception){
                this.toast(getString(R.string.link_error))
            }
        }else {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(Url))
            startActivity(i)
        }

    }

    //Function to handle to instagram icon click
    private fun openInstagram(Url: String){
        // create an Intent to send data to the Instagram
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.instagram.android")
        if (intent.resolveActivity(packageManager) != null) {
            try {
                intent.data = Uri.parse(Url)
                startActivity(intent)
            } catch (e: Exception){
                this.toast(getString(R.string.link_error))
            }
        }else {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(Url))
            startActivity(i)
        }
    }

    //Function to handle to stcPay icon click
    private fun openSTCPay(mobile: String){
        // create an Intent to send data to the whatsapp
        val intent = getPackageManager().getLaunchIntentForPackage("sa.com.stcpay")
        if (intent != null) {
            startActivity(intent)
        }else {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=sa.com.stcpay"))
            startActivity(i)
        }
    }

    //Function to handle to charity location clicked
    private fun openGoogleMaps(location: LatLng){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("http://maps.google.com/maps?q=loc:"+location.latitude+","
                    +location.longitude+"("+charity.name+")")
        intent.setPackage("com.google.android.apps.maps")
        if (intent.resolveActivity(packageManager) != null){
            startActivity(intent)
        } else {
            this.toast(getString(R.string.location_error))
        }
    }
}