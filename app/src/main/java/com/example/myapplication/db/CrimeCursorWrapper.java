package com.example.myapplication.db;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.example.myapplication.data.Crime;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TITLE));
        String text = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TEXT));

        int isSolved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED));
        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setText(text);
        crime.setSolved(isSolved != 0);

        Log.e("GOT CRIME",crime.toString());
        return crime;
    }
}
