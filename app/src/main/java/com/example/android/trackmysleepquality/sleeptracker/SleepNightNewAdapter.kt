package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding

class SleepNightNewAdapter(private val clickListener : SleepNightListener) : ListAdapter<SleepNight, SleepNightNewAdapter.MyViewHolder>(SleepNightDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item_sleep_night, parent, false)
        val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }

    class MyViewHolder(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root) {
//        val qualityImage: ImageView = binding.qualityImage
//        val quality: TextView = binding.qualityString
//        val sleepLength: TextView = binding.sleepLength

        fun bind(item: SleepNight, clickListener: SleepNightListener) {
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

}

class SleepNightListener(val clickListener : (sleepId: Long)->Unit){
    fun onClick(night : SleepNight) = clickListener(night.nightId)
}