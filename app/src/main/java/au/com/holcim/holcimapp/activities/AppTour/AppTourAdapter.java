package au.com.holcim.holcimapp.activities.AppTour;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import au.com.holcim.holcimapp.R;

/**
 * Created by chowii on 23/05/17.
 *
 */

class AppTourAdapter extends FragmentStatePagerAdapter {

    private final int NUM_PAGES = 4;

    AppTourAdapter(FragmentManager fm) { super(fm); }

    @Override
    public Fragment getItem(int position) {
        AppTourPagerFragment tourFragment = AppTourPagerFragment.newInstance();
        switch(position){
            case 0:
                configureFragment(tourFragment,
                        R.drawable.img_delivery_list,
                        R.string.apptour_delivery_list_title_text,
                        R.string.apptour_delivery_list_desc_text);
                return tourFragment;
            case 1:
                configureFragment(tourFragment,
                        R.drawable.img_delivery_detail,
                        R.string.apptour_delivery_details_title_text,
                        R.string.apptour_delivery_details_desc_text);
                return tourFragment;
            case 2:
                configureFragment(tourFragment,
                        R.drawable.img_map,
                        R.string.apptour_map_title_text,
                        R.string.apptour_map_desc_text);
                return tourFragment;
            case 3:
                configureFragment(tourFragment,
                        R.drawable.img_alerts_2,
                        R.string.apptour_alert_title_text,
                        R.string.apptour_delivery_details_desc_text);
                return tourFragment;
        }
        return AppTourPagerFragment.newInstance();
    }

    private void configureFragment(Fragment fragment, @DrawableRes int imgRes, @StringRes int titleText, @StringRes int descText){
        ((AppTourPagerFragment)(fragment)).setImage(imgRes);
        ((AppTourPagerFragment)(fragment)).titleTextViewText(titleText);
        ((AppTourPagerFragment)(fragment)).descTextViewText(descText);
    }

    @Override
    public int getCount() { return NUM_PAGES; }

}
