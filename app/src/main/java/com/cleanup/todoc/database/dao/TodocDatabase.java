package com.cleanup.todoc.database.dao;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {
    //SINGLETON
    private static volatile TodocDatabase INSTANCE;

    //INSTANCE
    public static TodocDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TodocDatabase.class, "TodocDatabase.db").addCallback(prepopulateDatabase()).build();
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {
            /**
             * Called when the database is created for the first time. This is called after all the
             * tables are created.
             *
             * @param db The database.
             */
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();
                //contentValues.put("id");
            }
        };

    }

    //DAO
    public abstract TaskDao taskDao();

    public abstract ProjectDao projectDao();
}
