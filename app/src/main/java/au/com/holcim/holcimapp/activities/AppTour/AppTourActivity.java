package au.com.holcim.holcimapp.activities.AppTour;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.activities.LoginActivity;
import au.com.holcim.holcimapp.activities.SplashActivity;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chowii on 23/05/17.
 *
 * Activity initializes and displays the view pager,
 * the view pager indicator and the button. Handles the button and
 * the view pager indicator logic.
 *
 */

public class AppTourActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private final String TAG = this.getClass().getSimpleName() ;
    AppTourAdapter mPagerAdapter;

    @Bind(R.id.apptour_pager)
    ViewPager mPager;

    @Bind(R.id.apptour_skip_button)
    TextView mSkipButton;

    ImageView[] mIndicatorImageArray;
    private int tourSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_tour);
        ButterKnife.bind(this);
        initView();

    }

    private void initView(){
        mPagerAdapter = new AppTourAdapter(getSupportFragmentManager());
        mPagerAdapter.getCount();
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(this);
        this.tourSize = mPagerAdapter.getCount();
        initButton();
        initIndicator();
    }

    private void initButton(){
        mSkipButton.setText(R.string.apptour_button_skip_text);
        Typeface ty = Typeface.createFromAsset(getAssets(), "fonts/arial.ttf");
        mSkipButton.setTypeface(ty, Typeface.BOLD);
        mSkipButton.setTextSize(12);
        mSkipButton.setOnClickListener(v -> {
            String checkString = getString(R.string.apptour_check_string);
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            boolean seenTour = pref.getBoolean(checkString, false);
            if(!seenTour){
                startActivity(new Intent(this, LoginActivity.class));
                pref.edit().putBoolean(checkString, true).apply();
            }else finish();

        });
    }

    private void initIndicator(){
        LinearLayout indicatorLayout = (LinearLayout) findViewById(R.id.layout_indicator);
        mIndicatorImageArray = new ImageView[tourSize];

        for(int i = 0; i < tourSize; i++){
          mIndicatorImageArray[i] = new ImageView(this);
            mIndicatorImageArray[i].setImageResource(R.drawable.ic_pager_unselected);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(12,0,12,0);
            indicatorLayout.addView(mIndicatorImageArray[i], params);
        }
        mIndicatorImageArray[0].setImageResource(R.drawable.ic_pager_selected);
    }

    @Override
    public void onPageSelected(int position) {
        for(int i = 0; i < tourSize; i++) mIndicatorImageArray[i].setImageResource(R.drawable.ic_pager_unselected);
        mIndicatorImageArray[position].setImageResource(R.drawable.ic_pager_selected);
        int textRes = (tourSize-1 == position)?
                R.string.apptour_button_next_text:
                R.string.apptour_button_skip_text;
        mSkipButton.setText(textRes);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageScrollStateChanged(int state) { }

}
