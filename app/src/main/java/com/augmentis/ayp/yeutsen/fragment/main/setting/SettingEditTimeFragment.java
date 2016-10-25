package com.augmentis.ayp.yeutsen.fragment.main.setting;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.augmentis.ayp.yeutsen.R;
import com.augmentis.ayp.yeutsen.activity.MainActivity;
import com.augmentis.ayp.yeutsen.fragment.main.firststretch.CheckTime;
import com.augmentis.ayp.yeutsen.loading.YeutSenLoading;
import com.augmentis.ayp.yeutsen.preference.YeutSenPreference;
import com.augmentis.ayp.yeutsen.service.YeutSenService;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class SettingEditTimeFragment extends Fragment {
    private static final String EXTRA_TIME = "TimeDialogFragment";
    private static final String DIALOG_TIME = "MainFragment";
    private static final String TAG = "MainFragment";
    private static final int FIRST_BTN = 1;
    private static final int SECOND_BTN = 2;

    private int tempValue;
    private CheckTime mCheckTime;
    private String checkDayOfWeek[] = new String[7];
    private String sTimeIn;
    private String sTimeOut;

    private Button buttonTimeIn;
    private Button buttonTimeOut;
    private Button buttonEnter;
    private CheckBox Sunday;
    private CheckBox Monday;
    private CheckBox Tuesday;
    private CheckBox Wednesday;
    private CheckBox Thursday;
    private CheckBox Friday;
    private CheckBox Saturday;
    private Calendar calTimeIn, calTimeOut;
    private DiscreteSeekBar mSeekBar;


    boolean flag[] = new boolean[7];

    Date startTimeDate, endTimeDate;
    Toolbar toolbarSettingTime;

    public static SettingEditTimeFragment newInstance() {
        Bundle args = new Bundle();
        SettingEditTimeFragment fragment = new SettingEditTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_set_time_edit, container, false);
        mSeekBar = (DiscreteSeekBar) view.findViewById(R.id.alert_seek_edit);
        buttonEnter = (Button) view.findViewById(R.id.enter_working);


        toolbarSettingTime = (Toolbar) view.findViewById(R.id.toolbar_set_time_1);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarSettingTime);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Setting Time");


        Sunday = (CheckBox) view.findViewById(R.id.sunday);
        Monday = (CheckBox) view.findViewById(R.id.monday);
        Tuesday = (CheckBox) view.findViewById(R.id.tuesday);
        Wednesday = (CheckBox) view.findViewById(R.id.wednesday);
        Thursday = (CheckBox) view.findViewById(R.id.thursday);
        Friday = (CheckBox) view.findViewById(R.id.friday);
        Saturday = (CheckBox) view.findViewById(R.id.saturday);


        mCheckTime = CheckTime.newInstance(getActivity());
        mCheckTime.setDayOfWeek();

        Sunday.setChecked(mCheckTime.isDayOfWeekFunction(1));
        Monday.setChecked(mCheckTime.isDayOfWeekFunction(2));
        Tuesday.setChecked(mCheckTime.isDayOfWeekFunction(3));
        Wednesday.setChecked(mCheckTime.isDayOfWeekFunction(4));
        Thursday.setChecked(mCheckTime.isDayOfWeekFunction(5));
        Friday.setChecked(mCheckTime.isDayOfWeekFunction(6));
        Saturday.setChecked(mCheckTime.isDayOfWeekFunction(7));
        checkWeekDay();

        Sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkWeekDay();
            }

        });

        Monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkWeekDay();
            }
        });

        Tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkWeekDay();
            }
        });

        Wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkWeekDay();
            }
        });

        Thursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkWeekDay();
            }
        });

        Friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkWeekDay();
            }
        });

        Saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkWeekDay();
            }
        });

        tempValue = YeutSenPreference.getLengthTimeAlert(getActivity());
        mSeekBar.setProgress(tempValue);
        mSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                tempValue = value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });


        calTimeIn = Calendar.getInstance();
        calTimeOut = Calendar.getInstance();


        buttonTimeIn = (Button) view.findViewById(R.id.button_time_in);
        sTimeIn = getFormattedTime(new Date(YeutSenPreference.getDateTimeIn(getActivity())));
        startTimeDate = new Date(YeutSenPreference.getDateTimeIn(getActivity()));
        buttonTimeIn.setText(sTimeIn);
        buttonTimeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                TimeDialogFragment dialogFragment = TimeDialogFragment.newInstance(startTimeDate);
                dialogFragment.setTargetFragment(SettingEditTimeFragment.this, FIRST_BTN);
                dialogFragment.show(fm, DIALOG_TIME);
                Log.d(TAG, "Time 1: ");
            }
        });

        buttonTimeOut = (Button) view.findViewById(R.id.button_time_out);
        sTimeOut = getFormattedTime(new Date(YeutSenPreference.getDateTimeOut(getActivity())));
        endTimeDate = new Date(YeutSenPreference.getDateTimeOut(getActivity()));
        buttonTimeOut.setText(sTimeOut);
        buttonTimeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                TimeDialogFragment dialogFragment = TimeDialogFragment.newInstance(endTimeDate);
                dialogFragment.setTargetFragment(SettingEditTimeFragment.this, SECOND_BTN);
                dialogFragment.show(fm, DIALOG_TIME);
                Log.d(TAG, "Time 2: ");
            }
        });



        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");


                StringBuffer result = new StringBuffer();



                result.append(Sunday.isChecked());
                result.append(",").append(Monday.isChecked());
                result.append(",").append(Tuesday.isChecked());
                result.append(",").append(Wednesday.isChecked());
                result.append(",").append(Thursday.isChecked());
                result.append(",").append(Friday.isChecked());
                result.append(",").append(Saturday.isChecked());

                Log.d(TAG, "onClick: Result " + result);


