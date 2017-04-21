package au.com.holcim.holcimapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import au.com.holcim.holcimapp.models.BasicOrder;
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

    public void updateDataset(List<BasicOrder> orders) {
        sectionNotes(orders);
    }

    private void sectionNotes(final List<BasicOrder> orders) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Collections.sort(orders, new Comparator<BasicOrder>() {
                    @Override
                    public int compare(BasicOrder order, BasicOrder order2) {
                        if(order.etaDate() != null && order2.etaDate() != null) {
                            return order.etaDate().compareTo(order2.etaDate());
                        }
                        return 0;
                    }
                });
                Map<String, List<BasicOrder>> sectionedOrders = new LinkedHashMap<>();
                for (BasicOrder order : orders) {
                    String sectionTitle;
                    if(order.etaDate() == null) {
                        sectionTitle = "UNKNOWN";
                    } else if(DateUtils.isToday(order.etaDate().getTime())) {
                        sectionTitle = "TODAY";
                    } else {
                        sectionTitle = "UPCOMING (Next 14 days)";
                    }
                    if (!sectionedOrders.containsKey(sectionTitle)) {
                        sectionedOrders.put(sectionTitle, new ArrayList<>());
                    }
                    sectionedOrders.get(sectionTitle).add(order);
                }

                Handler mainHandler = new Handler(mContext.getMainLooper());

                final List<OrderItem> newItems = new ArrayList<>();
                for(Object key: (sectionedOrders.keySet().toArray())) {
                    List<BasicOrder> sectionOfOrders = sectionedOrders.get(key);
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


