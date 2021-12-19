package com.saraha.paws.View.CharityViews.ViewCharities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saraha.paws.Model.Charity
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.databinding.FragmentViewCharitiesBinding


class ViewCharitiesFragment : Fragment() {

    private lateinit var viewModel: ViewCharitiesViewModel
    lateinit var binding: FragmentViewCharitiesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewCharitiesBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as HomeActivity)[ViewCharitiesViewModel::class.java]

        return binding.root
    }

    override fun onResume() {
        getAllCharities()
        super.onResume()
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