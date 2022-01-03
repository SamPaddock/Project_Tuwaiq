package com.saraha.paws.View.VendorsViews.ViewVendors

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.saraha.paws.R
import com.saraha.paws.databinding.FragmentViewAnimalsBinding
import com.saraha.paws.databinding.FragmentViewVendorsBinding

class ViewVendorsFragment : Fragment() {

    lateinit var binding: FragmentViewVendorsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentViewVendorsBinding.inflate(inflater, container, false)



        return binding.root
    }


}