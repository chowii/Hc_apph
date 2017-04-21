package au.com.holcim.holcimapp.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import au.com.holcim.holcimapp.OrderHeaderItem;
import au.com.holcim.holcimapp.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Jovan on 21/4/17.
 */

public class OrderItem extends AbstractSectionableItem<OrderItem.OrderViewHolder, OrderHeaderItem> {

    protected OrderHeaderItem mHeader;
    public BasicOrder mOrder;

    public OrderItem(OrderHeaderItem header, BasicOrder order) {
        super(header);
        this.mHeader = header;
        this.mOrder = order;
    }

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof OrderItem) {
            OrderItem inItem = (OrderItem) inObject;
            return this.mOrder.id == inItem.mOrder.id;
        }
        return false;
    }

    @Override
    public OrderHeaderItem getHeader() {
        return header;
    }

    @Override
    public void setHeader(OrderHeaderItem header) {
        this.header = header;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.order_list_item;
    }

    @Override
    public OrderViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new OrderViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, OrderViewHolder holder, int position, List payloads) {
        holder.mViewStatusIndicator.setBackgroundColor(mOrder.getStatusColour(holder.mViewStatusIndicator.getResources()));
        holder.mTvTitle.setText(mOrder.deliveryAddress);
        holder.mTvDetail.setText(mOrder.scheduledTimePrettyString());
    }

    public class OrderViewHolder extends FlexibleViewHolder {

        @Bind(R.id.txt_title) TextView mTvTitle;
        @Bind(R.id.txt_detail) TextView mTvDetail;
        @Bind(R.id.view_status_indicator) View mViewStatusIndicator;

        public OrderViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }
    }
}
