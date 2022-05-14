package com.example.myapplication;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.myapplication.gui.CrimeListFragment;
import com.example.myapplication.gui.SingleFragmentActivity;

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Log.e("CRIME ACTIVITY", "FRAGMENT");
        return new CrimeListFragment();
    }
}
