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

import com.augmentis.ayp.yeutsen.R;
import com.augmentis.ayp.yeutsen.preference.YeutSenPreference;

import java.util.Calendar;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class WelcomeFragment extends Fragment {
    private static final String TAG = "WelcomeFragment";
    private static final int LENGTH_TIME_ALERT = 45;
    private Button gettingStartButton;
    private Calendar calTimeIn, calTimeOut;

    public static WelcomeFragment newInstance() {
        Bundle args = new Bundle();
        WelcomeFragment fragment = new WelcomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set default value to preference
        //
        //
        calTimeIn = Calendar.getInstance();
        calTimeIn.set(calTimeIn.get(Calendar.YEAR), calTimeIn.get(Calendar.MONTH), calTimeIn.get(Calendar.DATE), 8, 00, 00);

        calTimeOut = Calendar.getInstance();
        calTimeOut.set(calTimeOut.get(Calendar.YEAR), calTimeOut.get(Calendar.MONTH), calTimeOut.get(Calendar.DATE), 17, 00, 00);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("false,true,true,true,true,true,false");
        Log.d(TAG, "onCreate: StringBuffer " +stringBuffer);

        YeutSenPreference.setDateTimeIn(getActivity(),calTimeIn.getTime().getTime());
        YeutSenPreference.setDateTimeOut(getActivity(),calTimeOut.getTime().getTime());
        YeutSenPreference.setLengthTimeAlert(getActivity(), LENGTH_TIME_ALERT);
        YeutSenPreference.setDayOfWeek(getActivity(),stringBuffer.toString());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        gettingStartButton = (Button) v.findViewById(R.id.getting_start_button);
        gettingStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TutorialFragment fragment = TutorialFragment.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container ,fragment)
                        .commit();
            }
        });
        return v;
    }
}
