package com.angler.automation;

import android.app.Application;

import com.appizona.yehiahd.fastsave.FastSave;

/**
 * Created by kathirvel on 02-01-2019.
 */

public class MyApplication extends Application {

    public ActionInterface actionInterface = null;
    public static MyApplication myApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();

        FastSave.init(getApplicationContext());

        if (myApplication == null) {
            myApplication = this;
        }
    }

    public ActionInterface getActionInterface() {
        return actionInterface;
    }

    public void setActionInterface(ActionInterface actionInterface) {
        this.actionInterface = actionInterface;
    }

    public static MyApplication getInstances() {
        return myApplication;
    }
}
