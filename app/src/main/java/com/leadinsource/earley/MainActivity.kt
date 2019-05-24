package com.leadinsource.earley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Callback {

    // Let's try to implement as much as possible from https://developer.android.com/jetpack

    var data = mutableListOf(Project("Project 1"), Project("Project 2"), Project("Project 3"),
        Project("Project 4"),Project("Project 5"),Project("Project 6"))
    lateinit var viewAdapter: RecyclerViewAdapter

    override fun onClick(position: Int) {

        val element = data[position]

        val result = mutableListOf<Project>()

        result.addAll(data.subList(0, position))

        if(position<data.size-1)
            result.addAll(data.subList(position+1, data.size))

        result.add(element)
        data = result
        viewAdapter.data = result
        viewAdapter.notifyDataSetChanged()

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerViewAdapter(data, this)

        val rv = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }
}
