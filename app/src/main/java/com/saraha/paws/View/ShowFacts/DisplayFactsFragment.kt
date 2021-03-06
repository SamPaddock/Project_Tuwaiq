package com.saraha.paws.View.ShowFacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saraha.paws.Database.DatabaseClient
import com.saraha.paws.Model.Facts.CatFacts
import com.saraha.paws.Util.AppSharedPreference
import com.saraha.paws.Util.SharedConst
import com.saraha.paws.Util.toast
import com.saraha.paws.View.Home.Home.HomeActivity
import com.saraha.paws.databinding.FragmentDisplayFactsBinding
import java.util.*
import android.app.AlarmManager

import android.app.PendingIntent
import android.content.Context

import android.content.Intent
import android.view.*
import com.saraha.paws.Service.MyFactNotificationReceiver


class DisplayFactsFragment : Fragment() {
    //View model and binding lateinit property
    private lateinit var viewModel: DisplayFactsViewModel
    lateinit var binding: FragmentDisplayFactsBinding
    //Shared preference helper class object
    val sharedPref = AppSharedPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDisplayFactsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity() as HomeActivity)[DisplayFactsViewModel::class.java]

        setHasOptionsMenu(true)

        //init database object
        viewModel.dbClient = DatabaseClient(requireActivity().applicationContext)
        viewModel.db = viewModel.dbClient?.getInstance(requireActivity().applicationContext)?.getAppDatabase()

        checkContentInRoom()

        setNotification()

        return binding.root
    }

    //Function for an toolbar content and handler
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu,inflater)
    }

    //Function to handle response of Data statues from viewModel
    private fun checkContentInRoom(){
        viewModel.checkIfRoomIsEmpty(this.requireContext())
        viewModel.factsLiveData.observe(this){
            val (data, response) = it
            when (response){
                DataStatus.New -> setNextFacts(data)
                DataStatus.Next -> setNextFacts(data)
                DataStatus.Room -> setRecyclerViewWithData(data)
                DataStatus.Error -> this.requireContext().toast(response.string)
                else -> this.requireContext().toast(response.string)
            }
        }
    }

    //Function to set data from next page of Api
    private fun setNextFacts(data: CatFacts?) {
        //Time of retrieving facts
        val instance = Calendar.getInstance()
        instance.add(Calendar.HOUR,24)
        sharedPref.write(SharedConst.PrefsFactDate.string, instance.timeInMillis)
        //saving facts in Room and displaying facts
        viewModel.saveFactIntoRoom(data)
        setRecyclerViewWithData(data)
    }

    //Function to set data into recyclerview
    private fun setRecyclerViewWithData(catFacts: CatFacts?) {
        val recyclerView = binding.recyclerViewDisplayfacts
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.adapter = DisplayFactsViewAdapter(this.requireContext(),catFacts!!)
    }

    //Set notification alarm to show notification every 24 hours periodically
    private fun setNotification() {
        val notifyIntent = Intent(this.requireContext(), MyFactNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,
            0,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmUp = PendingIntent.getBroadcast(context, 0,
            notifyIntent,
            PendingIntent.FLAG_NO_CREATE) != null

        if (alarmUp){
            val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (
                    1000 * 60 * 3).toLong(), pendingIntent)
        }

    }
}