package com.saraha.paws.View.ViewCharityDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.saraha.paws.Model.Charity
import com.saraha.paws.R
import com.saraha.paws.databinding.ActivityViewCharityDetailBinding
import com.squareup.picasso.Picasso
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import java.lang.Exception


class ViewCharityDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewCharityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCharityDetailBinding.inflate(layoutInflater)

        val data = intent.getSerializableExtra("charity")

        if (data != null){
            setValues(data as Charity)
        }

        setContentView(binding.root)
    }

    private fun setValues(charity: Charity) {
        //set values
        binding.textViewDisplayCharityEmail.text = charity.email
        binding.textViewDisplayCharityFounder.text = charity.founder
        binding.textViewDisplayCharityMobile.text = charity.mobile
        //set image
        Picasso.get().load(charity.photo).into(binding.imageViewDisplayCharityPhoto)
        //set buttons
        binding.imageViewSTCPayLink.setOnClickListener { openSTCPay(charity.stcPay) }
        binding.imageViewFacebookLink.setOnClickListener { openFacebook(charity.facebookUrl) }
        binding.imageViewInstaLink.setOnClickListener { openInstagram(charity.instagramUrl) }
        binding.imageViewWhatsappLink.setOnClickListener { openWhatsapp(charity.mobile) }
    }

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

    private fun openFacebook(Url: String){
        // create an Intent to send data to the Facebook
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.facebook.katana")
        if (intent.resolveActivity(packageManager) != null) {
            try {
                intent.data = Uri.parse(Url)
                startActivity(intent)
            } catch (e: Exception){
                Toast.makeText(this, "could not open link", Toast.LENGTH_SHORT).show()
            }
        }else {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(Url))
            startActivity(i)
        }

    }

    private fun openInstagram(Url: String){
        // create an Intent to send data to the Instagram
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.instagram.android")
        if (intent.resolveActivity(packageManager) != null) {
            try {
                intent.data = Uri.parse(Url)
                startActivity(intent)
            } catch (e: Exception){
                Toast.makeText(this, "could not open link", Toast.LENGTH_SHORT).show()
            }
        }else {
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(Url))
            startActivity(i)
        }
    }

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
}