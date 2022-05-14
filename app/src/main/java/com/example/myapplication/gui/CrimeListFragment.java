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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.CrimePagerActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.Task;
import com.example.myapplication.data.TaskHolder;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";


    private RecyclerView mCrimeRecyclerView;

    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("LIST", "On create list");
        View view = inflater.inflate(R.layout.fragment_crime_list, container,
                false);
        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean
                    (SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();


        return view;
    }

    private void updateUI() {
        TaskHolder taskHolder = TaskHolder.get(getActivity());
        List<Task> tasks = taskHolder.getTasks();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(tasks);
            mCrimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCrimes(tasks);
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
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
            Intent intent = CrimePagerActivity.newIntent(getActivity(),
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

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
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
                Intent intent = CrimePagerActivity
                        .newIntent(getActivity(), task.getId());
                startActivity(intent);
                return true;
            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        TaskHolder taskHolder = TaskHolder.get(getActivity());
        int crimeCount = taskHolder.getTasks().size();

        String subtitle = crimeCount + " crimes";
        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }


    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Task> mTasks;

        public CrimeAdapter(List<Task> tasks) {
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