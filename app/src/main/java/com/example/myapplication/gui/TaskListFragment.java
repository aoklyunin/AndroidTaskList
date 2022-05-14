package com.example.myapplication.gui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.TaskPagerActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.Task;
import com.example.myapplication.data.TaskHolder;

import java.util.List;

/**
 * Фрагмент списка задач
 */
public class TaskListFragment extends Fragment {
    /**
     * представление списка фрагментов
     */
    private RecyclerView mTaskRecyclerView;
    /**
     * Адаптер задач
     */
    private TaskAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("LIST", "On create list");
        View view = inflater.inflate(R.layout.fragment_crime_list, container,
                false);
        mTaskRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));

        updateUI();


        return view;
    }

    private void updateUI() {
        TaskHolder taskHolder = TaskHolder.get(getActivity());
        List<Task> tasks = taskHolder.getTasks();

        if (mAdapter == null) {
            mAdapter = new TaskAdapter(tasks);
            mTaskRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCrimes(tasks);
            mAdapter.notifyDataSetChanged();
        }

    }


    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;
        private Task mTask;

        public CrimeHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_crime_solved_check_box);
            itemView.setOnClickListener(this);

        }

        public void bindCrime(Task task) {
            mTask = task;
            mTitleTextView.setText(mTask.getTitle());
            mDateTextView.setText(mTask.getText());
            mSolvedCheckBox.setChecked(mTask.isSolved());
        }

        @Override
        public void onClick(View v) {
            Intent intent = TaskPagerActivity.newIntent(getActivity(),
                    mTask.getId());
            startActivity(intent);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Task task = new Task();
                TaskHolder.get(getActivity()).addTask(task);
                Intent intent = TaskPagerActivity
                        .newIntent(getActivity(), task.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class TaskAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Task> mTasks;

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bindCrime(task);
        }

        @Override
        public int getItemCount() {
            return mTasks.size();
        }

        public void setCrimes(List<Task> tasks) {
            mTasks = tasks;
        }
    }


}