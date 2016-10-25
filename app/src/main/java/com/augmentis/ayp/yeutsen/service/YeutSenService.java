package com.augmentis.ayp.yeutsen.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.augmentis.ayp.yeutsen.activity.MainActivity;
import com.augmentis.ayp.yeutsen.preference.YeutSenPreference;

import java.util.Date;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class YeutSenService extends IntentService {
    private static final String TAG = "YeutSenService";
    public static final String ACTION_SHOW_NOTIFICATION = "com.augmentis.ayp.yeutsen.service.YeutSenService.ACTION_SHOW_NOTIFICATION";
    public static final String RECEIVER_SHOW_NOTIFICATION = "com.augmentis.ayp.yeutsen.service.YeutSenService.RECEIVER_SHOW_NOTIFICATION";
    private static final String REQUEST_CODE = "request_code";
    private static final int REQUEST_CODE_ONE = 1;
    private static final int REQUEST_CODE_TWO = 2;

    public YeutSenService() {
        super(TAG);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, YeutSenService.class);
    }

    //    TODO : (DO d d) setServiceAlarm
    public static void setServiceAlarm(Context context, int requestCode) {
        Intent intentSetService = YeutSenService.newIntent(context.getApplicationContext());
        PendingIntent pi = PendingIntent.getService(context, 0, intentSetService, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        AlarmManager amTimeIn = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        switch (requestCode) {
            case REQUEST_CODE_ONE:
                Log.d(TAG, "setServiceAlarm: REQUEST_CODE_ONE for Start time in " );
                YeutSenPreference.setBtnOnStart(context, false);
//                amTimeIn.setRepeating(AlarmManager.RTC_WAKEUP, YeutSenPreference.getDateTimeIn(context),60*1000, pi);
                amTimeIn.set(AlarmManager.RTC_WAKEUP, YeutSenPreference.getDateTimeIn(context), pi);
                Log.d(TAG, "setServiceAlarm: time1 " + new Date(YeutSenPreference.getDateTimeIn(context)));

                break;
            case REQUEST_CODE_TWO:
                Log.d(TAG, "setServiceAlarm: REQUEST_CODE_TWO for set next time to alert ");
                am.set(AlarmManager.RTC_WAKEUP, YeutSenPreference.getDateToAlert(context), pi);
                Log.d(TAG, "setServiceAlarm: time2 " + new Date(YeutSenPreference.getDateToAlert(context)));
                break;
        }

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        Intent i = MainActivity.newIntent(this);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Context ctx = getApplicationContext();


        builder.setTicker("Time Up !!! ");
        builder.setContentTitle("Time Up !!! ");
        builder.setContentText(" :---- Stretch Sy ----: ");
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay);
        if(YeutSenPreference.isSwitchNotification(ctx)) {
            if(YeutSenPreference.getUriSound(ctx)!=null) {
                Uri soundUri = Uri.parse(YeutSenPreference.getUriSound(ctx));
                builder.setSound(soundUri);
            }
        }
        builder.setContentIntent(pi);
        if(YeutSenPreference.isVibrate(ctx)) builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setAutoCancel(true);

        Notification notification = builder.build();

        sendBackgroundNotification(0, notification);
        new YeutSenScreen().on(YeutSenService.this);

    }


    private void sendBackgroundNotification(int requestCode, Notification notification) {
        Intent intent = new Intent(ACTION_SHOW_NOTIFICATION);
        intent.putExtra(REQUEST_CODE, requestCode);
        intent.putExtra(RECEIVER_SHOW_NOTIFICATION, notification);
        sendOrderedBroadcast(intent, RECEIVER_SHOW_NOTIFICATION, null, null, Activity.RESULT_OK, null, null);

    }
}
