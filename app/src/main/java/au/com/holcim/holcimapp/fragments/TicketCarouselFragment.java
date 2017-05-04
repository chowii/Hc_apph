package au.com.holcim.holcimapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import au.com.holcim.holcimapp.DateHelper;
import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.models.Order;
import au.com.holcim.holcimapp.models.Ticket;
import butterknife.Bind;
import butterknife.ButterKnife;

public class TicketCarouselFragment extends Fragment {

    @Bind(R.id.tv_time) TextView mTvTime;
    @Bind(R.id.tv_ticket_number) TextView mTvTicketNumber;
    @Bind(R.id.tv_status) TextView mTvStatus;
    @Bind(R.id.tv_eta) TextView mTvEta;
    @Bind(R.id.tv_ticket_description) TextView mTvDescription;

    public Order mOrder;
    public int mPosition;

    public TicketCarouselFragment() {
        // Required empty public constructor
    }

    public static TicketCarouselFragment newInstance(Order order, int position) {
        TicketCarouselFragment fragment = new TicketCarouselFragment();
        fragment.mOrder = order;
        fragment.mPosition = position;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_carousel, container, false);
        ButterKnife.bind(this, view);
        populateView();
        return view;
    }

    private void populateView() {
        Ticket ticket = mOrder.tickets.get(mPosition);
        mTvTime.setBackgroundColor(ticket.getStatusColour(getResources()));
        if(ticket.etaDate() != null) {
            mTvEta.setText("(" + DateHelper.dateToString(ticket.etaDate(), "hh:mma") + " ETA)");
            mTvTime.setText(DateHelper.getTimeUntilString(ticket.etaDate(), new Date()));
        } else {
            mTvEta.setText("");
            mTvTime.setText("");
        }
        mTvStatus.setText(ticket.getStatusDisplayString());
        mTvTicketNumber.setText("Ticket " + ticket.ticket_number);
        mTvDescription.setText(ticket.description);
    }
}
