package au.com.holcim.holcimapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import au.com.holcim.holcimapp.models.Order;
import au.com.holcim.holcimapp.models.OrderItem;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * Created by Jovan on 14/09/15.
 */
public class OrdersAdapter extends FlexibleAdapter<OrderItem> {

    private Context mContext;

    public OrdersAdapter(Context context) {
        super(new ArrayList<OrderItem>(), context);
        mContext = context;
    }

    public void updateDataset(List<Order> orders) {
        sectionNotes(orders);
    }

    private void sectionNotes(final List<Order> orders) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Collections.sort(orders, new Comparator<Order>() {
                    @Override
                    public int compare(Order order, Order order2) {
                        if(order.etaDate() != null && order2.etaDate() != null) {
                            order.etaDate().compareTo(order2.etaDate());
                        }
                        return 0;
                    }
                });
                Map<String, List<Order>> sectionedOrders = new LinkedHashMap<>();
                for (Order order : orders) {
                    String sectionDate = order.sectionDateFormat();
                    if (!sectionedOrders.containsKey(sectionDate)) {
                        sectionedOrders.put(sectionDate, new ArrayList<Order>());
                    }
                    sectionedOrders.get(sectionDate).add(order);
                }

                Handler mainHandler = new Handler(mContext.getMainLooper());

                final List<OrderItem> newItems = new ArrayList<>();
                for(Object key: (sectionedOrders.keySet().toArray())) {
                    List<Order> sectionOfOrders = sectionedOrders.get(key);
                    OrderHeaderItem header = new OrderHeaderItem(((String) key));
                    for(int i = 0; i < sectionOfOrders.size(); i++) {
                        newItems.add(new OrderItem(header, sectionOfOrders.get(i)));
                    }
                }

                Runnable mainRunnable = new Runnable() {
                    @Override
                    public void run() {
                        OrdersAdapter.this.updateDataSet(newItems);
                    }
                };
                mainHandler.post(mainRunnable);
            }
        });
    }
}


