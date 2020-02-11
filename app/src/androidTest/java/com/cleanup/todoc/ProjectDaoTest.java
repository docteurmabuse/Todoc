package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ProjectDaoTest {
    private static long PROJECT_ID = 1L;
    private static Project PROJECT_DEMO = new Project(1L, "Project Mad Kitchen", 0xFFEADAD1);
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    ProjectDao projectDao;
    //FOR DATA
    private TodocDatabase database;

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), TodocDatabase.class).allowMainThreadQueries().build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetProjects() throws InterruptedException {
        //TEST CREATE Project & READ project list
        List<Project> projects = new ArrayList<>();
        assertEquals(0, projects.size());
        this.database.projectDao().createProject(PROJECT_DEMO);
        projects = LiveDataTestUtil.getValue(this.database.projectDao().getProjects());
        assertEquals(1, projects.size());
    }
}
