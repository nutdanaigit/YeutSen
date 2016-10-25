package com.augmentis.ayp.yeutsen.service;

import android.content.Context;
import android.os.PowerManager;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class YeutSenScreen {
    private static final String  SCREEN_CLASS_TAG = "SCREEN";

    public YeutSenScreen(){}

    public void on(Context context){
        PowerManager powerManager =(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if(!powerManager.isScreenOn()) {
            PowerManager.WakeLock wl = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                    PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, SCREEN_CLASS_TAG);
            wl.acquire();

//            KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//            final KeyguardManager.KeyguardLock kl = km.newKeyguardLock("MyKeyguardLock");
//            kl.disableKeyguard();


        }
    }
}
