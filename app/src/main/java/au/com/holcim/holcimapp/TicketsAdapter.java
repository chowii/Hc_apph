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
import au.com.holcim.holcimapp.models.Ticket;
import au.com.holcim.holcimapp.models.TicketItem;
import eu.davidea.flexibleadapter.FlexibleAdapter;

/**
 * Created by Jovan on 14/09/15.
 */
public class TicketsAdapter extends FlexibleAdapter<TicketItem> {

    private Context mContext;

    public TicketsAdapter(Context context) {
        super(new ArrayList<TicketItem>(), context);
        mContext = context;
    }

    public void updateDataset(Order order) {
        final List<TicketItem> newItems = new ArrayList<>();
        OrderDetailHeaderItem header = new OrderDetailHeaderItem(order);
        for(Ticket ticket:order.tickets) {
            newItems.add(new TicketItem(header, order));
        }
        Handler mainHandler = new Handler(mContext.getMainLooper());
        Runnable mainRunnable = new Runnable() {
            @Override
            public void run() {
                TicketsAdapter.this.updateDataSet(newItems);
            }
        };
        mainHandler.post(mainRunnable);
    }
}


