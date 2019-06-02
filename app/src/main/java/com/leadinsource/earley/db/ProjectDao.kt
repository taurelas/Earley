package com.leadinsource.earley.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.leadinsource.earley.Project

@Dao
interface ProjectDao {

    @Query("SELECT * FROM ProjectData")
    fun getAll(): LiveData<List<Project>>

    @Insert
    fun insert(project: ProjectData)

    @Query("SELECT COUNT(*) FROM ProjectData")
    fun getCount(): Int

    @Update
    fun update(project: ProjectData)

    @Query("UPDATE ProjectData SET position = position -1 WHERE position>:position")
    fun updatePositionsFrom(position: Int)
}