package com.example.myapplication.db;

/**
 * Класс для хранения схем БД
 */
public class TaskDBSchema {
    /**
     * Схема таблицы задач
     */
    public static final class TaskTable {
        /**
         * Название таблицы
         */
        public static final String NAME = "tasks";

        /**
         * Класс колонок таблицы
         */
        public static final class Cols {
            /**
             * Название колонки id
             */
            public static final String UUID = "uuid";
            /**
             * Название колонки заголовка
             */
            public static final String TITLE = "title";
            /**
             * Название колонки текста
             */
            public static final String TEXT = "text";
            /**
             * Название колонки флага, решена ли задача
             */
            public static final String SOLVED = "solved";

            /**
             * Закрываем конструктор для защиты
             */
            private Cols() {
                throw new AssertionError("Этот конструктор не должен вызываться!");
            }
        }

        /**
         * Закрываем конструктор для защиты
         */
        private TaskTable() {
            throw new AssertionError("Этот конструктор не должен вызываться!");
        }
    }

    /**
     * Закрываем конструктор для защиты
     */
    private TaskDBSchema() {
        throw new AssertionError("Этот конструктор не должен вызываться!");
    }
}
