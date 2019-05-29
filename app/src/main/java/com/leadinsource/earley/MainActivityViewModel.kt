package com.leadinsource.earley

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val liveData: MutableLiveData<MutableList<Project>> by lazy {
        MutableLiveData<MutableList<Project>>().also {
            it.postValue(mutableListOf(
                Project("Project 1"), Project("Project 2"), Project("Project 3"),
                Project("Project 4"), Project("Project 5"), Project("Project 6")
            ))
        }
    }

    fun getProjects(): LiveData<MutableList<Project>> {
        return liveData
    }

    fun setItemDone(position: Int) {

        val data = liveData.value

        if (data != null) {
            val element = data[position]

            val result = mutableListOf<Project>()

            result.addAll(data.subList(0, position))

            if (position < data.size - 1)
                result.addAll(data.subList(position + 1, data.size))

            result.add(element)
            liveData.postValue(result)
        }
    }

    fun addProject(project: Project) {
        val data = liveData.value
        data?.add(0, project)
        liveData.postValue(data)
    }
}