package com.example.myapplication.data;

import java.util.UUID;

/**
 * Класс задачи
 */
public class Task {
    /**
     * id задачи
     */
    private final UUID id;
    /**
     * Заголовок задачи
     */
    private String title;
    /**
     * Текст задачи
     */
    private String text;
    /**
     * Флаг, решена ли задача
     */
    private boolean solved;

    /**
     * Конструктор задачи
     * инициализирует задачу случайным id
     */
    public Task() {
        this(UUID.randomUUID());
    }

    /**
     * Конструктор задачи
     *
     * @param id - id задачи
     */
    public Task(UUID id) {
        this.id = id;
    }

    /**
     * Получить id задачи
     *
     * @return - id задачи
     */
    public UUID getId() {
        return id;
    }

    /**
     * Получить заголовок задачи
     *
     * @return - заголовок задачи
     */
    public String getTitle() {
        return title;
    }

    /**
     * Задать заголовок задачи
     *
     * @param title - заголовок задачи
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Получить текст задачи
     *
     * @return - текст задачи
     */
    public String getText() {
        return text;
    }

    /**
     * Задать текст задачи
     *
     * @param text - текст задачи
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Получить флаг, решена ли задача
     *
     * @return -  флаг, решена ли задача
     */
    public boolean isSolved() {
        return solved;
    }

    /**
     * Задать  флаг, решена ли задача
     *
     * @param solved -  флаг, решена ли задача
     */
    public void setSolved(boolean solved) {
        this.solved = solved;
    }

}
