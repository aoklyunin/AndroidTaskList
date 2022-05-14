package com.example.myapplication.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.data.Task;
import com.example.myapplication.data.CrimeLab;

import java.util.UUID;

public class CrimeFragment extends Fragment {
    private Task mTask;
    private EditText mTitleField;
    private EditText textField;
    private Button mDeleteButton;
    private CheckBox mSolvedCheckBox;
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        Log.e("CRIMEFRAGMENT ", crimeId + "");
        mTask = CrimeLab.get(getActivity()).getCrime(crimeId);
        Log.e("CRIMEFRAGMENT", mTask.toString());
    }

    @Override
    public void onPause() {
        super.onPause();
        CrimeLab.get(getActivity())
                .updateCrime(mTask);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mTask.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
                // Здесь намеренно оставлено пустое место
            }

            @Override
            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
                mTask.setTitle(c.toString());
            }

            @Override
            public void afterTextChanged(Editable c) {
                // И здесь тоже
            }
        });

        textField = (EditText) v.findViewById(R.id.crime_text);
        textField.setText(mTask.getText());
        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
                // Здесь намеренно оставлено пустое место
            }

            @Override
            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
                mTask.setText(c.toString());
            }

            @Override
            public void afterTextChanged(Editable c) {
                // И здесь тоже
            }
        });

        mDeleteButton = (Button) v.findViewById(R.id.crime_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.app_name);
                builder.setMessage("Do you want to stop ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CrimeLab.get(getActivity()).deleteCrime(mTask);
                        dialog.dismiss();
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mTask.isSolved());

        mSolvedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Назначение флага раскрытия преступления
            mTask.setSolved(isChecked);
        });

        return v;
    }



    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

    }
}
