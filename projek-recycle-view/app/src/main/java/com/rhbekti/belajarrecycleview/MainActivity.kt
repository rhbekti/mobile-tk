package com.rhbekti.belajarrecycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhbekti.belajarrecycleview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val list = ArrayList<Users>()
    val listUsers = arrayOf(
        "Alpha",
        "Bravo",
        "Charlie",
        "Delta"
    )

    val listFoods = arrayOf(
        "Burger",
        "Apple",
        "Sandwich",
        "Pizza"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mRecyclerView.setHasFixedSize(false)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)

        for (i in listUsers.indices) {
            list.add(Users(listUsers[i],listFoods[i]))
            if (listUsers.size - 1 == i) {
                val adapter = Adapter(list)
                adapter.notifyDataSetChanged()
                binding.mRecyclerView.adapter = adapter
            }
        }
    }
}