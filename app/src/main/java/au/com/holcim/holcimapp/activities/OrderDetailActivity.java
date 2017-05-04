package au.com.holcim.holcimapp.activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import au.com.holcim.holcimapp.Constants;
import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.TicketsAdapter;
import au.com.holcim.holcimapp.models.Order;
import au.com.holcim.holcimapp.network.ApiClient;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends BaseActivity {

    int orderId;
    @Bind(R.id.rv_tickets) RecyclerView mRvTickets;
    @Bind(R.id.swiperefresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.cl_container) CoordinatorLayout mClContainer;
    @Bind(R.id.txt_empty_dataset) TextView mTxtEmptyDataset;
    TicketsAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
         ButterKnife.bind(this);
        setupSwipeToRefresh();
        setupAdapter();
        setToolbar("", true);
        orderId = getIntent().getIntExtra(Constants.Extras.ORDER_ID, 0);
        retrieveOrder();
    }

    private void setupAdapter() {
        mAdapter = new TicketsAdapter(this);
        mAdapter.addListener(this)
                .setAnimationOnScrolling(true)
                .setAnimationOnReverseScrolling(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRvTickets.setLayoutManager(mLayoutManager);
        mRvTickets.setAdapter(mAdapter);
        mAdapter.setDisplayHeadersAtStartUp(true)
                .areHeadersSticky();
        mAdapter.setDisplayHeadersAtStartUp(true);
    }

    private void setupSwipeToRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveOrder();
            }
        });
    }

    private void retrieveOrder() {
        mSwipeRefreshLayout.setRefreshing(true);
        ApiClient.getService().getOrder(orderId).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                OrderDetailActivity.this.mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.updateDataset(response.body());
                OrderDetailActivity.this.mTxtEmptyDataset.setVisibility(response.body().tickets.size() == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                OrderDetailActivity.this.mSwipeRefreshLayout.setRefreshing(false);
                handleError(t, true, OrderDetailActivity.this.mClContainer);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
            retrieveOrder();
        } if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
