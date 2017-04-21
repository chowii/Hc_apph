package au.com.holcim.holcimapp.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.goodiebag.pinview.Pinview;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;

import au.com.holcim.holcimapp.Constants;
import au.com.holcim.holcimapp.network.HolcimError;
import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.models.User;
import au.com.holcim.holcimapp.helpers.SharedPrefsHelper;
import au.com.holcim.holcimapp.network.ApiClient;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;


public class SmsVerificationFragment extends BaseFragment {

    private static final String TAG = "SignUpVerifyActivity";
    public interface SmsVerificationListener {
        void getNewPin();
        void smsVerificationFailed(String error);
        void smsVerificationSuccessful();
    }

    @Bind(R.id.pinview) Pinview mPinview;
    @Bind(R.id.fl_content_container) FrameLayout mFlContainer;
    @Bind(R.id.av_indicator) AVLoadingIndicatorView mAvIndicator;

    SmsVerificationListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sms_verification, container, false);
        ButterKnife.bind(this, view);
        mPinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean b) {
                submitPin();
            }
        });
        mAvIndicator.hide();
        return view;
    }

    public static SmsVerificationFragment newInstance(SmsVerificationListener listener) {
        SmsVerificationFragment fragment = new SmsVerificationFragment();
        fragment.mListener = listener;
        return fragment;
    }

    private Map<String, Object> createParams() {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> deviceParams = new HashMap<>();
        deviceParams.put("type", "DeviceGcm");
        deviceParams.put("token", SharedPrefsHelper.getInstance().getDeviceToken());
        params.put("device", deviceParams);
        params.put("pin_code", mPinview.getValue());
        params.put("state", SharedPrefsHelper.getInstance().getLastUsedState());
        params.put("phone_number", SharedPrefsHelper.getInstance().getLastUsedMobile());
        return params;
    }

    private void submitPin() {
        toggleProgressIndicator(true);
        ApiClient.getService().verifySms(createParams()).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                SmsVerificationFragment.this.toggleProgressIndicator(false);
                SharedPrefsHelper.getInstance().saveUserCredentials(response.body());
                mListener.smsVerificationSuccessful();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
                SmsVerificationFragment.this.toggleProgressIndicator(false);
                SmsVerificationFragment.this.handleError(t, false, null);
            }
        });
    }

    @Override
    public void handleCustomError(String error) {
        mListener.smsVerificationFailed(error);
    }

    private void toggleProgressIndicator(boolean show) {
        if(show) {
            mAvIndicator.show();
        } else {
            mAvIndicator.hide();
        }
        mPinview.setEnabled(!show);
    }

    // MARK: - ================== Onclicks ==================

    @OnClick(R.id.btn_contact_holcim)
    void onClickContactHolcim(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + getResources().getString(R.string.contact_number)));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, Constants.RequestKey.requestPhonePermission);
            return;
        }
        startActivity(callIntent);
    }

    @OnClick(R.id.btn_get_new_pin)
    void onClickCantAccessApp(View view) {
        mListener.getNewPin();
    }
}
