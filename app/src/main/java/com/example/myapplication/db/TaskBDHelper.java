package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.data.Task;

/**
 * Помощник для работы с задачами в базе данных
 */
public class TaskBDHelper extends SQLiteOpenHelper {
    /**
     * Версия БД
     */
    private static final int VERSION = 1;
    /**
     * Имя БД
     */
    private static final String DATABASE_NAME = "tasksBase.db";

    /**
     * Конструктор помощника для работы с задачами в базе данных
     *
     * @param context контекст приложения
     */
    public TaskBDHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * Создание помощника(если базы данных нет)
     *
     * @param db - объект базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // выполняем команду по созданию таблицы задач
        db.execSQL("create table " + TaskDBSchema.TaskTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TaskDBSchema.TaskTable.Cols.UUID + ", " +
                TaskDBSchema.TaskTable.Cols.TITLE + ", " +
                TaskDBSchema.TaskTable.Cols.TEXT + ", " +
                TaskDBSchema.TaskTable.Cols.SOLVED +
                ")"
        );
    }

    /**
     * Если база уже создана, но версия изменилась
     *
     * @param db         - объект базы данных
     * @param oldVersion - старая версия базы
     * @param newVersion - новая версия
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * Получить данные задачи в удобном для базы данных виде
     *
     * @param task - задача
     * @return -  данные задачи в удобном для базы данных виде
     */
    public static ContentValues getContentValues(Task task) {
        // создаём объект значений
        ContentValues values = new ContentValues();
        // добавляем id
        values.put(TaskDBSchema.TaskTable.Cols.UUID, task.getId().toString());
        // добавляем заголовок
        values.put(TaskDBSchema.TaskTable.Cols.TITLE, task.getTitle());
        // добавляем текст
        values.put(TaskDBSchema.TaskTable.Cols.TEXT, task.getText());
        // добавляем флаг, решена ли задача
        values.put(TaskDBSchema.TaskTable.Cols.SOLVED, task.isSolved() ? 1 : 0);
        // возвращаем сформированный объект
        return values;
    }

}
