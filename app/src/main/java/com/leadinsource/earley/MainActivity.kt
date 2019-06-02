package com.leadinsource.earley

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Callback,
    AddProjectFragment.OnFragmentInteractionListener {

    private lateinit var addProjectFragment: Fragment

    override fun onFragmentInteraction(project: Project) {
        viewModel.addProject(project)
        removeAddProjectFragment()
        //TODO animation instead of toast
        Toast.makeText(this, "Adding new project", Toast.LENGTH_SHORT).show()
    }

    // Let's try to implement as much as possible from https://developer.android.com/jetpack

    private lateinit var viewModel: MainActivityViewModel
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addProject -> {
                displayNewProjectDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayNewProjectDialog() {
        //
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        addProjectFragment = AddProjectFragment.newInstance()
        fragmentTransaction.add(R.id.rootLayout, addProjectFragment)
        fragmentTransaction.commit()
    }

    override fun onClick(project: Project) {
        viewModel.setItemDone(project)

    }

    private fun removeAddProjectFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(addProjectFragment)
        fragmentTransaction.commit()
    }
}
