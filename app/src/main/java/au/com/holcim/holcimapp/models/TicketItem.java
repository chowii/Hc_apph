package au.com.holcim.holcimapp.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import au.com.holcim.holcimapp.DateHelper;
import au.com.holcim.holcimapp.OrderDetailHeaderItem;
import au.com.holcim.holcimapp.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Jovan on 21/4/17.
 */

public class TicketItem extends AbstractSectionableItem<TicketItem.TicketViewHolder, OrderDetailHeaderItem> {

    protected OrderDetailHeaderItem mHeader;
    public Order mOrder;

    public TicketItem(OrderDetailHeaderItem header, Order order) {
        super(header);
        this.mHeader = header;
        this.mOrder = order;
    }

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof TicketItem) {
            TicketItem inItem = (TicketItem) inObject;
            return this.mOrder.id == inItem.mOrder.id;
        }
        return false;
    }

    @Override
    public OrderDetailHeaderItem getHeader() {
        return header;
    }

    @Override
    public void setHeader(OrderDetailHeaderItem header) {
        this.header = header;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_list_ticket;
    }

    @Override
    public TicketViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new TicketViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, TicketViewHolder holder, int position, List payloads) {
        Ticket ticket = mOrder.tickets.get(position - 1);
        holder.mTvTime.setBackgroundColor(ticket.getStatusColour(holder.mTvStatus.getResources()));
        if(ticket.etaDate() != null) {
            holder.mTvEta.setText("(" + DateHelper.dateToString(ticket.etaDate(), "hh:mma") + " ETA)");
            holder.mTvTime.setText(DateHelper.getTimeUntilString(ticket.etaDate(), new Date()));
        } else {
            holder.mTvEta.setText("");
            holder.mTvTime.setText("");
        }
        holder.mTvStatus.setText(ticket.getStatusDisplayString());
        holder.mTvTicketNumber.setText("Ticket " + ticket.ticket_number);
        holder.mTvDescription.setText(ticket.description);
    }

    public class TicketViewHolder extends FlexibleViewHolder {

        @Bind(R.id.tv_time) TextView mTvTime;
        @Bind(R.id.tv_ticket_number) TextView mTvTicketNumber;
        @Bind(R.id.tv_status) TextView mTvStatus;
        @Bind(R.id.tv_eta) TextView mTvEta;
        @Bind(R.id.tv_ticket_description) TextView mTvDescription;

        public TicketViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }
    }
}
