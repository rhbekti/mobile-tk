package com.rhbekti.belajarrecycleview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rhbekti.belajarrecycleview.databinding.ListBinding

class Adapter (private val list:ArrayList<Users>) : RecyclerView.Adapter<Adapter.UsersViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = ListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    inner class UsersViewHolder(val binding: ListBinding)
        :RecyclerView.ViewHolder(binding.root)


    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.lbName.text  = this.name
                binding.lbFood.text = this.food
            }
        }
    }
}