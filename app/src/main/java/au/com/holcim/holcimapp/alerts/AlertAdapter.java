package au.com.holcim.holcimapp.alerts;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.helpers.NavHelper;
import butterknife.ButterKnife;

/**
 * Created by chowii on 29/05/17.
 */

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder> {


    private final AlertItemClickListener listener;
    private List<Alert> mAlertsList;
    private int mPosition;

    AlertAdapter(List<Alert> alerts, AlertItemClickListener alertItemClickListener){
        mAlertsList = alerts;
        listener = alertItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alert alerts = mAlertsList.get(position);

        holder.mAdditionalMessage.setText(alerts.getAdditionalMessage());
        holder.mStatusTextView.setText(alerts.getAddress());
        //getDuration(alerts.getCreatedAt());

       // holder.mTimeTextView.setText(getDuration(alerts.getCreatedAt()));

        holder.mTicketNumber.setText(
                getTicketNumber(
                        alerts.getAlertAttachment().getTicketNumber()));

        holder.mRefreshLayout.setOnClickListener(v -> {
            listener.onItemClicked(
                    getAlertsAttachment(position).isTicket(),
                    getAlerts().get(position));

            Log.d("TAG", "onBindViewHolder: " + getAlertsAttachment(position).isTicket());
        });
    }

    private String getTicketNumber(int ticketNumber) {
        return ticketNumber == 0 ?  "" : ticketNumber + "";
    }

    private String getDuration(String createdAt) {
        String time = createdAt.substring(createdAt.indexOf("T") + 1);
        Log.d("TAG---", "getDuration: " + time);
        SimpleDateFormat dateString = new SimpleDateFormat("HH:mm:ss");
        try {
            dateString.parse(createdAt);
            Date date = new Date();
            String output = dateString.format(date);
            Log.d("TAG---", "getDuration: \n" + output);

        } catch (ParseException e) {
            Log.d("TAG---", "getDuration: \n" + e.getMessage());
        }
        return time;
    }

    @Override
    public int getItemCount() { return mAlertsList.size(); }

    @Override
    public int getItemViewType(int position){
        mPosition = position;
        return R.layout.item_list_ticket;
    }

    public int getPosition() {
        return mPosition;
    }

    public List<Alert> getAlerts() {
        return mAlertsList;
    }

    public AlertAttachment getAlertsAttachment(int position){
        return mAlertsList.get(position).getAlertAttachment();
    }

    interface AlertItemClickListener{
        void onItemClicked(boolean isTicket, Alert alert);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTimeTextView;
        TextView mStatusTextView;
        TextView mAddressTextView;
        TextView mAdditionalMessage;
        TextView mTicketNumber;
        TextView mEta;
        RelativeLayout mRefreshLayout;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            mTimeTextView = (TextView) itemView.findViewById(R.id.tv_time);
            mStatusTextView = (TextView) itemView.findViewById(R.id.tv_status);
            mAdditionalMessage = (TextView) itemView.findViewById(R.id.tv_ticket_description);
            mTicketNumber = (TextView) itemView.findViewById(R.id.tv_ticket_number);
            mEta = (TextView) itemView.findViewById(R.id.tv_eta);
            mRefreshLayout = (RelativeLayout) itemView.findViewById(R.id.item_layout);
        }
    }
}
