package au.com.holcim.holcimapp;

import android.app.Application;

import au.com.holcim.holcimapp.helpers.SharedPrefsHelper;

/**
 * Created by Jovan on 12/4/17.
 */

public class HolcimApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefsHelper.getInstance().init(this);
        Constants.init(this);
    }

}
