/*
 * Copyright 2018, The Android Open Source Project
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

package com.vanh.android.nicenight.sleepTracker

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.vanh.android.nicenight.database.SleepNight
import com.vanh.android.nicenight.databinding.ListItemSleepNightBinding

class SleepNightAdapter(val clickListener:SleepNightListener): ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(
                                SleepNightDiffCallback() ){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(clickListener,getItem(position)!!)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemSleepNightBinding)
                :RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener:SleepNightListener,item:SleepNight){
            binding.sleep = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
             fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSleepNightBinding.inflate(layoutInflater,parent,false)

                return ViewHolder(binding)
            }
        }
    }
}

// DiffUtil is a utility to calculate the data changed difference for
// RecycleView to redraw only the changed item on the list instead of the
// whole list to improve responsiveness
class SleepNightDiffCallback: DiffUtil.ItemCallback<SleepNight>() {
    override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean
            = oldItem.nightId == newItem.nightId
    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean
            =  oldItem == newItem
}

 class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
     fun onClick(night: SleepNight) = clickListener(night.nightId)
 }