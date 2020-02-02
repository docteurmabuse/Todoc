package com.cleanup.todoc.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.cleanup.todoc.model.Project;

@Dao
public interface ProjectDao {
    @Query("SELECT * FROM Project WHERE project_id = :projectId")
    LiveData<Project> getProject(long projectId);

    @Query("SELECT * FROM Project")
    LiveData<Project> getProjects();

    @Insert
    long createProject(Project project);
}
