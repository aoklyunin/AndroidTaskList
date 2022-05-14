package com.example.myapplication.db;

import android.database.Cursor;
import android.database.CursorWrapper;
import java.util.UUID;

import com.example.myapplication.data.Task;

/**
 * Курсор базы данных заточенный под работу с задачами
 */
public class TaskCursorWrapper extends CursorWrapper {
    /**
     * Конструктор курсора
     *
     * @param cursor - обычный курсор БД
     */
    public TaskCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    /**
     * Получить задачу из записи, перед которой сейчас находится курсор
     *
     * @return - задача из записи БД
     */
    public Task getTask() {
        // получаем строковое предстваление id
        String uuidString = getString(getColumnIndex(TaskDBSchema.TaskTable.Cols.UUID));
        // получаем заголовок задачи
        String title = getString(getColumnIndex(TaskDBSchema.TaskTable.Cols.TITLE));
        // получаем текст задачи
        String text = getString(getColumnIndex(TaskDBSchema.TaskTable.Cols.TEXT));
        // получаем флаг, решена ли задача
        int isSolved = getInt(getColumnIndex(TaskDBSchema.TaskTable.Cols.SOLVED));
        // создаём задачу
        Task task = new Task(UUID.fromString(uuidString));
        // задаём заголовок
        task.setTitle(title);
        // задаём текст
        task.setText(text);
        // задаём флаг, решена ли задача
        task.setSolved(isSolved != 0);
        // возвращзаем задачу
        return task;
    }
}
