package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.dao.TodocDatabase;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    //FOR DATA
    private TodocDatabase database;
    private static long PROJECT_ID = 1L;
    private static Task TASK_DEMO = new Task(1, PROJECT_ID, "Clean Kitchen", new Date().getTime());

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), TodocDatabase.class).allowMainThreadQueries().build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void getTasksWithNoTaskInserted() throws InterruptedException {
        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        //BEFORE : Adding a new task
        this.database.taskDao().createTask(TASK_DEMO);
        //TEST
        Task task = LiveDataTestUtil.getValue(this.database.taskDao().getTask(PROJECT_ID));
        assertTrue(task.getName().equals(TASK_DEMO.getName()) && task.getId() == TASK_DEMO.getId());

    }
}
