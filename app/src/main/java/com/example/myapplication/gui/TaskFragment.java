package com.example.myapplication.gui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.data.Task;
import com.example.myapplication.data.TaskDBHolder;

import java.util.UUID;

/**
 * Фрагмент задачи
 */
public class TaskFragment extends Fragment {
    /**
     * Задача
     */
    private Task task;
    /**
     * Ключ для получения id задачи из намерения
     */
    private static final String ARG_TASK_ID = "task_id";

    /**
     * Создание фрагмента
     *
     * @param savedInstanceState - созранённое состояние фрагмента
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // получаем id из аргументов
        UUID taskId = (UUID) getArguments().getSerializable(ARG_TASK_ID);
        // получаем задачу по её id
        task = TaskDBHolder.get(getActivity()).getTask(taskId);
    }

    /**
     * Приостановка фрагмента
     */
    @Override
    public void onPause() {
        super.onPause();
        // обновляем задачу
        TaskDBHolder.get(getActivity()).updateTask(task);
    }

    /**
     * Создаение представления
     *
     * @param inflater           - объект для формирования представления
     * @param container          - контейнер
     * @param savedInstanceState - сохранённое состояние
     * @return - представление
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // формируем представление на основе разметки
        View v = inflater.inflate(R.layout.fragment_task, container, false);
        // Получаем поле ввода заголовка
        EditText mTitleField = (EditText) v.findViewById(R.id.task_title);
        // задаём ему значение заголовка задачи
        mTitleField.setText(task.getTitle());
        // вешаем обработчик изменения текста
        mTitleField.addTextChangedListener(new TextWatcher() {
            // операция перед изменением текста
            @Override
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // Здесь намеренно оставлено пустое место
            }

            // обработчик изменения текста
            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                // задаём новый заголовок задаче
                task.setTitle(c.toString());
            }

            // операция после изменения текста
            @Override
            public void afterTextChanged(Editable c) {
                // И здесь тоже
            }
        });
        // Получаем поле ввода текста
        EditText textField = (EditText) v.findViewById(R.id.task_text);
        // задаём ему значение текста задачи
        textField.setText(task.getText());
        // вешаем обработчик изменения текста
        textField.addTextChangedListener(new TextWatcher() {
            // операция перед изменением текста
            @Override
            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // Здесь намеренно оставлено пустое место
            }

            // обработчик изменения текста
            @Override
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                task.setText(c.toString());
            }

            // операция после изменения текста
            @Override
            public void afterTextChanged(Editable c) {
                // И здесь тоже
            }
        });

        // Получаем кнопку удаления
        Button mDeleteButton = (Button) v.findViewById(R.id.task_delete);
        // вешаем обработчик нажатия
        mDeleteButton.setOnClickListener(view -> {
            // получаем строителя диалогов
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            // задаём ему заголовок как у приложения
            builder.setTitle(R.string.app_name);
            // задаём текст диалога
            builder.setMessage(R.string.confirm_delete_question);
            // определяем кнопку да
            builder.setPositiveButton(R.string.confirm_delete_yes, (dialog, id) -> {
                // удаляем задачу
                TaskDBHolder.get(getActivity()).deleteTask(task);
                // скрываем диалог
                dialog.dismiss();
                // закрываем активнрость
                getActivity().finish();
            });
            // определяем кнопку нет, она просто закрывает диалог
            builder.setNegativeButton(R.string.confirm_delete_no, (dialog, id) -> dialog.dismiss());
            // создаём диалог
            AlertDialog alert = builder.create();
            // показываем его
            alert.show();
        });

        // получаем чек-бокс
        CheckBox mSolvedCheckBox = (CheckBox) v.findViewById(R.id.task_solved);
        // задаём ему значение в соответствии с состоянием задачи
        mSolvedCheckBox.setChecked(task.isSolved());
        // вешаем обработчик нажатия
        mSolvedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Назначение флага раскрытия преступления
            task.setSolved(isChecked);
        });

        return v;
    }

    /**
     * Создание нового фрагмента
     *
     * @param taskId - id задачи
     * @return - новый фрагмент
     */
    public static TaskFragment newInstance(UUID taskId) {
        // объект для работы фрагмента
        Bundle args = new Bundle();
        // добавляем ключ с id задачи
        args.putSerializable(ARG_TASK_ID, taskId);
        // создаём фрагмент
        TaskFragment fragment = new TaskFragment();
        // задаём фрагменту аргументы
        fragment.setArguments(args);
        // возвращаем фрагмент
        return fragment;
    }

}
