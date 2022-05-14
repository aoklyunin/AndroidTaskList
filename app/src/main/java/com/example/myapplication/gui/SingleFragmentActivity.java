package com.example.myapplication.gui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;


/**
 * Активность с одним фрагментом
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    /**
     * Метод, формирующий активность (необходимо переопределить в потомке)
     *
     * @return - фрагмент для активности
     */
    protected abstract Fragment createFragment();

    /**
     * Создание активности
     *
     * @param savedInstanceState - сохранённое состояние
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        // получаем менеджер фрагментов
        FragmentManager fm = getSupportFragmentManager();
        // получаем фрагмент
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        // если фрагмент не загружен
        if (fragment == null) {
            // создаём фрагмент
            fragment = createFragment();
            // создаём транзакцию фрагментов
            fm.beginTransaction()
                    // добавляем фрагмент внутрь контейнера
                    .add(R.id.fragmentContainer, fragment)
                    // применяем изменения
                    .commit();
        }
    }
}