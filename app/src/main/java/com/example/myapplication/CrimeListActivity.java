package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        Log.e("CRIME ACTIVITY", "FRAGMENT");
        return new CrimeListFragment();
    }
}
