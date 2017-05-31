package au.com.holcim.holcimapp.alerts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chowii on 30/05/17.
 */

class AlertAttachment implements AlertAttachable {

    @SerializedName("id")
    private int primaryId;

    @SerializedName("order_id")
    private int secondaryId;

    @SerializedName("ticket_number")
    private int ticketNumber;


    @Override
    public int getPrimaryId() {
        return primaryId;
    }

    @Override
    public int getSecondaryId() {
        return secondaryId;
    }

    @Override
    public int getTicketNumber() {
        return ticketNumber;
    }

    @Override
    public boolean isTicket() { return secondaryId != 0; }
}


    interface AlertAttachable{
        int getPrimaryId();
        int getSecondaryId();
        int getTicketNumber();
        boolean isTicket();
    }
