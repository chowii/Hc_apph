package au.com.holcim.holcimapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 *
 */
public class SharedPrefsHelper {

    private static final String TAG = "SharedPrefsHelper";

    private static final String SHARED_PREFS_DEVICE_TOKEN = "SHARED_PREFS_DEVICE_TOKEN";
    private static final String SHARED_PREFS_AUTH_TOKEN = "SHARED_PREFS_AUTH_TOKEN";
    private static final String SHARED_PREFS_LAST_EMAIL = "SHARED_PREFS_LAST_EMAIL";

    private static SharedPrefsHelper INSTANCE = new SharedPrefsHelper();
    private SharedPreferences sharedPreferences;

    private SharedPrefsHelper() {
    }

    public static SharedPrefsHelper getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean haveAuthToken() {
        return !sharedPreferences.getString(SHARED_PREFS_AUTH_TOKEN, "").isEmpty();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(SHARED_PREFS_AUTH_TOKEN, "");
    }

    public void setAuthToken(String authToken) {
        Log.d(TAG, "puttin auth token in prefs : "+authToken);
        sharedPreferences.edit().putString(SHARED_PREFS_AUTH_TOKEN, authToken).apply();
    }

    public void removeAuthToken() {
        sharedPreferences.edit().remove(SHARED_PREFS_AUTH_TOKEN).apply();
    }

    public void setDeviceToken(String token) {
        sharedPreferences.edit().putString(SharedPrefsHelper.SHARED_PREFS_DEVICE_TOKEN, token).apply();
    }

    public String getDeviceToken() {
        return sharedPreferences.getString(SharedPrefsHelper.SHARED_PREFS_DEVICE_TOKEN, "");
    }

    public void removeDeviceToken() {
        sharedPreferences.edit().putString(SharedPrefsHelper.SHARED_PREFS_DEVICE_TOKEN, "").apply();
    }

    public void setLastUsedEmail(String email) {
        sharedPreferences.edit().putString(SharedPrefsHelper.SHARED_PREFS_LAST_EMAIL, email).apply();
    }

    public String getLastUsedEmail() {
        return sharedPreferences.getString(SharedPrefsHelper.SHARED_PREFS_LAST_EMAIL, "");
    }
}

