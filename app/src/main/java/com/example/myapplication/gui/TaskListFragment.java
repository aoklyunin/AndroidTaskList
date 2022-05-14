package com.example.myapplication.gui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.TaskPagerActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.Task;
import com.example.myapplication.data.TaskDBHolder;
import com.example.myapplication.req.JsontestAPI;
import com.example.myapplication.req.ServerResponse;
import com.example.myapplication.req.TasksRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Фрагмент списка задач
 */
public class TaskListFragment extends Fragment {
    /**
     * представление списка фрагментов
     */
    private RecyclerView taskRecyclerView;
    /**
     * Адаптер задач
     */
    private TaskAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_task_list, container,
                false);
        // получаем список
        taskRecyclerView = (RecyclerView) view.findViewById(R.id.task_recycler_view);
        // вешаем на него обработчик разметки
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // получаем кнопку отправки
        Button sendButton = view.findViewById(R.id.auth_btn);
        // вешаем обработчик нажатия
        sendButton.setOnClickListener(view1 -> {
            // содаём асинхронную задачу
            SendAsyncTask sendTask = new SendAsyncTask();
            // запускаем отправку задач
            sendTask.execute();
        });
        // обновляем интерфейс
        updateUI();
        // возвращаем представление
        return view;
    }

    // обновляем интерфейс
    private void updateUI() {
        // получаем помощник для работы с задачами
        TaskDBHolder taskDBHolder = TaskDBHolder.get(getActivity());
        // получаем список задач
        List<Task> tasks = taskDBHolder.getTasks();
        // если адаптер не создан
        if (adapter == null) {
            // создаём адаптер
            adapter = new TaskAdapter(tasks);
            // задаём его списку
            taskRecyclerView.setAdapter(adapter);
        } else {
            // задаём задачи адапетру
            adapter.setTasks(tasks);
            // говорим адаптеру, тчо надо обновить данные
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Класс асинхронной задачи для отправки
     */
    private class SendAsyncTask extends AsyncTask<Void, Void, Void> {
        /**
         * Фоновое выполнение
         *
         * @param voids - аргументов нет
         * @return - возвращаем null
         */
        @Override
        protected Void doInBackground(Void... voids) {
            // создаём клиент retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://buran-it-sch.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // Создаём объект для запросов
            JsontestAPI api = retrofit.create(JsontestAPI.class);

            // получаем помощник для работы с задачами
            TaskDBHolder taskDBHolder = TaskDBHolder.get(getActivity());
            // получаем список задач
            List<Task> tasks = taskDBHolder.getTasks();
            // формируем запрос
            Call<ServerResponse> serverCall = api.add(new TasksRequest(
                    "droid", tasks
            ));
            // выполняем запрос
            serverCall.enqueue(new Callback<ServerResponse>() {
                // если получен ответ сервера
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    // получаем тело ответа сервера
                    ServerResponse serverResponse = response.body();
                    if (serverResponse.getStatus().equals("ok"))
                        // выводим сообщение об удачной отправке
                        Toast.makeText(getContext(), "Задачи отправлены", Toast.LENGTH_SHORT).show();
                    else
                        // выводим сообщение об ошибке
                        Toast.makeText(getContext(), "Ошибка: " + serverResponse.getStatus(), Toast.LENGTH_SHORT).show();
                }

                // если произошла ошибка
                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    // выводим сообщение с ошибкой
                    Toast.makeText(getContext(), "Ошибка: " + t, Toast.LENGTH_SHORT).show();
                }
            });
            // возвращаем null, т.к. это процесс ничего не должен возвращать
            return null;
        }
    }


    /**
     * Помощник для работы с задачами в списке
     */
    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * текстовое поле заголовка
         */
        private final TextView titleView;
        /**
         * текстовое поле текста
         */
        private final TextView textView;
        /**
         * чек-бокс, решена ли задача
         */
        private final CheckBox solvedCheckBox;
        /**
         * Задача
         */
        private Task task;

        /**
         * Конструктор помощника
         *
         * @param itemView - представление элемента сипсика
         */
        public TaskHolder(View itemView) {
            super(itemView);
            // получаем текстовое поле заголовка
            titleView = (TextView) itemView.findViewById(R.id.list_item_task_title_text_view);
            // получаем текстовое поле текста
            textView = (TextView) itemView.findViewById(R.id.list_item_task_date_text_view);
            // получаем чек-бокс, решена ли задача
            solvedCheckBox = (CheckBox) itemView.findViewById(R.id.list_item_task_solved_check_box);
            // вешаем оброаботчик кликов
            itemView.setOnClickListener(this);
        }

        /**
         * Связываем элемент списка с задачей
         *
         * @param task - задача
         */
        public void bindTask(Task task) {
            // задача
            this.task = task;
            // задаём текстовое поле заголовка
            titleView.setText(this.task.getTitle());
            // задаём текстовое поле текста
            textView.setText(this.task.getText());
            // задаём чек-бокс, решена ли задача
            solvedCheckBox.setChecked(this.task.isSolved());
        }

        /**
         * Обработчик нажатия
         *
         * @param v - представление
         */
        @Override
        public void onClick(View v) {
            // получаем намерение, кладём в него id
            Intent intent = TaskPagerActivity.newIntent(getActivity(), task.getId());
            // запускаем активность
            startActivity(intent);
        }

    }

    /**
     * Создание активности
     *
     * @param savedInstanceState - созранённое состояние
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // говорим, что фрагмент имеет меню
        setHasOptionsMenu(true);
    }

    /**
     * Создание элементов меню
     *
     * @param menu     - меню
     * @param inflater - объект для формирования представления
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task_list, menu);
    }

    /**
     * Восстановление активности
     */
    @Override
    public void onResume() {
        super.onResume();
        // обновлем интерфейс
        updateUI();
    }

    /**
     * Обработчик клика по меню
     *
     * @param item - выбранный элемент
     * @return - получилось ли обработать
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получаем id выбранного элемента меню
        switch (item.getItemId()) {
            // кнопка новой задачи
            case R.id.menu_item_new_task:
                // создаём задачу
                Task task = new Task();
                // добавляем задачу
                TaskDBHolder.get(getActivity()).addTask(task);
                // создаём намерение с id задачи
                Intent intent = TaskPagerActivity.newIntent(getActivity(), task.getId());
                // запускаем активность
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Адаптер для списка задач
     */
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        /**
         * Список задач
         */
        private List<Task> tasks;

        /**
         * Конструктор адаптера списка задач
         *
         * @param tasks - список задач
         */
        public TaskAdapter(List<Task> tasks) {
            // сохраняем список задач
            this.tasks = tasks;
        }

        /**
         * Обработчик создания представления
         *
         * @param parent   - предок
         * @param viewType - тип представления
         * @return - обёртка элемента списка
         */
        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_task, parent, false);
            return new TaskHolder(view);
        }

        /**
         * Обработчик связывания
         *
         * @param holder   - обертка элемента
         * @param position - положение
         */
        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            // получаем задачу
            Task task = tasks.get(position);
            holder.bindTask(task);
        }

        /**
         * Получить кол-во элементов
         *
         * @return - кол-во элементов
         */
        @Override
        public int getItemCount() {
            return tasks.size();
        }

        /**
         * Задать список задач
         *
         * @param tasks - список задач
         */
        public void setTasks(List<Task> tasks) {
            this.tasks = tasks;
        }
    }


}