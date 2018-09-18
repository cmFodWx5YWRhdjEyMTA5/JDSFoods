package com.jdsfoods.Utilities;

import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.jdsfoods.R;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by dikhong on 26-07-2018.
 */

public class JDSFood extends MultiDexApplication {

    private static JDSFood instance;
    public String SERVER_ERROR = "Unable to connect to server, please try again after sometime";


    @Override

    public void onCreate() {
        super.onCreate();
        instance = this;
        Fabric.with(this, new Crashlytics());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static JDSFood getInstance() {
        return instance;
    }
}
