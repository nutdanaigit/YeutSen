package com.augmentis.ayp.yeutsen.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.augmentis.ayp.yeutsen.fragment.main.MainFragment;
import com.augmentis.ayp.yeutsen.fragment.main.firststretch.FirstStretchFragment;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class MainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment onCreateFragment() {
        return MainFragment.newInstance();
//        return MainFragment.newInstance();
    }

    public static Intent newIntent(Context context){
        return new Intent(context,MainActivity.class);
    }
}
