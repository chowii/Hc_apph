package au.com.holcim.holcimapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.SettingsAdapter;
import au.com.holcim.holcimapp.SwitchItemListener;
import au.com.holcim.holcimapp.fragments.BaseFragment;
import au.com.holcim.holcimapp.helpers.NavHelper;
import au.com.holcim.holcimapp.models.Settings;
import au.com.holcim.holcimapp.models.SettingsItem;
import au.com.holcim.holcimapp.network.ApiClient;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends BaseFragment implements SwitchItemListener, SettingsAdapter.OnItemClickListener, View.OnClickListener {

    SettingsAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    Settings mSettings;
    @Bind(R.id.pb_loading) ProgressBar mPbLoading;
    @Bind(R.id.tv_error_message) TextView mTvError;
    @Bind(R.id.ll_error_overlay) LinearLayout mLlErrorOverlay;
    @Bind(R.id.rv_settings) RecyclerView mRecyclerView;

    public SettingsFragment() {
        // Required empty public constructor
    }

    // MARK: - ================== LifeCycle ==================

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        setToolbar("", R.drawable.logo);
        retrieveSettings();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    // MARK: - ================== Setup ==================

    private void setupAdapter() {
        mAdapter = new SettingsAdapter(getActivity(), this, this, mSettings);
        mAdapter.addListener(this)
                .setAnimationOnScrolling(true)
                .setAnimationOnReverseScrolling(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDisplayHeadersAtStartUp(true)
                .areHeadersSticky();
        mAdapter.setDisplayHeadersAtStartUp(true);
    }

    // MARK: - ================== Api ==================

    private void retrieveSettings() {
        mPbLoading.setVisibility(View.VISIBLE);
        SettingsFragment.this.mPbLoading.setVisibility(View.GONE);
        ApiClient.getService().getSettings().enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                SettingsFragment.this.mPbLoading.setVisibility(View.GONE);
                SettingsFragment.this.mSettings = response.body();
                SettingsFragment.this.setupAdapter();
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                SettingsFragment.this.mPbLoading.setVisibility(View.GONE);
                SettingsFragment.this.handleError(t, false, null);
            }
        });
    }

    private void updateReceivePushNotificaitons(String key, boolean receive) {
        Map<String, Object> params = new HashMap<>();
        params.put(key, receive);
        ApiClient.getService().updateSettings(params).enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                SettingsFragment.this.mSettings = response.body();
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                handleError(t, true, null);
                SettingsFragment.this.mAdapter.updatePushNotificationsEnabled(!receive);
            }
        });
    }

    @Override
    public void handleCustomError(String error) {
        mLlErrorOverlay.setVisibility(View.VISIBLE);
        mTvError.setText(error);
    }

    // MARK: - ================== SwitchItemListener ==================

    @Override
    public void switchItemCheckChanged(String key, boolean checked) {
        updateReceivePushNotificaitons(key, checked);
    }

    // MARK: - ================== OnClicks ==================

    @OnClick(R.id.btn_retry)
    public void onClick() {
        retrieveSettings();
    }

    @Override
    public boolean onItemClick(int position) {
        if(mAdapter.getItem(position) instanceof SettingsItem) {
            getActivity().startActivity(((SettingsItem) mAdapter.getItem(position)).mIntent);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        //Only one action for now, logout
        NavHelper.logout(getActivity());
    }
}
