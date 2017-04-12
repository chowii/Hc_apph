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
import android.view.View;
import android.widget.FrameLayout;

import au.com.holcim.holcimapp.Constants;
import au.com.holcim.holcimapp.LoginFragment;
import au.com.holcim.holcimapp.NavHelper;
import au.com.holcim.holcimapp.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginFragment.LoginListener {

    @Bind(R.id.fl_content_container) FrameLayout mFlContentContainer;
    @Bind(R.id.cl_login) CoordinatorLayout mClcontainer;
    LoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        changeToLoginFragment();
    }

    // MARK: - ================== Fragment related ==================

    private void changeToLoginFragment() {
        if(mLoginFragment == null) {
            mLoginFragment = LoginFragment.newInstance(this);
        }
        replaceFragment(mLoginFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(fragment instanceof LoginFragment) {
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

    @OnClick(R.id.btn_contact_holcim)
    void onClickContactHolcim(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + getResources().getString(R.string.contact_number)));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, Constants.RequestKey.requestPhonePermission);
            return;
        }
        startActivity(callIntent);
    }
    @OnClick(R.id.btn_cant_access_app)
    void onClickCantAccessApp(View view) {
        NavHelper.showWebView(this, getResources().getString(R.string.cant_access_app_url));
    }
}
