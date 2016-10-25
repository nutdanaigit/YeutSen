package com.augmentis.ayp.yeutsen.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.augmentis.ayp.yeutsen.preference.YeutSenPreference;
import com.augmentis.ayp.yeutsen.service.YeutSenService;

import java.util.Calendar;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class YeutSenBroadcastBootCompleted extends BroadcastReceiver {
    private static final String TAG = "BroadcastBootCompleted";
    private static final int REQUEST_CODE_INT_TWO = 2;

    public YeutSenBroadcastBootCompleted() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: YeutSenBootBroadcast");

//        TODO:(DO d d) if else
        if(Calendar.getInstance().getTime().getTime() < YeutSenPreference.getDateToAlert(context)){
            YeutSenService.setServiceAlarm(context,REQUEST_CODE_INT_TWO);
        }
    }
}
