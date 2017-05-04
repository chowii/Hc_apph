package au.com.holcim.holcimapp.models;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.format.DateUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Random;

import au.com.holcim.holcimapp.DateHelper;
import au.com.holcim.holcimapp.R;

/**
 * Created by Jovan on 3/5/17.
 */

public class Ticket implements Parcelable {

    public int id = -1;
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


    public int getStatusColour(Resources resources) {
        switch(status != null ? status : "") {
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

    public String getStatusDisplayString() {
        switch(status != null ? status : "") {
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

    public LatLng getLatLng() {
        return new LatLng(latitude != null ? latitude : 0, longitude != null ? longitude : 0);
    }

    @Nullable
    public Date etaDate() {
        return DateHelper.stringToDate(etaString, DateHelper.unixTimeStampFormat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.etaString);
        dest.writeString(this.scheduledNextActiveTicketOnJobTimeString);
        dest.writeString(this.ticketCode);
        dest.writeString(this.ticket_number);
        dest.writeString(this.deliveryPlant);
        dest.writeString(this.truckCode);
        dest.writeString(this.truckId);
        dest.writeInt(this.ticketLoadNumber);
        dest.writeString(this.ticketQuantity);
        dest.writeString(this.status);
        dest.writeString(this.description);
        dest.writeInt(this.orderId);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
    }

    public Ticket() {
    }

    protected Ticket(Parcel in) {
        this.id = in.readInt();
        this.etaString = in.readString();
        this.scheduledNextActiveTicketOnJobTimeString = in.readString();
        this.ticketCode = in.readString();
        this.ticket_number = in.readString();
        this.deliveryPlant = in.readString();
        this.truckCode = in.readString();
        this.truckId = in.readString();
        this.ticketLoadNumber = in.readInt();
        this.ticketQuantity = in.readString();
        this.status = in.readString();
        this.description = in.readString();
        this.orderId = in.readInt();
        this.latitude = (Float) in.readValue(Float.class.getClassLoader());
        this.longitude = (Float) in.readValue(Float.class.getClassLoader());
    }

    public static final Parcelable.Creator<Ticket> CREATOR = new Parcelable.Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel source) {
            return new Ticket(source);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };
}
