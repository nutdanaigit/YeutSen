package com.augmentis.ayp.yeutsen.fragment.main.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.augmentis.ayp.yeutsen.R;
import com.augmentis.ayp.yeutsen.preference.YeutSenPreference;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class SettingNotificationFragment extends Fragment {
    private static final String TAG = "SettingNotificationsFragment";
    public static long[] VibrateOn;
    Switch mSwitchNotification;
    String chosenRingtone;
    Toolbar toolbarNotification;
    TextView mTextView;
    TextView mTextViewName;
    TextView mTextViewVibrate;
    Switch mSwitchVibrate;
    Uri uri;


    public static SettingNotificationFragment newInstance() {

        Bundle args = new Bundle();
        args.putLongArray("long", VibrateOn);
        SettingNotificationFragment fragment = new SettingNotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_notification, container, false);
        toolbarNotification = (Toolbar) view.findViewById(R.id.toolbar_notification);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarNotification);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Notification");
//        Log.d(TAG, "onCreateView1: ");

        mSwitchNotification = (Switch) view.findViewById(R.id.setting_stretching_switch_message);
        mSwitchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Log.d(TAG, "onActivityResult11: " + YeutSenPreference.isSwitchNotification(getActivity()));
                if (isChecked) {
                    Visible();
                    YeutSenPreference.setSwitchNotification(getActivity(), isChecked);
                } else {
                    Invisible();
                    YeutSenPreference.setSwitchNotification(getActivity(), isChecked);
                    delete();
                }
            }
        });


        mTextView = (TextView) view.findViewById(R.id.setting_stretching_text_ring_tone);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetupSoundNotification();
            }
        });

        mTextViewName = (TextView) view.findViewById(R.id.setting_stretching_text_ring_tone_name);
        mTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetupSoundNotification();
            }
        });
        if (YeutSenPreference.getUriSound(getActivity()) != null) {
            mTextViewName.setText(RingtoneManager.getRingtone(getActivity(), Uri.parse(YeutSenPreference.getUriSound(getActivity()))).getTitle(getActivity()));
        }else{
            mTextViewName.setText("None");
        }


        mTextViewVibrate = (TextView) view.findViewById(R.id.setting_stretching_text_vibrate);


        mSwitchVibrate = (Switch) view.findViewById(R.id.setting_switch_vibrate);
        mSwitchVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    YeutSenPreference.setVibrate(getActivity(), isChecked);
                    VibrateOn = new long[]{1000, 1000, 1000, 1000, 1000};
                    Log.d(TAG, "onSuccess: " + VibrateOn);
                } else {
                    YeutSenPreference.setVibrate(getActivity(), isChecked);
                    VibrateOn = null;
                }

            }
        });

        UpdateUI();
        return view;
    }

    public void UpdateUI() {
        boolean b = YeutSenPreference.isSwitchNotification(getActivity());
        mSwitchNotification.setChecked(b);
        if (b) {
            Visible();
        } else {
            Invisible();
        }
        boolean c = YeutSenPreference.isVibrate(getActivity());
        mSwitchVibrate.setChecked(c);
    }

    public void delete() {

        VibrateOn = null;
        Log.d(TAG, "delete: ");
    }


    public void SetupSoundNotification() {
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        if (YeutSenPreference.getUriSound(getActivity()) == null) {
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
        } else {
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(YeutSenPreference.getUriSound(getActivity())));
        }
        startActivityForResult(intent, 5);
    }

    public void Visible() {
        mTextView.setVisibility(View.VISIBLE);
        mTextViewName.setVisibility(View.VISIBLE);
        mTextViewVibrate.setVisibility(View.VISIBLE);
        mSwitchVibrate.setVisibility(View.VISIBLE);
    }

    public void Invisible() {
        mTextView.setVisibility(View.INVISIBLE);
        mTextViewName.setVisibility(View.INVISIBLE);
        mTextViewVibrate.setVisibility(View.INVISIBLE);
        mSwitchVibrate.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {
                uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                Log.d(TAG, "onActivityResult12: " + uri);

                chosenRingtone =(uri !=null)?  uri.toString():null;
                Log.d(TAG, "onActivityResult15: " + chosenRingtone);
                Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), uri);
                String title = (chosenRingtone!= null)?ringtone.getTitle(getActivity()): "None";
                mTextViewName.setText(title);
                YeutSenPreference.setUriSound(getActivity(), chosenRingtone);
                Log.d(TAG, "onActivityResult: " + YeutSenPreference.getUriSound(getActivity()));

            } else {
                Log.d(TAG, "onActivityResult: Cancel ");
            }
        }
    }

}











