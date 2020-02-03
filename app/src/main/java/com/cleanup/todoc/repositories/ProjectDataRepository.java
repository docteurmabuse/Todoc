package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

public class ProjectDataRepository {
    private final ProjectDao projectDao;

    public ProjectDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    //GET PROJECT
    public LiveData<Project> getProject(long projectId) {
        return this.projectDao.getProject(projectId);
    }

    //GET PROJECTS
    public LiveData<Project> getProjects() {
        return this.projectDao.getProjects();
    }
}
