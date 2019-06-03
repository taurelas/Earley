package com.leadinsource.earley

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val liveData: LiveData<List<Project>> by lazy {
        projectRepository.getAll()
    }

    private val projectRepository = ProjectRepository.getInstance(application.applicationContext)

    fun getProjects(): LiveData<List<Project>> {
        return Transformations.map(liveData) {
            it.sortedBy { project -> project.position }
        }
    }

    fun getCurrentProject(): LiveData<Project> {
        return Transformations.map(liveData) {
            it.sortedBy { project -> project.position }[0]
        }
    }

    fun setItemDone(project: Project) {
        projectRepository.moveToTheEnd(project)
    }

    fun addProject(project: Project) {
        projectRepository.add(project)
    }
}