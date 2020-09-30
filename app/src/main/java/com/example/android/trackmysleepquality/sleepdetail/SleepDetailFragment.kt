package com.example.android.trackmysleepquality.sleepdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.databinding.FragmentSleepDetailBinding
import com.example.android.trackmysleepquality.sleepquality.SleepQualityFragmentArgs
import com.example.android.trackmysleepquality.sleeptracker.SleepNightListener
import com.example.android.trackmysleepquality.sleeptracker.SleepNightNewAdapter
import com.example.android.trackmysleepquality.sleeptracker.SleepTrackerViewModel


class SleepDetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding : FragmentSleepDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sleep_detail,container,false)
        val application = requireActivity().application
        val arguments = SleepDetailFragmentArgs.fromBundle(arguments)

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepDetailViewModelFactory(arguments.sleepNightKey,dataSource)
        val viewModel = ViewModelProvider(this,viewModelFactory).get(SleepDetailViewModel::class.java)


        binding.lifecycleOwner = this
        binding.sleepDetailViewModel = viewModel

        return binding.root
    }

}