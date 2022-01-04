package com.saraha.paws.View.Home.HomeServices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saraha.paws.R
import com.saraha.paws.View.AnimalViews.ViewAnimals.ViewAnimalsFragment
import com.saraha.paws.View.VendorsViews.ViewVendors.ViewVendorsFragment
import com.saraha.paws.databinding.FragmentHomeServicesBinding
import com.saraha.paws.databinding.FragmentViewVendorsBinding

class HomeServicesFragment : Fragment() {

    lateinit var binding: FragmentHomeServicesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeServicesBinding.inflate(inflater, container, false)

        setOnBottomNavItemClick()

        return binding.root
    }

    fun setOnBottomNavItemClick(){
        displayFragment(ViewAnimalsFragment(), "adoption")

        binding.bottomNavigationHome.setOnItemSelectedListener {
            when (it.itemId){
                R.id.page_rescues_2 -> {displayFragment(ViewAnimalsFragment(), "adoption")}
                R.id.page_vets -> {displayFragment(ViewVendorsFragment(), "vets")}
                R.id.page_stores -> {displayFragment(ViewVendorsFragment(), "stores")}
                else -> {displayFragment(ViewAnimalsFragment(), "adoption")}
            }
            true
        }
    }

    //Function to replace a fragment view
    private fun displayFragment(fragment: Fragment, tag: String) {
        val bundle = Bundle()
        bundle.putString("tag", tag)
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction().replace(binding.homeServicesFrameLayout.id, fragment).commit()
    }

}