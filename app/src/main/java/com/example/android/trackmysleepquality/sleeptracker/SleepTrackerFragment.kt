/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleeptracker

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepDatabase
import com.example.android.trackmysleepquality.databinding.FragmentSleepTrackerBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment with buttons to record start and end times for sleep, which are saved in
 * a database. Cumulative data is displayed in a simple scrollable TextView.
 * (Because we have not learned about RecyclerView yet.)
 */
class SleepTrackerFragment : Fragment() {

    /**
     * Called when the Fragment is ready to display content to the screen.
     *
     * This function uses DataBindingUtil to inflate R.layout.fragment_sleep_quality.
     */
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sleep_tracker, container, false)

        // Get a reference to the binding object and inflate the fragment views.
        val application = requireNotNull(this.activity).application
        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        val sleepTrackerViewModel = ViewModelProvider(this, viewModelFactory).get(SleepTrackerViewModel::class.java)
        binding.sleepTrackerViewModel = sleepTrackerViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner
        //val adapter = SleepNightAdapter()
        val newAdapter = SleepNightNewAdapter(SleepNightListener {nightId->
            sleepTrackerViewModel.onSleepNightClicked(nightId)
            Toast.makeText(context, "$nightId is tapped", Toast.LENGTH_LONG).show()
            //sleepTrackerViewModel.onSleepNightClicked(nightId)

        })


        binding.sleepList.adapter = newAdapter






        //val alertDialog = AlertDialog.Builder(activity)
        //                alertDialog.setMessage("Are you want delete the all items ?")
        //                alertDialog.setTitle("Warning!")
        //                alertDialog.setPositiveButton("Yes"){dialog, which->
        //                    Toast.makeText(activity,"Yes",Toast.LENGTH_LONG).show()
        //                }
        //                alertDialog.create()
        //                alertDialog.setCancelable(true)
        //                alertDialog.show()

        sleepTrackerViewModel.showSnackBar.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.cleared_message), Snackbar.LENGTH_SHORT).show()

                sleepTrackerViewModel.doneShowingSnackBar()

            }

        })

//        sleepTrackerViewModel.allNights.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.data = it
//            }
//        })
        sleepTrackerViewModel.allNights.observe(viewLifecycleOwner, Observer {
            newAdapter.submitList(it)
        })


        sleepTrackerViewModel.navigateSleepQuality.observe(viewLifecycleOwner, Observer {
            it?.let {
                val action =
                        SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(it.nightId)
                this.findNavController().navigate(action)
                sleepTrackerViewModel.doneNavigation()
            }
        })

        sleepTrackerViewModel.navigateToSleepDetail.observe(viewLifecycleOwner,Observer{night->
            night?.let {
                this.findNavController().navigate(SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepDetailFragment2(night))
                sleepTrackerViewModel.onSleepDetailNavigated()
            }
        })


        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        binding.sleepList.layoutManager = manager

        return binding.root
    }
}
