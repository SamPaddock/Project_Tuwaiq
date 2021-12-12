package com.saraha.paws.View.ViewCharities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saraha.paws.Model.Charity
import com.saraha.paws.R
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.View.RegisterAccount.RegisterActivity
import com.saraha.paws.View.RegisterAccount.RegisterViewModel
import com.saraha.paws.databinding.FragmentRegisterPage2Binding
import com.saraha.paws.databinding.FragmentViewCharitiesBinding


class ViewCharitiesFragment : Fragment() {

    private lateinit var viewModel: ViewCharitiesViewModel
    lateinit var binding: FragmentViewCharitiesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentViewCharitiesBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as HomeActivity)[ViewCharitiesViewModel::class.java]

        getAllCharities()

        return binding.root
    }

    private fun getAllCharities(){
        viewModel.getAllCharitiesFromFirebase()
        viewModel.listOfCharitiesLiveData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                setRecyclerViewWithData(it)
            }
        }
    }

    private fun setRecyclerViewWithData(charities: List<Charity>?) {
        val recyclerView = binding.recyclerViewCharityList
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = CharityViewAdapter(this.context!!,charities!!)
    }

}