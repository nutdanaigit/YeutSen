package com.augmentis.ayp.yeutsen.activity;

import android.support.v4.app.Fragment;

import com.augmentis.ayp.yeutsen.activity.SingleFragmentActivity;
import com.augmentis.ayp.yeutsen.fragment.SplashFragment;

public class StartActivity extends SingleFragmentActivity {

    @Override
    protected Fragment onCreateFragment() {
        return SplashFragment.newInstance();
    }
}
