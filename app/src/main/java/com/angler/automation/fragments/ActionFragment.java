package com.angler.automation.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angler.automation.HomeActivity;
import com.angler.automation.MyApplication;
import com.angler.automation.R;

/**
 * Created by kathirvel on 31-12-2018.
 */

public class ActionFragment extends Fragment {

    public static final String ARG_OBJECT = "object";
    ImageView actionSwitch;
    TextView statusText;
    int position = 0;
    public static String ACTIVE = "ACTIVE", INACTIVE = "INACTIVE";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_action, container, false);
        Bundle args = getArguments();
        position = args.getInt(ActionFragment.ARG_OBJECT, 0);

        init(rootView);

        return rootView;
    }

    private void init(View rootView) {
        actionSwitch = rootView.findViewById(R.id.on_off_img);
        statusText = rootView.findViewById(R.id.statusTXT);

        actionSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication.getInstances().getActionInterface().onButtonClick(position);
                updateView();
            }
        });

        updateView();
    }

    public void updateView() {
        switch (position) {
            case 1:
                if (HomeActivity.supplyClass.isSwitch1()) {
                    actionSwitch.setBackgroundResource(R.drawable.bg_switch_on);
                    actionSwitch.setColorFilter(Color.argb(255, 255, 255, 255));
                    statusText.setText(ACTIVE);
                } else {
                    actionSwitch.setBackgroundResource(R.drawable.bg_switch_off);
                    actionSwitch.setColorFilter(ContextCompat.getColor(getActivity(), R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                    statusText.setText(INACTIVE);
                }
                break;
            case 2:
                if (HomeActivity.supplyClass.isSwitch2()) {
                    actionSwitch.setBackgroundResource(R.drawable.bg_switch_on);
                    actionSwitch.setColorFilter(Color.argb(255, 255, 255, 255));
                    statusText.setText(ACTIVE);
                } else {
                    actionSwitch.setBackgroundResource(R.drawable.bg_switch_off);
                    actionSwitch.setColorFilter(ContextCompat.getColor(getActivity(), R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                    statusText.setText(INACTIVE);
                }
                break;
            case 3:
                if (HomeActivity.supplyClass.isSwitch3()) {
                    actionSwitch.setBackgroundResource(R.drawable.bg_switch_on);
                    actionSwitch.setColorFilter(Color.argb(255, 255, 255, 255));
                    statusText.setText(ACTIVE);
                } else {
                    actionSwitch.setBackgroundResource(R.drawable.bg_switch_off);
                    actionSwitch.setColorFilter(ContextCompat.getColor(getActivity(), R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                    statusText.setText(INACTIVE);
                }
                break;
            case 4:
                if (HomeActivity.supplyClass.isSwitch4()) {
                    actionSwitch.setBackgroundResource(R.drawable.bg_switch_on);
                    actionSwitch.setColorFilter(Color.argb(255, 255, 255, 255));
                    statusText.setText(ACTIVE);
                } else {
                    actionSwitch.setBackgroundResource(R.drawable.bg_switch_off);
                    actionSwitch.setColorFilter(ContextCompat.getColor(getActivity(), R.color.red), android.graphics.PorterDuff.Mode.MULTIPLY);
                    statusText.setText(INACTIVE);
                }
                break;
        }
    }
}