//                Set Value in SharedPref.
                YeutSenPreference.setDayOfWeek(getActivity(), result.toString());
                YeutSenPreference.setDateTimeIn(getActivity(), startTimeDate.getTime());
                YeutSenPreference.setDateTimeOut(getActivity(), endTimeDate.getTime());
                YeutSenPreference.setLengthTimeAlert(getActivity(), tempValue);


                Log.d(TAG, "onClick: setDateTimeIn " + new Date(YeutSenPreference.getDateTimeIn(getActivity())));
                Log.d(TAG, "onClick: setDateTimeOut " + new Date(YeutSenPreference.getDateTimeOut(getActivity())));
                Log.d(TAG, "onClick: setLength " + YeutSenPreference.getLengthTimeAlert(getActivity()));

                mCheckTime.setDayOfWeek();
                if (!mCheckTime.isDayOfWeekFunction(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))) {
                    mCheckTime.setTimeToAlertNextDay(); //set another day
                } else {
                    YeutSenService.setServiceAlarm(getActivity(), 1);
                }


                new YeutSenLoading(getActivity()).execute(1);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragmentPreviewTime = SettingPreviewSetTimeFragment.newInstance();
                fm.beginTransaction().replace(R.id.fragment_container, fragmentPreviewTime).commit();
            }
        });


        return view;
    }


    private String getFormattedTime(Date date) {
        return new SimpleDateFormat("hh:mm a").format(date);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult Therdsak : ");
        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case FIRST_BTN:
                    startTimeDate = (Date) data.getSerializableExtra(EXTRA_TIME);
                    Log.d(TAG, "onActivityResult: Cal1 " + startTimeDate);
                    buttonTimeIn.setText(getFormattedTime(startTimeDate));
                    break;
                case SECOND_BTN:
                    endTimeDate = (Date) data.getSerializableExtra(EXTRA_TIME);
                    Log.d(TAG, "onActivityResult: Cal2 " + endTimeDate);
                    buttonTimeOut.setText(getFormattedTime(endTimeDate));
                    break;
            }
        }
    }



    private void checkWeekDay() {
        if (!Sunday.isChecked() && !Monday.isChecked() && !Tuesday.isChecked()
                && !Wednesday.isChecked() && !Thursday.isChecked() && !Friday.isChecked()
                && !Saturday.isChecked()) {
            buttonEnter.setEnabled(false);
            buttonEnter.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        } else {
            buttonEnter.setEnabled(true);
            buttonEnter.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }

}
