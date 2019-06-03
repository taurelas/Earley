package com.leadinsource.earley.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leadinsource.earley.Project

@Entity
data class ProjectData(
    @PrimaryKey(autoGenerate = true) val uuid: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "position") val position: Int?,
    @ColumnInfo(name = "info") val info: String
) {
    companion object {
        fun fromProject(project: Project): ProjectData {
            return ProjectData(project.uuid, project.name, project.position, project.info)
        }
    }
}