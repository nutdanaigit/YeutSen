package com.augmentis.ayp.yeutsen.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.augmentis.ayp.yeutsen.R;
import com.augmentis.ayp.yeutsen.fragment.main.YeutsenAlertDialogFragment;
import com.augmentis.ayp.yeutsen.fragment.main.firststretch.FirstStretchFragment;
import com.augmentis.ayp.yeutsen.service.YeutSenService;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity implements FirstStretchFragment.CallBack{
    private static final String TAG = "SingleFragmentActivity";
    public boolean REQUEST_BOOLEAN_ACTIVITY = true;

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");
            setResultCode(Activity.RESULT_CANCELED);
            Log.d(TAG, "onReceive: Finish");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = onCreateFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }else{
            //Do Some things
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        REQUEST_BOOLEAN_ACTIVITY = true;
        IntentFilter filter = new IntentFilter(YeutSenService.ACTION_SHOW_NOTIFICATION);
        this.registerReceiver(mBroadcastReceiver, filter, YeutSenService.RECEIVER_SHOW_NOTIFICATION, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        REQUEST_BOOLEAN_ACTIVITY = false;
        this.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void getDialog() {
        Log.d(TAG, "getDialog: ");
        if (REQUEST_BOOLEAN_ACTIVITY) {
            Log.d(TAG, "getDialog: isTrue");
            YeutsenAlertDialogFragment yeutSenDialogFragment = new YeutsenAlertDialogFragment();
            yeutSenDialogFragment.show(getSupportFragmentManager(), "test");
        }
    }

    protected abstract Fragment onCreateFragment ();
}
