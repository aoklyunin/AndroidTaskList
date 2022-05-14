package com.example.myapplication.db;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.example.myapplication.data.Task;

import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getCrime() {
        String uuidString = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TITLE));
        String text = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TEXT));

        int isSolved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED));
        Task task = new Task(UUID.fromString(uuidString));
        task.setTitle(title);
        task.setText(text);
        task.setSolved(isSolved != 0);

        Log.e("GOT CRIME", task.toString());
        return task;
    }
}
