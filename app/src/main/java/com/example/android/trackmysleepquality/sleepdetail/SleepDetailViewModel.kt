package com.example.android.trackmysleepquality.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight

class SleepDetailViewModel(private val sleepNightKey: Long = 0L,dao: SleepDatabaseDao) : ViewModel() {

    val database = dao
    private val night: LiveData<SleepNight>
    fun getNight() = night
    init {
        night = database.getNightWithId(sleepNightKey)
    }


}