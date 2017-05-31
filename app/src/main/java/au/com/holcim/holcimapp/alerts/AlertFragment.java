package au.com.holcim.holcimapp.alerts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.fragments.PollingFragment;
import au.com.holcim.holcimapp.helpers.NavHelper;
import au.com.holcim.holcimapp.models.Order;
import au.com.holcim.holcimapp.network.ApiClient;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chowii on 30/05/17.
 */

public class AlertFragment extends PollingFragment implements AlertAdapter.AlertItemClickListener{

    private static int mLayoutRes;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    AlertAdapter.AlertItemClickListener listener;

    public static AlertFragment newInstance() {
        AlertFragment fragment = new AlertFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static AlertFragment newInstance(int layoutRes) {
        AlertFragment fragment = new AlertFragment();
        Bundle args = new Bundle();
        mLayoutRes = layoutRes;
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alert, container, false);
        ButterKnife.bind(this, view);
        setupView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this::retrieveAlerts);
    }

    private void setupView() {
        setToolbar("", R.drawable.logo);
        retrieveAlerts();
    }

        List<Alert> mAlerts ;
    public List<Alert> retrieveAlerts() {
        ApiClient.getService().getAlerts().enqueue(new Callback<List<Alert>>() {
            @Override
            public void onResponse(Call<List<Alert>> call, Response<List<Alert>> response) {
                mAlerts = response.body();
                configureView(response.body());
            }

            @Override
            public void onFailure(Call<List<Alert>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                handleError(t);
            }
        });
        return mAlerts;
    }

    private void configureView(List<Alert> alertsList) {
        AlertAdapter adapter = new AlertAdapter(alertsList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showTicket(Alert alert) {
        ApiClient.getService().getOrder(alert.getId()).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                NavHelper.showTicketsMapActivity(getActivity(),
                        response.body(),
                        response.body().userId);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }


    @Override
    public void pollFailed(Throwable throwable) {
        handleError(throwable);
    }
    @Override
    public void pollSuccessful(Object polledObject) {
        super.pollSuccessful(polledObject);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void pollLoading(boolean loading) {
        super.pollLoading(loading);
        swipeRefreshLayout.setRefreshing(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.refresh_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
            swipeRefreshLayout.setRefreshing(true);
            retrieveAlerts();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClicked(boolean isTicket, Alert alert) {
        Log.d("TAG---", "showItem: " + alert.getId());
        if(isTicket) {
            showTicket(alert);
        }else {
            NavHelper.showOrderDetailActivity(getActivity(), alert.getId());
        }
    }
}
