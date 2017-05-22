package au.com.holcim.holcimapp.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

import au.com.holcim.holcimapp.AlertSettingsAdapter;
import au.com.holcim.holcimapp.Constants;
import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.SwitchItemListener;
import au.com.holcim.holcimapp.models.Settings;
import au.com.holcim.holcimapp.network.ApiClient;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertSettingsActivity extends BaseActivity implements SwitchItemListener {

    AlertSettingsAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    Settings mSettings;
    @Bind(R.id.rv_settings) RecyclerView mRecyclerView;

    Map<String, Object> updateParams = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_settings);
        ButterKnife.bind(this);
        setToolbar("", true);
        if(getIntent().getParcelableExtra(Constants.Extras.SETTINGS) != null) {
            mSettings = getIntent().getParcelableExtra(Constants.Extras.SETTINGS);
            setupAdapter();
        }
    }

    // MARK: - ================== Setup ==================

    private void setupAdapter() {
        mAdapter = new AlertSettingsAdapter(this, this, mSettings);
        mAdapter.addListener(this)
                .setAnimationOnScrolling(true)
                .setAnimationOnReverseScrolling(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDisplayHeadersAtStartUp(true)
                .areHeadersSticky();
        mAdapter.setDisplayHeadersAtStartUp(true);
    }

    // MARK: - ================== ApiCall ==================

    private void updateAlertSettings() {
        ApiClient.getService().updateSettings(updateParams).enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                finish();
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                handleError(t, true, null);
            }
        });
    }

    // MARK: - ================== SwitchItemListener ==================

    @Override
    public void switchItemCheckChanged(String key, boolean checked) {
        updateParams.put(key, checked);
    }

    @Override
    public void onBackPressed() {
        if(updateParams.size() > 0) {
            updateAlertSettings();
        } else {
            finish();
        }
    }
}
