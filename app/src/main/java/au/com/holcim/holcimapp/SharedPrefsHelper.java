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
    private static final String SHARED_PREFS_LAST_STATE = "SHARED_PREFS_LAST_STATE";
    private static final String SHARED_PREFS_LAST_MOBILE = "SHARED_PREFS_LAST_MOBILE";

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

    // MARK: - ================== User related ==================

    public boolean isLoggedIn() {
        return !sharedPreferences.getString(SHARED_PREFS_AUTH_TOKEN, "").isEmpty();
    }

    public boolean hasEnteredMobileAndState() {
        return !sharedPreferences.getString(SHARED_PREFS_LAST_MOBILE, "").isEmpty() && !sharedPreferences.getString(SHARED_PREFS_LAST_STATE, "").isEmpty();
    }

    public void saveUserCredentials(User user) {
        sharedPreferences.edit()
                .putString(SHARED_PREFS_AUTH_TOKEN, user.authToken)
                .putString(SHARED_PREFS_LAST_MOBILE, user.phoneNumber)
                .putString(SHARED_PREFS_LAST_STATE, user.state)
                .apply();
    }

    public void removeUserCredentials() {
        sharedPreferences.edit()
                .putString(SHARED_PREFS_AUTH_TOKEN, "")
                .putString(SHARED_PREFS_LAST_MOBILE, "")
                .putString(SHARED_PREFS_LAST_STATE, "")
                .apply();
    }

    public String getLastUsedState() {
        return sharedPreferences.getString(SharedPrefsHelper.SHARED_PREFS_LAST_STATE, "");
    }

    public String getLastUsedMobile() {
        return sharedPreferences.getString(SharedPrefsHelper.SHARED_PREFS_LAST_MOBILE, "");
    }

    // MARK: - ================== Auth related ==================

    public String getAuthToken() {
        return sharedPreferences.getString(SHARED_PREFS_AUTH_TOKEN, "");
    }

    public void setAuthToken(String authToken) {
        Log.d(TAG, "puttin auth token in prefs : "+authToken);
        sharedPreferences.edit().putString(SHARED_PREFS_AUTH_TOKEN, authToken).apply();
    }

    // MARK: - ================== Device Token related ==================

    public void setDeviceToken(String token) {
        sharedPreferences.edit().putString(SharedPrefsHelper.SHARED_PREFS_DEVICE_TOKEN, token).apply();
    }

    public String getDeviceToken() {
        return sharedPreferences.getString(SharedPrefsHelper.SHARED_PREFS_DEVICE_TOKEN, "123"); //TODO: remove
    }
}

