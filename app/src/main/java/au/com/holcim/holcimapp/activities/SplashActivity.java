package au.com.holcim.holcimapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.activities.AppTour.AppTourActivity;
import au.com.holcim.holcimapp.helpers.NavHelper;
import au.com.holcim.holcimapp.helpers.SharedPrefsHelper;
import au.com.holcim.holcimapp.models.User;
import au.com.holcim.holcimapp.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean seenTour = prefs.getBoolean("ShowAppTour",false);
        if(!seenTour) startActivity(new Intent(this, AppTourActivity.class));
        else {
            if(SharedPrefsHelper.getInstance().isLoggedIn()) {
                ApiClient.getService().getCurrentUser().enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        NavHelper.showMainActivity(SplashActivity.this);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        handleError(t, false, null);
                    }
                });
            } else {
                NavHelper.showLandingActivity(SplashActivity.this, null);
            }
        }
    }

    @Override
    public void handleCustomError(String error) {
        super.handleCustomError(error);
        NavHelper.showLandingActivity(this, "Failed to fetch account details, please try logging in again.");
    }
}
