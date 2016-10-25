package com.augmentis.ayp.yeutsen.fragment.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.augmentis.ayp.yeutsen.R;
import com.augmentis.ayp.yeutsen.activity.MainActivity;
import com.augmentis.ayp.yeutsen.fragment.main.firststretch.CheckTime;
import com.augmentis.ayp.yeutsen.loading.YeutSenLoading;
import com.augmentis.ayp.yeutsen.preference.YeutSenPreference;
import com.augmentis.ayp.yeutsen.service.YeutSenService;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class SetDayAndLengthFragment extends Fragment {
    private static final String TAG = "SetDayAndLengthFragment";
    private static final int INTEGER_PARAM_ASYNC = 2;
    private static final int REQUEST_CODE_ONE = 1;

    private Button backButton;
    private Button finishButton;
    private CheckBox sunday;
    private CheckBox monday;
    private CheckBox tuesday;
    private CheckBox wednesday;
    private CheckBox thursday;
    private CheckBox friday;
    private CheckBox saturday;
    private CheckTime mCheckTime;
    private DiscreteSeekBar alertSeekLength;
    private TextView setAlertLength;

    private int tempValue;
    private StringBuffer stringBuffer = new StringBuffer();
    private String checkDayOfWeek[] = new String[7];

    public static SetDayAndLengthFragment newInstance() {
        Bundle args = new Bundle();
        SetDayAndLengthFragment fragment = new SetDayAndLengthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get value from preference
        //
        //
        mCheckTime = CheckTime.newInstance(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day_and_length, container, false);
        alertSeekLength = (DiscreteSeekBar) v.findViewById(R.id.alert_seek);
        backButton = (Button) v.findViewById(R.id.day_and_length_back_button);
        setAlertLength = (TextView) v.findViewById(R.id.set_alert_length_text);
        finishButton = (Button) v.findViewById(R.id.day_and_length_finish_button);


        sunday = (CheckBox) v.findViewById(R.id.check_sunday);
        sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    checkDayOfWeek[0] = "true";
                } else {
                    checkDayOfWeek[0] = "false";
                }
                checkWeekDay();
            }
        });

        monday = (CheckBox) v.findViewById(R.id.check_monday);
        monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    checkDayOfWeek[1] = "true";
                } else{
                    checkDayOfWeek[1] = "false";
                }
                checkWeekDay();
            }
        });

        tuesday = (CheckBox) v.findViewById(R.id.check_tuesday);
        tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkDayOfWeek[2] = "true";
                }else{
                    checkDayOfWeek[2] = "false";
                }
                checkWeekDay();
            }
        });

        wednesday = (CheckBox) v.findViewById(R.id.check_wednesday);
        wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    checkDayOfWeek[3] = "true";
                }else{
                    checkDayOfWeek[3] = "false";
                }
                checkWeekDay();
            }
        });

        thursday = (CheckBox) v.findViewById(R.id.check_thursday);
        thursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkDayOfWeek[4] = "true";
                }else{
                    checkDayOfWeek[4] = "false";
                }
                checkWeekDay();
            }
        });

        friday = (CheckBox) v.findViewById(R.id.check_friday);
        friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    checkDayOfWeek[5] = "true";
                }else{
                    checkDayOfWeek[5] = "false";
                }
                checkWeekDay();
            }
        });

        saturday = (CheckBox) v.findViewById(R.id.check_saturday);
        saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    checkDayOfWeek[6] = "true";
                }else{
                    checkDayOfWeek[6] = "false";
                }
                checkWeekDay();
            }
        });

        checkWeekDay();
        mCheckTime.setDayOfWeek();

        sunday.setChecked(mCheckTime.isDayOfWeekFunction(1));
        monday.setChecked(mCheckTime.isDayOfWeekFunction(2));
        tuesday.setChecked(mCheckTime.isDayOfWeekFunction(3));
        wednesday.setChecked(mCheckTime.isDayOfWeekFunction(4));
        thursday.setChecked(mCheckTime.isDayOfWeekFunction(5));
        friday.setChecked(mCheckTime.isDayOfWeekFunction(6));
        saturday.setChecked(mCheckTime.isDayOfWeekFunction(7));


        tempValue = YeutSenPreference.getLengthTimeAlert(getActivity());
        alertSeekLength.setProgress(tempValue);
        alertSeekLength.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                setAlertLength.setText("Set alert length : " + alertSeekLength.getProgress() + " minutes");
                tempValue = value;

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });


        setAlertLength.setText("Set alert length : " + alertSeekLength.getProgress() + " minutes");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Set value to preference
                //
                //

                getStringBuffer();
                YeutSenPreference.setLengthTimeAlert(getActivity(),tempValue);

                SetTimeFragment fragment = SetTimeFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container ,fragment)
                        .commit();
            }
        });


        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set value to preference
                //
                //
                getStringBuffer();
                YeutSenPreference.setLengthTimeAlert(getActivity(),tempValue);
                YeutSenPreference.setFirstEnter(getActivity(),true);
                YeutSenPreference.setSwitchNotification(getActivity(),true);

                mCheckTime.setDayOfWeek();
                if (!mCheckTime.isDayOfWeekFunction(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))) {
                    mCheckTime.setTimeToAlertNextDay(); //set another day
                } else {
                    YeutSenService.setServiceAlarm(getActivity(), REQUEST_CODE_ONE);
                }

//                new YeutSenLoading(getActivity()).execute(INTEGER_PARAM_ASYNC);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });



        return v;
    }

    public void getStringBuffer(){
        stringBuffer.append(checkDayOfWeek[0]);
        stringBuffer.append(",").append(checkDayOfWeek[1]);
        stringBuffer.append(",").append(checkDayOfWeek[2]);
        stringBuffer.append(",").append(checkDayOfWeek[3]);
        stringBuffer.append(",").append(checkDayOfWeek[4]);
        stringBuffer.append(",").append(checkDayOfWeek[5]);
        stringBuffer.append(",").append(checkDayOfWeek[6]);
        YeutSenPreference.setDayOfWeek(getActivity(),stringBuffer.toString());
    }

    private void checkWeekDay(){
        if(!sunday.isChecked() && !monday.isChecked() && !tuesday.isChecked()
                && !wednesday.isChecked() && !thursday.isChecked() && !friday.isChecked()
                && !saturday.isChecked()){
            finishButton.setEnabled(false);
            finishButton.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        }else{
            finishButton.setEnabled(true);
            finishButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }
}
