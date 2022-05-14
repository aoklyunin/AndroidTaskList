package com.example.myapplication.db;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.example.myapplication.data.Task;

import java.util.UUID;

public class TaskCursorWrapper extends CursorWrapper {
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String uuidString = getString(getColumnIndex(TaskDBSchema.CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(TaskDBSchema.CrimeTable.Cols.TITLE));
        String text = getString(getColumnIndex(TaskDBSchema.CrimeTable.Cols.TEXT));

        int isSolved = getInt(getColumnIndex(TaskDBSchema.CrimeTable.Cols.SOLVED));
        Task task = new Task(UUID.fromString(uuidString));
        task.setTitle(title);
        task.setText(text);
        task.setSolved(isSolved != 0);

        Log.e("GOT CRIME", task.toString());
        return task;
    }
}
