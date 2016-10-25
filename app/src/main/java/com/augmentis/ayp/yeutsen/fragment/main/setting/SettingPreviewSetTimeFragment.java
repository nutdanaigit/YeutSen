package com.augmentis.ayp.yeutsen.fragment.main.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.augmentis.ayp.yeutsen.R;
import com.augmentis.ayp.yeutsen.fragment.main.MainFragment;
import com.augmentis.ayp.yeutsen.fragment.main.firststretch.CheckTime;
import com.augmentis.ayp.yeutsen.preference.YeutSenPreference;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class SettingPreviewSetTimeFragment extends Fragment {

    private Toolbar toolbarSetTime;
    private CheckTime mCheckTime;
    private String string;
    private StringBuffer s = new StringBuffer();
    private TextView mTxtTimeDay;
    private TextView mTxtLengthAlert;
    private TextView mTxtDayOfWeek;
    private FragmentManager fm;
    private Button buttonSolve;
    private Button buttonCancel;

    public static SettingPreviewSetTimeFragment newInstance() {

        Bundle args = new Bundle();

        SettingPreviewSetTimeFragment fragment = new SettingPreviewSetTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_set_time,container,false);
        toolbarSetTime  = (Toolbar) view.findViewById(R.id.toolbar_set_time);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarSetTime);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Setting Time");

        mCheckTime = CheckTime.newInstance(getActivity());

        for (int i = 1; i <= 7; i++) {
            if (mCheckTime.isDayOfWeekFunction(i)) {
                switch (i) {
                    case 1:
                        string = "Sunday";
                        break;
                    case 2:
                        string = "Monday";
                        break;
                    case 3:
                        string = "Tuesday";
                        break;
                    case 4:
                        string = "Wednesday";
                        break;
                    case 5:
                        string = "Thursday";
                        break;
                    case 6:
                        string = "Friday";
                        break;
                    case 7:
                        string = "Saturday";
                        break;
                }
                s.append(string).append("\n");
            }
        }

        mTxtTimeDay = (TextView) view.findViewById(R.id.txt_time_day);
        mTxtDayOfWeek = (TextView) view.findViewById(R.id.txt_set_day);
        mTxtLengthAlert = (TextView) view.findViewById(R.id.txt_length_Alert);


        mTxtTimeDay.setText(getFormattedTime(new Date(YeutSenPreference.getDateTimeIn(getActivity()))) + " - " + getFormattedTime(new Date(YeutSenPreference.getDateTimeOut(getActivity()))));
        mTxtDayOfWeek.setText(s.toString());
        mTxtLengthAlert.setText(YeutSenPreference.getLengthTimeAlert(getActivity()).toString());


        fm = getActivity().getSupportFragmentManager();



        buttonSolve = (Button) view.findViewById(R.id.button_solve);
        buttonSolve.setOnClickListener(new View.OnClickListener() {
            public static final String TAG = "SettingSetTimeFragment";

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                Fragment fragmentSolve = SettingEditTimeFragment.newInstance();
                fm.beginTransaction().replace(R.id.fragment_container, fragmentSolve).commit();
            }
        });

        buttonCancel = (Button) view.findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragmentCancel = MainFragment.newInstance();
                fm.beginTransaction().replace(R.id.fragment_container, fragmentCancel).commit();

            }
        });


        return view;
    }




    private String getFormattedTime(Date date) {
        return new SimpleDateFormat("hh:mm a").format(date);
    }
}

