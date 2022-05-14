package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.db.CrimeBaseHelper;
import com.example.myapplication.db.CrimeCursorWrapper;
import com.example.myapplication.db.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    Context mAppContext;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private List<Task> mTasks;


    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }

    public void deleteCrime(Task task) {
        String uuidString = task.getId().toString();
        mDatabase.delete(CrimeDbSchema.CrimeTable.NAME,
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public void addCrime(Task c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeDbSchema.CrimeTable.NAME, null, values);
    }

    private CrimeLab(Context context) {
        mAppContext = context;

        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();

    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new CrimeCursorWrapper(cursor);
    }

    public void populate() {
        for (int i = 0; i < 100; i++) {
            Task task = new Task();
            task.setTitle("Crime #" + i);
            task.setSolved(i % 2 == 0); // Для каждого второго объекта
            mTasks.add(task);
        }
    }

    private static ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(CrimeDbSchema.CrimeTable.Cols.UUID, task.getId().toString());
        values.put(CrimeDbSchema.CrimeTable.Cols.TITLE, task.getTitle());
        values.put(CrimeDbSchema.CrimeTable.Cols.TEXT, task.getText());
        values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED, task.isSolved() ? 1 : 0);
        return values;
    }

    public List<Task> getCrimes() {
        List<Task> tasks = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    public void updateCrime(Task task) {
        String uuidString = task.getId().toString();
        ContentValues values = getContentValues(task);
        mDatabase.update(CrimeDbSchema.CrimeTable.NAME, values,
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    public Task getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeDbSchema.CrimeTable.Cols.UUID + " = ?",
                        new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }
}