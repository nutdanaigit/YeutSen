package com.augmentis.ayp.yeutsen.broadcast;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.augmentis.ayp.yeutsen.fragment.main.firststretch.CheckTime;
import com.augmentis.ayp.yeutsen.preference.YeutSenPreference;
import com.augmentis.ayp.yeutsen.service.YeutSenService;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class YeutSenBroadcastNotification extends BroadcastReceiver {
    private static final String TAG = "YeutSenBroadcastNoti";

    public YeutSenBroadcastNotification() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Notification calling");
        CheckTime.newInstance(context).getLoopTime();
        if (getResultCode() != Activity.RESULT_OK) {
            Log.d(TAG, "onReceive: Cancel");
            context.stopService(intent);
            return;
        }

        if (YeutSenPreference.isBtnOnStart(context)) {
            if(YeutSenPreference.isSwitchNotification(context)) {
                Notification notification = intent.getParcelableExtra(YeutSenService.RECEIVER_SHOW_NOTIFICATION);
                //ไปเอา object notification มาจากตัว Intent
                NotificationManagerCompat.from(context).notify(0, notification);
            }
        }
        Log.i(TAG, "Notify new item displayed");
    }
}
