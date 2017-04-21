package au.com.holcim.holcimapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import au.com.holcim.holcimapp.OrdersAdapter;
import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.models.BasicOrder;
import au.com.holcim.holcimapp.network.ApiClient;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends BaseFragment {

    @Bind(R.id.orders_recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.swiperefresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.txt_empty_dataset) TextView mTxtEmptyDataset;
    @Bind(R.id.cl_container) CoordinatorLayout mClContainer;

    OrdersAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    public OrdersFragment() {
        // Required empty public constructor
    }

    public static OrdersFragment newInstance() {
        OrdersFragment fragment = new OrdersFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, view);
        setupAdapter();
        setupSwipeToRefresh();
        retrieveOrders();
        setToolbar("", R.drawable.logo);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.orders_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
            retrieveOrders();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupAdapter() {
        mAdapter = new OrdersAdapter(getActivity());
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

    private void setupSwipeToRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveOrders();
            }
        });
    }

    private void retrieveOrders() {
        mSwipeRefreshLayout.setRefreshing(true);
        ApiClient.getService().getOrders().enqueue(new Callback<List<BasicOrder>>() {
            @Override
            public void onResponse(Call<List<BasicOrder>> call, Response<List<BasicOrder>> response) {
                OrdersFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.updateDataset(response.body());
                OrdersFragment.this.mTxtEmptyDataset.setVisibility(response.body().size() == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFailure(Call<List<BasicOrder>> call, Throwable t) {
                OrdersFragment.this.mSwipeRefreshLayout.setRefreshing(false);
                handleError(t, true, mClContainer);
            }
        });
    }
}
