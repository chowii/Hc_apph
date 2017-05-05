package au.com.holcim.holcimapp.activities;

import android.os.Handler;
import android.util.Log;

import java.util.List;

import au.com.holcim.holcimapp.interfaces.Poller;
import au.com.holcim.holcimapp.models.Order;
import au.com.holcim.holcimapp.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jovan on 5/5/17.
 */


public class PollingActivity<T> extends BaseActivity implements Poller<T> {

    private String TAG = "PollingActivity";

    private T mObjectToPoll;
    private int pollingRate = 8000;
    private final Handler poller = new Handler();
    private Call<T> call;

    // MARK: - ================== LifeCycle ==================

    @Override
    protected void onResume() {
        super.onResume();
        startPolling();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPolling();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPolling();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopPolling();
    }

    // MARK: - ================== General Methods ==================

    protected void setObjectToPoll(T objectToPoll) {
        mObjectToPoll = objectToPoll;
        startPolling();
    }

    // MARK: - ================== Polling Related ==================

    public void forceRefresh() {
        Log.d(TAG, "Forcing poll refresh.");
        stopPolling();
        apiCall();
    }

    private void startPolling() {
        Log.d(TAG, "Checking if can poll.");
        if(mObjectToPoll != null) {
            Log.d(TAG, "Starting poller.");
            poller.postDelayed(()
                    -> apiCall(), pollingRate);
        }
        Log.d(TAG, "No object to poll.");
    }

    private void stopPolling() {
        Log.d(TAG, "Stopping poller.");
        call.cancel();
        poller.removeCallbacksAndMessages(null);
    }

    private void apiCall() {
        if(mObjectToPoll instanceof List) {
            call = ((Call<T>) ApiClient.getService().getOrders());
        } else if(mObjectToPoll instanceof Order) {
            call = ((Call<T>) ApiClient.getService().getOrder(((Order) mObjectToPoll).id));
        }
        if(call != null) {
            pollLoading(true);
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, Response<T> response) {
                    PollingActivity.this.pollLoading(false);
                    PollingActivity.this.mObjectToPoll = response.body();
                    PollingActivity.this.pollSuccessful(response.body());
                    PollingActivity.this.startPolling();
                }

                @Override
                public void onFailure(Call<T> call, Throwable t) {
                    PollingActivity.this.pollLoading(false);
                    PollingActivity.this.pollFailed(t);
                }
            });
        } else {
            Log.d(TAG, "Polling of this object is not supported.");
        }
    }

    // MARK: - ================== Poller interface ==================

    @Override
    public void pollLoading(boolean loading) {}

    @Override
    public void pollSuccessful(T polledObject) {}

    @Override
    public void pollFailed(Throwable throwable) {}
}
