package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.db.TaskBDHelper;
import com.example.myapplication.db.TaskCursorWrapper;
import com.example.myapplication.db.TaskDBSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс-синглтон в помощь для работы с задачами
 */
public class TaskHolder {
    /**
     * Единственный экземпляр класса
     */
    private static TaskHolder taskHolder;
    /**
     * Контекст приложения
     */
    private final Context context;
    /**
     * SQL база данных
     */
    private final SQLiteDatabase db;

    /**
     * Синглтон-метод, возвращающий единственный объект
     *
     * @param context - контект приложения
     * @return синглтон-объект
     */
    public static TaskHolder get(Context context) {
        // если объект ещё не создан
        if (taskHolder == null) {
            // создаём его на основе нотекста приложения
            taskHolder = new TaskHolder(context);
        }
        // возвращаем объект
        return taskHolder;
    }

    /**
     * Закрытый конструктор синглтона-помощника для работы с задачами
     *
     * @param context - контектс приложения
     */
    private TaskHolder(Context context) {
        // получаем контекст приложения
        this.context = context.getApplicationContext();
        // создаём помощника для работы с базой данных
        db = new TaskBDHelper(this.context).getWritableDatabase();
    }

    /**
     * Удаление задачи
     *
     * @param task - задача
     */
    public void deleteTask(Task task) {
        // получаем строковое предсавление id
        String uuidString = task.getId().toString();
        // выполняем запрос к базе данных: используем таблицу задач,
        // удаляем все, у которых id равен заданному нами
        db.delete(TaskDBSchema.CrimeTable.NAME,
                TaskDBSchema.CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    /**
     * Добавление задачи
     *
     * @param task - задача
     */
    public void addTask(Task task) {
        // получаем понятное базе данных представление задачи
        ContentValues values = TaskBDHelper.getContentValues(task);
        // отправляем запрос на добавление этой задачи в базу данных
        db.insert(TaskDBSchema.CrimeTable.NAME, null, values);
    }

    /**
     * Обновление задачи
     *
     * @param task - задача
     */
    public void updateTask(Task task) {
        // получаем строковое предсавление id
        String uuidString = task.getId().toString();
        // получаем понятное базе данных представление задачи
        ContentValues values = TaskBDHelper.getContentValues(task);
        // отправляем запрос на обновление этой задачи в базе данных
        db.update(TaskDBSchema.CrimeTable.NAME, values,
                TaskDBSchema.CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    /**
     * Формирует произвольный запрос задач
     *
     * @param whereClause - условия запроса
     * @param whereArgs   - значения запроса
     * @return - курсор, заточенный под работу с задачами
     */
    private TaskCursorWrapper queryTasks(String whereClause, String[] whereArgs) {
        // создаём курсор
        Cursor cursor = db.query(
                TaskDBSchema.CrimeTable.NAME,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        // возвращаем курсор, заточенный под работу с задачами
        return new TaskCursorWrapper(cursor);
    }

    /**
     * Возвращает список всех задач
     *
     * @return - список задач
     */
    public List<Task> getTasks() {
        // создаём список задач
        List<Task> tasks = new ArrayList<>();
        // получаем курсор
        try (TaskCursorWrapper cursor = queryTasks(null, null)) {
            // перемещаемся к первой записи
            cursor.moveToFirst();
            // если есть что читать
            while (!cursor.isAfterLast()) {
                // добавляем задачу из записи, перед которой стоит курсор
                tasks.add(cursor.getTask());
                // переводим курсор к следующей записи
                cursor.moveToNext();
            }
        }
        // возвращаем список задач
        return tasks;
    }

    /**
     * Получить задачу по её id
     * @param id - id задачи
     * @return - объект задачи, если она найдена, null в противном случае
     */
    public Task getTask(UUID id) {
        // Получаем курсор
        try (TaskCursorWrapper cursor = queryTasks(
                TaskDBSchema.CrimeTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        )) {
            // если записей нет
            if (cursor.getCount() == 0) {
                // значит, задача не найдена
                return null;
            }
            // перемещаемся к первой записи
            cursor.moveToFirst();
            // возвращаем задачу, построенную из первой записи
            return cursor.getTask();
        }
    }


}