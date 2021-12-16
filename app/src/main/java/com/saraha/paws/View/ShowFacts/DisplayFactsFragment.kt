package com.saraha.paws.View.ShowFacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saraha.paws.Database.DatabaseClient
import com.saraha.paws.Model.Facts.CatFacts
import com.saraha.paws.Util.toast
import com.saraha.paws.View.Home.HomeActivity
import com.saraha.paws.databinding.FragmentDisplayFactsBinding
import java.util.*

class DisplayFactsFragment : Fragment() {

    private lateinit var viewModel: DisplayFactsViewModel
    lateinit var binding: FragmentDisplayFactsBinding

    //val sharedPref = AppSharedPreference()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisplayFactsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as HomeActivity)[DisplayFactsViewModel::class.java]

        viewModel.dbClient = DatabaseClient(requireActivity().applicationContext)
        viewModel.db = viewModel.dbClient?.getInstance(requireActivity().applicationContext)?.getAppDatabase()

        checkContentInRoom()

        return binding.root
    }

    private fun checkContentInRoom(){
        viewModel.checkIfRoomIsEmpty()
        viewModel.factsLiveData.observe(this){
            val (data, response) = it
            when (response){
                DataStatus.New -> setNewFacts(data)
                DataStatus.Next -> setNextFacts(data)
                DataStatus.Room -> setRecyclerViewWithData(data)
                DataStatus.Error -> showErrorToast(response)
                else -> showErrorToast(response)
            }
        }
    }

    private fun showErrorToast(response: DataStatus) {
        this.requireContext().toast(response.string)
    }

    private fun setNewFacts(data: CatFacts?) {
        //sharedPref.write(SharedConst.PrefsFactDate.string, Calendar.getInstance().timeInMillis)
        setNextFacts(data)
    }

    private fun setNextFacts(data: CatFacts?) {
        viewModel.saveFactIntoRoom(data)
        setRecyclerViewWithData(data)
    }

    private fun saveFactIntoRoom(catFacts: CatFacts?){
        if (catFacts != null) {
            viewModel.db?.catFactsDao()?.delete()
            viewModel.db?.catFactsDao()?.insert(catFacts)
        }
    }

    private fun setRecyclerViewWithData(catFacts: CatFacts?) {
        val recyclerView = binding.recyclerViewDisplayfacts
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.adapter = DisplayFactsViewAdapter(this.requireContext(),catFacts!!)
    }
}