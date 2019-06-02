package com.leadinsource.earley

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.leadinsource.earley.db.AppDatabase
import com.leadinsource.earley.db.ProjectData
import java.util.concurrent.Executors

class ProjectRepository private constructor(applicationContext: Context) {

    private var allProjects: LiveData<List<Project>>

    fun getAll(): LiveData<List<Project>> {
        return allProjects
    }

    fun moveToTheEnd(project: Project) {

        executeDbOperation {
            val oldPosition = project.position
            db.projectDao().updatePositionsFrom(oldPosition)
            project.position = db.projectDao().getCount()
            db.projectDao().update(ProjectData.fromProject(project))
        }
    }

    fun add(project: Project) {
        executeDbOperation {
            project.position = db.projectDao().getCount() + 1
            db.projectDao().insert(ProjectData.fromProject(project))
        }
    }

    private val db: AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "database.db"
    ).build()

    init {
        allProjects = db.projectDao().getAll()
    }

    companion object : SingletonHolder<ProjectRepository, Context>(::ProjectRepository)

    private fun executeDbOperation(operation: () -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute(operation)
    }
}