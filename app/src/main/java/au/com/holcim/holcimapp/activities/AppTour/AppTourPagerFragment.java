package au.com.holcim.holcimapp.activities.AppTour;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import au.com.holcim.holcimapp.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chowii on 23/05/17.
 */

public class AppTourPagerFragment extends Fragment {

    @Bind(R.id.apptour_image_view)
    ImageView tourImage;

    @Bind(R.id.titleTextView)
    TextView titleTextView;
    int mTitleText;

    @Bind(R.id.descTextView)
    TextView descTextView;
    private int mDescText;

    private int mImageResource;

    public static AppTourPagerFragment newInstance(){
        AppTourPagerFragment fragment = new AppTourPagerFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_apptour, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView(){
        tourImage.setImageResource(mImageResource);
        titleTextView.setText(mTitleText);
        descTextView.setText(mDescText);
    }


    protected void setImage(@DrawableRes int imgRes){ mImageResource = imgRes; }

    protected void titleTextViewText(int title){ this.mTitleText = title; }

    protected void descTextViewText(int desc){ this.mDescText = desc;}

    @Override
    public void onResume() {
        super.onResume();
        ButterKnife.unbind(this);
    }
}

