package au.com.holcim.holcimapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.models.BasicOrder;
import au.com.holcim.holcimapp.network.ApiClient;
import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersFragment extends Fragment {

    @Bind(R.id.orders_recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.swiperefresh) SwipeRefreshLayout mSwipeRefreshLayout;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrieveOrders();
    }

    private void retrieveOrders() {
        ApiClient.getService().getOrders().enqueue(new Callback<List<BasicOrder>>() {
            @Override
            public void onResponse(Call<List<BasicOrder>> call, Response<List<BasicOrder>> response) {

            }

            @Override
            public void onFailure(Call<List<BasicOrder>> call, Throwable t) {

            }
        });
    }
}
