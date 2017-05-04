package au.com.holcim.holcimapp.models;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.format.DateUtils;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import au.com.holcim.holcimapp.DateHelper;
import au.com.holcim.holcimapp.R;

/**
 * Created by Jovan on 3/5/17.
 */

public class Ticket {

    public int id;
    @SerializedName("eta")
    public String etaString;
    @SerializedName("scheduled_next_active_ticket_on_job_time")
    public String scheduledNextActiveTicketOnJobTimeString;
    @SerializedName("ticket_code")
    public String ticketCode;
    @SerializedName("ticket_number")
    public String ticket_number;
    @SerializedName("delivery_plant")
    public String deliveryPlant;
    @SerializedName("truck_code")
    public String truckCode;
    @SerializedName("truck_id")
    public String truckId;
    @SerializedName("ticket_load_number")
    public int ticketLoadNumber;
    @SerializedName("ticket_quantity")
    public String ticketQuantity;
    public String status;
    @SerializedName("product_description")
    public String description;
    @SerializedName("oirder_id")
    public int orderId;
    public Float latitude;
    public Float longitude;


    int getStatusColour(Resources resources) {
        switch(status) {
            case "delivered":
                return ResourcesCompat.getColor(resources, R.color.status_delivered_blue, null);
            case "in_transit":
                return ResourcesCompat.getColor(resources, R.color.status_transit_green, null);
            case "cancelled":
                return ResourcesCompat.getColor(resources, R.color.status_cancelled_grey, null);
            case "pending":
                return ResourcesCompat.getColor(resources, R.color.status_pending_orange, null);
            default:
                return ResourcesCompat.getColor(resources, R.color.black, null);
        }
    }

    String getStatusDisplayString() {
        switch(status) {
            case "delivered":
                return "Delivered";
            case "in_transit":
                return "In Transit";
            case "cancelled":
                return "Cancelled";
            case "pending":
                return "Pending";
            default:
                return "";
        }
    }

    @Nullable
    public Date etaDate() {
        return DateHelper.stringToDate(etaString, DateHelper.unixTimeStampFormat);
    }
}
