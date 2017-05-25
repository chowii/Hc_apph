package au.com.holcim.holcimapp.activities;

import android.os.Bundle;

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
           if(SharedPrefsHelper.getInstance().isLoggedIn()) {
                ApiClient.getService().getCurrentUser().enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        NavHelper.showMainActivity(SplashActivity.this);
                        SharedUser.getInstance().setUser(response.body());
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

    @Override
    public void handleCustomError(String error) {
        super.handleCustomError(error);
        NavHelper.showLandingActivity(this, "Failed to fetch account details, please try logging in again.");
    }
}
