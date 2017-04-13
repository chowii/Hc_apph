package au.com.holcim.holcimapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import au.com.holcim.holcimapp.Constants;
import au.com.holcim.holcimapp.LoginFragment;
import au.com.holcim.holcimapp.NavHelper;
import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.SharedPrefsHelper;
import au.com.holcim.holcimapp.SmsVerificationFragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        showCorrectIntialFragment();
    }

    // MARK: - ================== Fragment related ==================

    private void showCorrectIntialFragment() {
        if(SharedPrefsHelper.getInstance().hasEnteredMobileAndState()) {
            changeToSmsVerifyFragment();
        } else {
            changeToLoginFragment();
        }
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
        Log.d("TEST", "Sms successful.");
    }
}
