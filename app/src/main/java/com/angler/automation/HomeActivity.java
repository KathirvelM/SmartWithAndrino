package com.angler.automation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.angler.automation.fragments.ActionFragment;
import com.appizona.yehiahd.fastsave.FastSave;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import java.util.ArrayList;
import java.util.Set;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.DeviceCallback;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = HomeActivity.class.getSimpleName();

    private ViewPager mViewPager;
    private NavigationTabStrip mTopNavigationTabStrip;
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    public static SupplyClass supplyClass = null;
    Bluetooth bluetooth = new Bluetooth(this);

    ImageView switch1, switch2, switch3, switch4, bluetoothActionBtn;
    LinearLayout inActiveLLay, activeLLay, emptyLay;
    Button clickToPairBtn;

    private ListView lstvw;
    private ArrayAdapter aAdapter;
    private BluetoothAdapter bAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initValues();
        initUI();
        initViewPager();
        updateValues();
        listeners();
        showDeviceList();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                blutooth1();
            }
        }, 2000);
    }

    private void showDeviceList() {
        if (bAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth Not Supported", Toast.LENGTH_SHORT).show();
        } else {
            Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
            final ArrayList<String> list = new ArrayList();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    String devicename = device.getName();
                    String macAddress = device.getAddress();
                    list.add(devicename);
                }
                lstvw = (ListView) findViewById(R.id.deviceList);
                aAdapter = new ArrayAdapter(getApplicationContext(), R.layout.list_item_device, list);
                lstvw.setAdapter(aAdapter);
                emptyLay.setVisibility(View.GONE);
                inActiveLLay.setVisibility(View.VISIBLE);
                lstvw.setVisibility(View.VISIBLE);

                lstvw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        connectToDevice(list.get(i));
                    }
                });
            } else {
                emptyLay.setVisibility(View.VISIBLE);
                inActiveLLay.setVisibility(View.VISIBLE);
                lstvw.setVisibility(View.GONE);
            }
        }
    }

    public void connectToDevice(String o) {
        if (bluetooth != null) {
            bluetooth.connectToName(o);
        }
    }

    private void blutooth1() {
        bluetooth.setDeviceCallback(new DeviceCallback() {
            @Override
            public void onDeviceConnected(BluetoothDevice device) {
                Log.d(TAG, "onDeviceConnected: ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activeLLay.setVisibility(View.VISIBLE);
                        inActiveLLay.setVisibility(View.GONE);
                        bluetoothActionBtn.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onDeviceDisconnected(BluetoothDevice device, String message) {
                Log.d(TAG, "onDeviceDisconnected: " + message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activeLLay.setVisibility(View.GONE);
                        bluetoothActionBtn.setVisibility(View.GONE);
                        inActiveLLay.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onMessage(String message) {
                Log.d(TAG, "onMessage: " + message);
            }

            @Override
            public void onError(String message) {
                Log.d(TAG, "onError: " + message);
            }

            @Override
            public void onConnectError(BluetoothDevice device, String message) {
                Log.d(TAG, "onConnectError: " + message);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bAdapter != null) {
            if (!bAdapter.isEnabled()) {
                bAdapter.enable();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activeLLay.setVisibility(View.GONE);
                        inActiveLLay.setVisibility(View.VISIBLE);
                    }
                }, 3000);
            } else {

            }
        } else {
            Toast.makeText(this, "Device bluetooth option not works", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bluetooth.onStart();
        bluetooth.enable();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bluetooth.onStop();
    }

    private void listeners() {
        switch1.setOnClickListener(this);
        switch2.setOnClickListener(this);
        switch3.setOnClickListener(this);
        switch4.setOnClickListener(this);

        clickToPairBtn.setOnClickListener(this);
        bluetoothActionBtn.setOnClickListener(this);

        ((MyApplication) getApplication()).setActionInterface(new ActionInterface() {
            @Override
            public void onButtonClick(int position) {
                switch (position) {
                    case 1:
                        supplyClass.setSwitch1(!supplyClass.isSwitch1());
                        updateValues();
                        break;

                    case 2:
                        supplyClass.setSwitch2(!supplyClass.isSwitch2());
                        updateValues();
                        break;

                    case 3:
                        supplyClass.setSwitch3(!supplyClass.isSwitch3());
                        updateValues();
                        break;

                    case 4:
                        supplyClass.setSwitch4(!supplyClass.isSwitch4());
                        updateValues();
                        break;
                }
            }
        });
    }

    private void updateValues() {
        if (supplyClass.isSwitch1())
            switch1.setBackgroundResource(R.drawable.bg_highligted);
        else
            switch1.setBackgroundResource(R.drawable.bg_non_highlighted);

        if (supplyClass.isSwitch2())
            switch2.setBackgroundResource(R.drawable.bg_highligted);
        else
            switch2.setBackgroundResource(R.drawable.bg_non_highlighted);

        if (supplyClass.isSwitch3())
            switch3.setBackgroundResource(R.drawable.bg_highligted);
        else
            switch3.setBackgroundResource(R.drawable.bg_non_highlighted);

        if (supplyClass.isSwitch4())
            switch4.setBackgroundResource(R.drawable.bg_highligted);
        else
            switch4.setBackgroundResource(R.drawable.bg_non_highlighted);
    }

    private void initValues() {
        supplyClass = FastSave.getInstance().getObject("Supply", SupplyClass.class);
        if (supplyClass == null) {
            supplyClass = new SupplyClass();
            FastSave.getInstance().saveObject("Supply", supplyClass);
        }
    }

    private void initUI() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mTopNavigationTabStrip = (NavigationTabStrip) findViewById(R.id.navigationTabStrip);
        switch1 = findViewById(R.id.ImageSwitch1);
        switch2 = findViewById(R.id.ImageSwitch2);
        switch3 = findViewById(R.id.ImageSwitch3);
        switch4 = findViewById(R.id.ImageSwitch4);

        bluetoothActionBtn = findViewById(R.id.bluetooth_action_btn);

        inActiveLLay = findViewById(R.id.inactive_lay);
        activeLLay = findViewById(R.id.active_lay);
        emptyLay = findViewById(R.id.empty_lis_lay);
        clickToPairBtn = findViewById(R.id.click_to_pair_btn);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImageSwitch1:
                mViewPager.setCurrentItem(0);
                break;

            case R.id.ImageSwitch2:
                mViewPager.setCurrentItem(1);
                break;

            case R.id.ImageSwitch3:
                mViewPager.setCurrentItem(2);
                break;

            case R.id.ImageSwitch4:
                mViewPager.setCurrentItem(3);
                break;

            case R.id.click_to_pair_btn:
                try {
                    Intent intentOpenBluetoothSettings = new Intent();
                    intentOpenBluetoothSettings.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
                    startActivity(intentOpenBluetoothSettings);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.bluetooth_action_btn:
                if(bluetooth!=null && bluetooth.isConnected()){
                    bluetooth.disconnect();
                }else{
                    activeLLay.setVisibility(View.GONE);
                    bluetoothActionBtn.setVisibility(View.GONE);
                    inActiveLLay.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new ActionFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(ActionFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    public void initViewPager() {
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mTopNavigationTabStrip.setViewPager(mViewPager);
    }

}
