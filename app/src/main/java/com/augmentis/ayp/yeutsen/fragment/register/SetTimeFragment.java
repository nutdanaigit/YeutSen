package com.augmentis.ayp.yeutsen.fragment.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.augmentis.ayp.yeutsen.R;
import com.augmentis.ayp.yeutsen.preference.YeutSenPreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class SetTimeFragment extends Fragment {

    private static final String TAG = "SetTimeFragment";

    private Boolean isStartClick;
    private Boolean isFinishClick;

    private Button startTimeButton;
    private Button finishTimeButton;
    private Button nextButton;
    private Calendar calendarTimeIn, calendarTimeOut;
    private Date dateTimeIn,dateTimeOut,d2,d3;
    private LinearLayout startTimeLayout;
    private LinearLayout finishTimeLayout;
    private TimePicker startTimePicker;
    private TimePicker finishTimePicker;

    private int hourTimeIn,hourTimeOut,minuteTimeIn,minuteTimeOut;

    private String sTimeIn;
    private String sTimeOut;

    public static SetTimeFragment newInstance() {
        Bundle args = new Bundle();
        SetTimeFragment fragment = new SetTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isStartClick = false;
        isFinishClick = false;
        dateTimeIn = new Date(YeutSenPreference.getDateTimeIn(getActivity()));
        dateTimeOut = new Date(YeutSenPreference.getDateTimeOut(getActivity()));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set_time, container, false);
        startTimeLayout = (LinearLayout) v.findViewById(R.id.start_time_linear);
        finishTimeLayout = (LinearLayout) v.findViewById(R.id.finish_time_linear);
        startTimeButton = (Button) v.findViewById(R.id.start_time_label);
        finishTimeButton = (Button) v.findViewById(R.id.finish_time_label);

        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isStartClick){
                    startTimeLayout.animate().setDuration(1000).translationY(startTimeButton.getBottom()/2).alpha(1.0f).start();
                    finishTimeButton.animate().setDuration(1000).translationY(startTimeLayout.getBottom()).start();
                    finishTimeLayout.animate().setDuration(1000).translationY(+1000).alpha(0.0f).start();
                    isStartClick = true;
                    isFinishClick = false;
                }else{
                    startTimeLayout.animate().setDuration(1000).translationY(-1000).alpha(0.0f).start();
                    finishTimeLayout.animate().setDuration(1000).translationY(+1000).alpha(0.0f).start();
                    finishTimeButton.animate().setDuration(1000).translationY(0).start();
                    isStartClick = false;
                }
            }
        });


        hourTimeIn = dateTimeIn.getHours();
        hourTimeOut = dateTimeOut.getHours();
        minuteTimeIn =dateTimeIn.getMinutes();
        minuteTimeOut = dateTimeOut.getMinutes();

        startTimePicker = (TimePicker) v.findViewById(R.id.start_time_picker);
        startTimePicker.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        startTimePicker.setHour(hourTimeIn);
        startTimePicker.setMinute(minuteTimeIn);

        getCalendarTimeIn(hourTimeIn,minuteTimeIn);
        getCalendarTimeOut(hourTimeOut,minuteTimeOut);



        finishTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFinishClick){
                    isFinishClick = true;
                    isStartClick = false;
                    finishTimeButton.animate().setDuration(1000).translationY(0).start();
                    finishTimeLayout.animate().setDuration(1000).translationY(finishTimeButton.getBottom() - 90).alpha(1.0f).start();
                    startTimeLayout.animate().setDuration(1000).translationY(-1000).alpha(0.0f).start();
                }else{
                    isFinishClick = false;
                    finishTimeButton.animate().setDuration(1000).translationY(0).start();
                    startTimeLayout.animate().setDuration(1000).translationY(-1000).alpha(0.0f).start();
                    finishTimeLayout.animate().setDuration(1000).translationY(+1000).alpha(0.0f).start();
                }
            }
        });

        finishTimePicker = (TimePicker) v.findViewById(R.id.finish_time_picker);
        finishTimePicker.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        finishTimePicker.setHour(hourTimeOut);
        finishTimePicker.setMinute(minuteTimeOut);

        startTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                getCalendarTimeIn(i,i1);
                startTimeButton.setText("Start Time : "+ getFormattedTime(new Date(calendarTimeIn.getTime().getTime())));

            }
        });

        finishTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                getCalendarTimeOut(i,i1);
                finishTimeButton.setText("Finish Time : " + getFormattedTime(new Date(calendarTimeOut.getTime().getTime())));
            }
        });


        nextButton = (Button) v.findViewById(R.id.time_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save value to preference
                //
                //
                hourTimeIn = startTimePicker.getHour();
                hourTimeOut = finishTimePicker.getHour();
                minuteTimeIn = startTimePicker.getMinute();
                minuteTimeOut = finishTimePicker.getMinute();


                getCalendarTimeIn(hourTimeIn,minuteTimeIn);
                getCalendarTimeOut(hourTimeOut,minuteTimeOut);


                YeutSenPreference.setDateTimeIn(getActivity(),calendarTimeIn.getTime().getTime());
                YeutSenPreference.setDateTimeOut(getActivity(),calendarTimeOut.getTime().getTime());

                SetDayAndLengthFragment fragment = SetDayAndLengthFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container ,fragment)
                        .commit();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startTimeButton.setText("Start Time : "+getFormattedTime(new Date(calendarTimeIn.getTime().getTime())));
        finishTimeButton.setText("Finish Time : " + getFormattedTime(new Date(calendarTimeOut.getTime().getTime())));

        startTimeLayout.animate().alpha(0.0f).start();
        finishTimeLayout.animate().alpha(0.0f).start();

        finishTimeButton.setY(-startTimeLayout.getBottom());
        startTimeLayout.setY(-startTimeLayout.getBottom());

    }

    private void getCalendarTimeIn(int hour,int minute){
        calendarTimeIn = Calendar.getInstance();
        calendarTimeIn.set(calendarTimeIn.get(Calendar.YEAR), calendarTimeIn.get(Calendar.MONTH), calendarTimeIn.get(Calendar.DATE), hour, minute, 00);

    }
    private void getCalendarTimeOut(int hour,int minute){
        calendarTimeOut = Calendar.getInstance();
        calendarTimeOut.set(calendarTimeOut.get(Calendar.YEAR), calendarTimeOut.get(Calendar.MONTH), calendarTimeOut.get(Calendar.DATE), hour, minute, 00);

    }

    private String getFormattedTime(Date date) {
        return new SimpleDateFormat("hh:mm a").format(date);
    }
}
