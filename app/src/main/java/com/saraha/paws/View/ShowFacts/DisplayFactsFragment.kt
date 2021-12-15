package com.saraha.paws.View.ShowFacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saraha.paws.Model.Facts.catFacts
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.databinding.FragmentDisplayFactsBinding

class DisplayFactsFragment : Fragment() {

    private lateinit var viewModel: DisplayFactsViewModel
    lateinit var binding: FragmentDisplayFactsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisplayFactsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as HomeActivity)[DisplayFactsViewModel::class.java]

        getAllFacts()

        return binding.root
    }

    private fun getAllFacts(){
        viewModel.getFacts()
        viewModel.factsLiveData.observe(viewLifecycleOwner){
            if (it != null && it.data.isNotEmpty()) {
                setRecyclerViewWithData(it)
            }
        }
    }

    private fun setRecyclerViewWithData(catFacts: catFacts?) {
        val recyclerView = binding.recyclerViewDisplayfacts
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.adapter = DisplayFactsViewAdapter(this.requireContext(),catFacts!!)
    }
}