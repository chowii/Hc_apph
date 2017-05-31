package au.com.holcim.holcimapp.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;

import android.widget.FrameLayout;

import java.util.List;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.alerts.Alert;
import au.com.holcim.holcimapp.alerts.AlertFragment;
import au.com.holcim.holcimapp.fragments.ContactFragment;
import au.com.holcim.holcimapp.fragments.OrdersFragment;
import au.com.holcim.holcimapp.helpers.BottomNavigationViewHelper;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.bottom_bar) BottomNavigationView mBottomBar;
    @Bind(R.id.fl_content_container) FrameLayout mFlContainer;
    private OrdersFragment mOrdersFragment;
    private SettingsFragment mSettingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        BottomNavigationViewHelper.disableShiftMode(mBottomBar);
        mBottomBar.getMenu().getItem(0).setCheckable(true);
        mBottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                handleBottomNavigationItemSelected(item);
                return true;
            }
        });
        mBottomBar.findViewById(R.id.action_orders).performClick();
    }

    private void handleBottomNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_orders:
                changeToOrdersFragment();
                break;
            case R.id.action_more:
                changeToSettingsFragment();
                break;
            case R.id.action_alerts:
                replaceFragment(AlertFragment.newInstance());
                break;
            case R.id.action_contact:
                replaceFragment(ContactFragment.newInstance());
            default: break;
        }
    }

    // MARK: - ================== Fragment management methods ==================

    private void changeToOrdersFragment() {
        if(mOrdersFragment == null) {
            mOrdersFragment = OrdersFragment.newInstance();
        }
        replaceFragment(mOrdersFragment);
    }

    private void changeToSettingsFragment() {
        if(mSettingsFragment == null) {
            mSettingsFragment = SettingsFragment.newInstance();
        }
        replaceFragment(mSettingsFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_content_container, fragment);
        transaction.commit();
    }
}
