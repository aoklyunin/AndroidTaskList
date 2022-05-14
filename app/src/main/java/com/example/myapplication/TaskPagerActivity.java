package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.data.Task;
import com.example.myapplication.data.TaskHolder;
import com.example.myapplication.gui.TaskFragment;

import java.util.List;
import java.util.UUID;

/**
 * Активность для отображения задачи с возможностью перехода к следующей
 * и предыдущей
 */
public class TaskPagerActivity extends AppCompatActivity {
    /**
     * Список задач
     */
    private List<Task> mTasks;

    /**
     * Константа для передачи значений в интентах
     */
    private static final String EXTRA_TASK_ID = "com.example.myapplication.task_id";

    /**
     * Создаёт новый интент
     *
     * @param packageContext - контекст пакета
     * @param taskId         - id задачи
     * @return намерение
     */
    public static Intent newIntent(Context packageContext, UUID taskId) {
        Intent intent = new Intent(packageContext, TaskPagerActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        return intent;
    }

    /**
     * Обработка создания активности
     *
     * @param savedInstanceState - сохранённое состояние активности
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // задаём разметку
        setContentView(R.layout.activity_crime_pager);
        // получаем id задачи из интента
        UUID taskId = (UUID) getIntent().getSerializableExtra(EXTRA_TASK_ID);
        // создаём объект перемещения между задачами
        ViewPager mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        // получаем задачи
        mTasks = TaskHolder.get(this).getTasks();
        // создаём менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // задаём адаптер для переключения между задачами
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            // получить объект по его опложению
            @Override
            public Fragment getItem(int position) {
                // получаем задачу по положению
                Task task = mTasks.get(position);
                // создаём новый фрагмент на основе id задачи
                return TaskFragment.newInstance(task.getId());
            }

            // получить кол-во объектов
            @Override
            public int getCount() {
                // возвращаем кол-во задач
                return mTasks.size();
            }
        });
        // перебираем задачи
        for (int i = 0; i < mTasks.size(); i++) {
            // если id задачи равен id в переключаетеле страниц
            if (mTasks.get(i).getId().equals(taskId)) {
                // задаём соответствующее положение
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}