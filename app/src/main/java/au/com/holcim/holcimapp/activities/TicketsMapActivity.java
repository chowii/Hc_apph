package au.com.holcim.holcimapp.activities;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import au.com.holcim.holcimapp.Constants;
import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.fragments.TicketCarouselFragment;
import au.com.holcim.holcimapp.helpers.AlertDialogHelper;
import au.com.holcim.holcimapp.models.Order;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.tooltip.Tooltip;

public class TicketsMapActivity extends BaseActivity implements OnMapReadyCallback, ViewPager.OnPageChangeListener, GoogleMap.OnMapLoadedCallback {

    Order mOrder;
    int mSelectedTicketId;
    @Bind(R.id.btn_left) ImageButton mBtnLeft;
    @Bind(R.id.btn_right) ImageButton mBtnRight;
    @Bind(R.id.btn_info) ImageButton mBtnInfo;
    @Bind(R.id.vp_tickets) ViewPager mVpTickets;
    SupportMapFragment mMapFragment;
    private TicketCarouselAdapter mPagerAdapter;
    private GoogleMap mMap;
    private boolean isMapLoaded = false;

    private List<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_map);
        ButterKnife.bind(this);
        mMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        if(getIntent().getParcelableExtra(Constants.Extras.ORDER) != null && getIntent().getIntExtra(Constants.Extras.TICKET_ID, 0) != 0) {
            mOrder = getIntent().getParcelableExtra(Constants.Extras.ORDER);
            mSelectedTicketId = getIntent().getIntExtra(Constants.Extras.TICKET_ID, 0);
        } else {
            AlertDialogHelper.showOk(this, "Error", "Failed to pass order.",
                    (dialogInterface, i) -> TicketsMapActivity.this.finish());
        }
        populateView();
        mPagerAdapter = new TicketCarouselAdapter(getSupportFragmentManager(), mOrder);
        mVpTickets.setAdapter(mPagerAdapter);
        mVpTickets.addOnPageChangeListener(this);
        mVpTickets.setCurrentItem(mOrder.getTicketIndexWithId(mSelectedTicketId));
        hidePagerNavButtonsIfNeeded();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLoadedCallback(this);
        mMap.setTrafficEnabled(true);
        mMap.setPadding(0, 0, 0, 200);
    }

    private void hidePagerNavButtonsIfNeeded() {
        int currentSelectedIndex = mVpTickets.getCurrentItem();
        mBtnLeft.setVisibility(currentSelectedIndex <= 0 ? View.GONE : View.VISIBLE);
        mBtnRight.setVisibility(currentSelectedIndex == (mOrder.tickets.size() - 1) ? View.GONE : View.VISIBLE);
    }

    private void populateView() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        hidePagerNavButtonsIfNeeded();
        setMapMarkers();
    }

    private void setMapMarkers() {
        if(isMapLoaded) {
            mMap.clear();
            markers = new ArrayList<>();
            addMapMarker(mOrder.getLatLng(), -1);
            addMapMarker(mOrder.tickets.get(mVpTickets.getCurrentItem()).getLatLng(), R.drawable.map_truck);
            animateCameraToFitMarkers();
        }
    }

    private void animateCameraToFitMarkers() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Marker marker: markers) {
            builder.include(marker.getPosition());
        }
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), 32);
        mMap.animateCamera(cameraUpdate);
    }

    private void addMapMarker(LatLng latLng, int icon) {
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(latLng));
        if(icon != -1) {
            marker.setIcon(BitmapDescriptorFactory.fromResource(icon));
        }
        markers.add(marker);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.btn_right, R.id.btn_left})
    void carouselNavigationOnClick(View view) {
        int nextIndex = mVpTickets.getCurrentItem() + (view.getId() == R.id.btn_left ? -1 : 1);
        if(nextIndex >= 0 && nextIndex < mOrder.tickets.size()) {
            mVpTickets.setCurrentItem(nextIndex);
        }
    }

    @OnClick(R.id.btn_close)
    void closeOnClick(View view) {
        finish();
    }

    @OnClick(R.id.btn_info)
    void infoOnClick(View view) {
        Tooltip.make(this, new Tooltip.Builder(101)
                .withStyleId(R.style.ToolTipLayoutStyle)
                .anchor(mBtnInfo, Tooltip.Gravity.RIGHT)
                .closePolicy(new Tooltip.ClosePolicy()
                        .insidePolicy(true, false)
                        .outsidePolicy(true, false), 3000)
                .activateDelay(800)
                .text("Delivery items are for guide only.")
                .withArrow(true)
                .withOverlay(true)
                .build()).show();
    }

    @Override
    public void onMapLoaded() {
        isMapLoaded = true;
        setMapMarkers();
    }

    private class TicketCarouselAdapter extends FragmentStatePagerAdapter {

        Order mOrder;

        public TicketCarouselAdapter(FragmentManager fm, Order order) {
            super(fm);
            this.mOrder = order;
        }

        @Override
        public Fragment getItem(int position) {
            return TicketCarouselFragment.newInstance(mOrder, position);
        }

        @Override
        public int getCount() {
            return mOrder.tickets.size();
        }
    }
}
