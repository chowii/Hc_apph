package au.com.holcim.holcimapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import au.com.holcim.holcimapp.Constants;
import au.com.holcim.holcimapp.activities.AppTour.AppTourActivity;
import au.com.holcim.holcimapp.fragments.LoginFragment;
import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.helpers.NavHelper;
import au.com.holcim.holcimapp.helpers.SharedPrefsHelper;
import au.com.holcim.holcimapp.fragments.SmsVerificationFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginListener, SmsVerificationFragment.SmsVerificationListener {

    @Bind(R.id.fl_content_container) FrameLayout mFlContentContainer;
    @Bind(R.id.cl_login) CoordinatorLayout mClcontainer;
    LoginFragment mLoginFragment;
    SmsVerificationFragment mSmsVerificationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean seenTour = prefs.getBoolean(getString(R.string.apptour_check_string),false);
        if(!seenTour) startActivity(new Intent(this, AppTourActivity.class));
        else {
            changeToLoginFragment();
            String reason = getIntent().getStringExtra(Constants.Extras.REASON);
            if (reason != null) {
                Snackbar.make(mClcontainer, reason, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    // MARK: - ================== Fragment related ==================

    private void showCorrectIntialFragment() {

    }

    private void changeToLoginFragment() {
        if(mLoginFragment == null) {
            mLoginFragment = LoginFragment.newInstance(this);
        }
        replaceFragment(mLoginFragment);
    }

    private void changeToSmsVerifyFragment() {
        if(mSmsVerificationFragment == null) {
            mSmsVerificationFragment = SmsVerificationFragment.newInstance(this);
        }
        replaceFragment(mSmsVerificationFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(fragment instanceof SmsVerificationFragment) {
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        } else {
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        }
        transaction.replace(R.id.fl_content_container, fragment);
        transaction.commit();
    }

    // MARK: - ================== LoginListener methods ==================

    @Override
    public void loginError(String error) {
        Snackbar.make(mClcontainer, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccessful() {
        changeToSmsVerifyFragment();
    }


    // MARK: - ================== SmsVerifyListener methods ==================

    @Override
    public void getNewPin() {
        SharedPrefsHelper.getInstance().removeUserCredentials();
        changeToLoginFragment();
    }

    @Override
    public void smsVerificationFailed(String error) {
        Snackbar.make(mClcontainer, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void smsVerificationSuccessful() {
        NavHelper.showMainActivity(this);
    }
}
