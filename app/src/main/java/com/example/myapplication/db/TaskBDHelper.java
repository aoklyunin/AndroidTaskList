package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.data.Task;

public class TaskBDHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "tasksBase.db";
    public TaskBDHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TaskDBSchema.CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TaskDBSchema.CrimeTable.Cols.UUID + ", " +
                TaskDBSchema.CrimeTable.Cols.TITLE + ", " +
                TaskDBSchema.CrimeTable.Cols.TEXT + ", " +
                TaskDBSchema.CrimeTable.Cols.SOLVED +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public static ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskDBSchema.CrimeTable.Cols.UUID, task.getId().toString());
        values.put(TaskDBSchema.CrimeTable.Cols.TITLE, task.getTitle());
        values.put(TaskDBSchema.CrimeTable.Cols.TEXT, task.getText());
        values.put(TaskDBSchema.CrimeTable.Cols.SOLVED, task.isSolved() ? 1 : 0);
        return values;
    }

}
