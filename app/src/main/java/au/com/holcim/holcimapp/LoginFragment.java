package au.com.holcim.holcimapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;
import java.util.Map;

import au.com.holcim.holcimapp.network.ApiClient;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class LoginFragment extends Fragment {

    public interface LoginListener {
        void loginError(String error);
    }

    @Bind(R.id.sp_state) Spinner mSpStates;
    @Bind(R.id.et_mobile_number) EditText mEtMobileNumber;
    @Bind(R.id.btn_get_pin) Button mBtnGetPin;
    @Bind(R.id.av_indicator) AVLoadingIndicatorView mAvIndicator;

    LoginListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(LoginListener listener) {
        LoginFragment fragment = new LoginFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        mAvIndicator.hide();
        return view;
    }

    @OnClick(R.id.btn_get_pin)
    public void getPinOnClick(View view) {
        if(validate()) {
            toggleProgressIndicator(true);
            ApiClient.getService().requestSms(createParams()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    LoginFragment.this.toggleProgressIndicator(false);
                    if(response.isSuccessful()) {
                        //TODO
                    } else {
                        HolcimError error = HolcimError.fromResponse(response.errorBody(), response.code());
                        if(LoginFragment.this != null) {
                            LoginFragment.this.mListener.loginError(error.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    LoginFragment.this.toggleProgressIndicator(false);
                    t.printStackTrace();
                    LoginFragment.this.mListener.loginError("Failed to login, please try again.");
                }
            });
        }
    }

    private void toggleProgressIndicator(boolean show) {
        if(show) {
            mAvIndicator.show();
        } else {
            mAvIndicator.hide();
        }
        mEtMobileNumber.setEnabled(!show);
        mSpStates.setEnabled(!show);
        mBtnGetPin.setEnabled(!show);
    }

    private Map<String, String> createParams() {
        Map<String, String> params = new HashMap<>();
        if(mEtMobileNumber.getText().toString().length() > 0) {
            params.put("phone_number", "61" + mEtMobileNumber.getText().toString().substring(1));
        }
        params.put("state", mSpStates.getSelectedItem().toString());
        return params;
    }

    private boolean validate() {
        if(mEtMobileNumber.getText().toString().isEmpty()) {
            mListener.loginError("Please enter a mobile number.");
            return false;
        }
        return true;
    }

    @OnItemSelected(R.id.sp_state)
    public void onStateSelected(Spinner spinner, int position) {

    }
}
