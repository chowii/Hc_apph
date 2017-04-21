package au.com.holcim.holcimapp.activities;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.fragments.OrdersFragment;
import au.com.holcim.holcimapp.helpers.BottomNavigationViewHelper;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.bottom_bar) BottomNavigationView mBottomBar;
    @Bind(R.id.fl_content_container) FrameLayout mFlContainer;
    private OrdersFragment mOrdersFragment;

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

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if(fragment instanceof SmsVerificationFragment) {
//            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
//        } else {
//            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
//        }
        transaction.replace(R.id.fl_content_container, fragment);
        transaction.commit();
    }
}
