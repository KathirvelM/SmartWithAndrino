package com.angler.automation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.angler.automation.fragments.ActionFragment;
import com.appizona.yehiahd.fastsave.FastSave;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.DiscoveryCallback;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private NavigationTabStrip mTopNavigationTabStrip;
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    public static SupplyClass supplyClass = null;
    Bluetooth bluetooth = new Bluetooth(this);

    ImageView switch1, switch2, switch3, switch4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initValues();
        initUI();
        initViewPager();
        updateValues();
        listeners();

        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBtAdapter != null) {
            if (!mBtAdapter.isEnabled()) {
                mBtAdapter.enable();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // blutooth();
                    }
                }, 3000);
            } else {
                blutooth1();
                //blutooth();
            }
        } else {
            Toast.makeText(this, "Device bluetooth option not works", Toast.LENGTH_SHORT).show();
        }

        /*
        * BluetoothConfiguration config = new BluetoothConfiguration();
config.context = getApplicationContext();
config.bluetoothServiceClass = BluetoothLeService.class; // BluetoothClassicService.class or BluetoothLeService.class
config.bufferSize = 1024;
config.characterDelimiter = '\n';
config.deviceName = "Your App Name";
config.callListenersInMainThread = true;

// Bluetooth Classic
config.uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Required

// Bluetooth LE
config.uuidService = UUID.fromString("e7810a71-73ae-499d-8c15-faa9aef0c3f2"); // Required
config.uuidCharacteristic = UUID.fromString("bef8d6c9-9c21-4c9e-b632-bd58c1009f9f"); // Required
config.transport = BluetoothDevice.TRANSPORT_LE; // Required for dual-mode devices
config.uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Used to filter found devices. Set null to find all devices.

BluetoothService.init(config);

BluetoothService service = BluetoothService.getDefaultInstance();

service.setOnScanCallback(new BluetoothService.OnBluetoothScanCallback() {
    @Override
    public void onDeviceDiscovered(BluetoothDevice device, int rssi) {
    }

    @Override
    public void onStartScan() {
    }

    @Override
    public void onStopScan() {
    }
});

service.startScan(); // See also service.stopScan();

service.setOnEventCallback(new BluetoothService.OnBluetoothEventCallback() {
    @Override
    public void onDataRead(byte[] buffer, int length) {
    }

    @Override
    public void onStatusChange(BluetoothStatus status) {
    }

    @Override
    public void onDeviceName(String deviceName) {
    }

    @Override
    public void onToast(String message) {
    }

    @Override
    public void onDataWrite(byte[] buffer) {
    }
});

service.connect(device); // See also service.disconnect();

BluetoothWriter writer = new BluetoothWriter(service);

writer.writeln("Your text here");*/
    }

    private void blutooth1() {



    }

    /*private void blutooth() {
        BluetoothConfiguration config = new BluetoothConfiguration();
        config.context = getApplicationContext();
        config.bluetoothServiceClass = BluetoothClassicService.class; // BluetoothClassicService.class or BluetoothLeService.class
        config.bufferSize = 1024;
        config.characterDelimiter = '\n';
        //config.deviceName = "Home Automation";
        config.callListenersInMainThread = true;

        // Bluetooth Classic
        config.uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // Required

        BluetoothService.init(config);

        BluetoothService service = BluetoothService.getDefaultInstance();

        service.setOnScanCallback(new BluetoothService.OnBluetoothScanCallback() {
            @Override
            public void onDeviceDiscovered(BluetoothDevice device, int rssi) {
                Log.d(HomeActivity.class.getSimpleName(), "onDeviceDiscovered: ");
            }

            @Override
            public void onStartScan() {
                Log.d(HomeActivity.class.getSimpleName(), "onStartScan: ");
            }

            @Override
            public void onStopScan() {
                Log.d(HomeActivity.class.getSimpleName(), "onStopScan: ");
            }
        });

        service.startScan(); // See also service.stopScan();

        *//*service.setOnEventCallback(new BluetoothService.OnBluetoothEventCallback() {
            @Override
            public void onDataRead(byte[] buffer, int length) {

            }

            @Override
            public void onStatusChange(BluetoothStatus status) {

            }

            @Override
            public void onDeviceName(String deviceName) {

            }

            @Override
            public void onToast(String message) {

            }

            @Override
            public void onDataWrite(byte[] buffer) {

            }
        });*//*
    }*/

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
