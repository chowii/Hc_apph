package au.com.holcim.holcimapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractHeaderItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Jovan on 21/4/17.
 */


public class OrderHeaderItem extends AbstractHeaderItem<OrderHeaderItem.OrderHeaderViewHolder> {

    public String title;

    public OrderHeaderItem(String title) {
        this.title = title;
        setHidden(false);
        setSelectable(false);
    }

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof OrderHeaderItem) {
            OrderHeaderItem inItem = (OrderHeaderItem) inObject;
            return this.title.equals(inItem.title);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.order_header_item;
    }

    @Override
    public OrderHeaderViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new OrderHeaderViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, OrderHeaderViewHolder holder, int position, List payloads) {
        holder.mTvTitle.setText(title);
    }

    public class OrderHeaderViewHolder extends FlexibleViewHolder {

        @Bind(R.id.txt_title) TextView mTvTitle;

        public OrderHeaderViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }
    }
}

