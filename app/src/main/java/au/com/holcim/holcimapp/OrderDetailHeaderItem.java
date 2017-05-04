package au.com.holcim.holcimapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import au.com.holcim.holcimapp.models.Order;
import butterknife.Bind;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractHeaderItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Jovan on 21/4/17.
 */


public class OrderDetailHeaderItem extends AbstractHeaderItem<OrderDetailHeaderItem.OrderDetailHeaderViewHolder> {

    public Order order;

    public OrderDetailHeaderItem(Order order) {
        this.order = order;
        setHidden(false);
        setSelectable(false);
    }

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof OrderDetailHeaderItem) {
            OrderDetailHeaderItem inItem = (OrderDetailHeaderItem) inObject;
            return this.order.equals(inItem);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.order_detail_header_item;
    }

    @Override
    public OrderDetailHeaderViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new OrderDetailHeaderViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, OrderDetailHeaderViewHolder holder, int position, List payloads) {
        holder.mTvAddress.setText(order.deliveryAddress);
        holder.mPbvBalance.setContent(order.getTotalProductShippedFloat(), order.getOrderQuantityFloat(), order.getBalanceProgressString());
        holder.mPbvTicketsDelivered.setContent(order.getTicketsDeliveredFloat(), order.getExpectedNumberOfLoadsFloat(), order.getTicketsDeliveredProgressString());
    }

    public class OrderDetailHeaderViewHolder extends FlexibleViewHolder {

        @Bind(R.id.tv_address) TextView mTvAddress;
        @Bind(R.id.pbv_balance) ProgressBarView mPbvBalance;
        @Bind(R.id.pbv_tickets_delivered) ProgressBarView mPbvTicketsDelivered;

        public OrderDetailHeaderViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }
    }
}

