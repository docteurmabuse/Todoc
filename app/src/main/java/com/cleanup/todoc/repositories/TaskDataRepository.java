package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {
    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    //CREATE
    public void createTask(Task task) {
        taskDao.insertTask(task);
    }

    //READ
    public LiveData<List<Task>> getTasks() {
        return taskDao.getTasks();
    }

    //UPDATE
    public void updateTask(Task task) {
        taskDao.updateTask(task);
    }

    //DELETE
    public void deleteTask(long taskId) {
        taskDao.deleteTask(taskId);
    }
}
