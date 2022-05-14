package com.example.myapplication;


import androidx.fragment.app.Fragment;
import com.example.myapplication.gui.TaskListFragment;
import com.example.myapplication.gui.SingleFragmentActivity;


/**
 * Активность со списком задач
 */
public class TaskListActivity extends SingleFragmentActivity {
    /**
     * Создание фрагмента для активности
     * @return - фрагмент для активности
     */
    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }
}
