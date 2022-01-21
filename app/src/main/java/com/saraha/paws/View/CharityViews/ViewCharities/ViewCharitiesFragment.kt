package com.saraha.paws.View.CharityViews.ViewCharities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saraha.paws.Model.Charity
import com.saraha.paws.R
import com.saraha.paws.View.AccountViews.EditProfile.EditProfileActivity
import com.saraha.paws.View.Home.Home.HomeActivity
import com.saraha.paws.databinding.FragmentViewCharitiesBinding


class ViewCharitiesFragment : Fragment() {
    //View model and binding lateinit property
    private lateinit var viewModel: ViewCharitiesViewModel
    lateinit var binding: FragmentViewCharitiesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewCharitiesBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as HomeActivity)[ViewCharitiesViewModel::class.java]

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onResume() {
        getAllCharities()
        super.onResume()
    }

    //Function for an toolbar content and handler
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu,inflater)
    }

    //Function to get all charities from Firestore
    private fun getAllCharities(){
        viewModel.getAllCharitiesFromFirebase()
        viewModel.listOfCharitiesLiveData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){ setRecyclerViewWithData(it) }
        }
    }

    //Function to set data into recyclerview
    private fun setRecyclerViewWithData(charities: List<Charity>?) {
        val recyclerView = binding.recyclerViewCharityList
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = CharityViewAdapter(this.context!!,charities!!)
    }

}