package com.example.myapplication.req;

import com.example.myapplication.data.Task;

import java.util.List;

/**
 * запрос на добавление задач
 */
public class TasksRequest {
    /**
     * Пользователь
     */
    final String user;
    /**
     * Список задач
     */
    final List<Task> tasks;

    /**
     * Конструктор  запроса на добавление задач
     *
     * @param text  -  пользователь
     * @param tasks - список задач
     */
    public TasksRequest(String text, List<Task> tasks) {
        this.user = text;
        this.tasks = tasks;
    }
}
