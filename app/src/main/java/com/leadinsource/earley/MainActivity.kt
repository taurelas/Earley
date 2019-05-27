package com.leadinsource.earley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isNotEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Callback {

    // Let's try to implement as much as possible from https://developer.android.com/jetpack

    lateinit var viewModel: MainActivityViewModel
    private lateinit var viewAdapter: RecyclerViewAdapter
    private var recyclerView: RecyclerView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRV()

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getProjects().observe(this, Observer {

            viewAdapter.setData(it)

        })
    }

    private fun initRV() {
        val viewManager = LinearLayoutManager(this)
        viewAdapter = RecyclerViewAdapter(this)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }


    override fun onClick(position: Int) {

       viewModel.setItemDone(position)



    }
}
