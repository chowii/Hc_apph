package au.com.holcim.holcimapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import au.com.holcim.holcimapp.DateHelper;

/**
 * Created by Jovan on 13/4/17.
 */

public class Order extends BasicOrder implements Parcelable {

    @SerializedName("order_date")
    public String orderDateString;
    @SerializedName("order_code")
    public String orderCode;
    @SerializedName("tickets_delivered")
    public String ticketsDelivered;
    @SerializedName("product_line")
    public String productLine;
    @SerializedName("order_status")
    public String orderStatus;
    @SerializedName("user_id")
    public int userId;
    @SerializedName("quote_number")
    public String quoteNumber;
    @SerializedName("pricing_plant")
    public String pricingPlant;
    @SerializedName("customer_requested_start_time")
    public String customerRequestedStartTimeString;
    public String product;
    @SerializedName("product_description")
    public String productDescription;
    @SerializedName("ordered_qty")
    public String orderQuantity;
    @SerializedName("expected_number_of_loads")
    public int expectedNumberOfLoads;
    @SerializedName("total_product_shipped")
    public String totalProductShipped;
    @SerializedName("order_cancellation_reason_code")
    public String orderCancellationReasonCode;
    @SerializedName("created_at")
    public String createdAtString;
    @SerializedName("updated_at")
    public String updatedAtString;
    public Float latitude;
    public Float longitude;
    public List<Ticket> tickets;

    public LatLng getLatLng() {
        return new LatLng(latitude != null ? latitude : 0, longitude != null ? longitude : 0);
    }

    public Order() {

    }

    public Float getTotalProductShippedFloat() {
        return Float.valueOf(totalProductShipped) != null ? Float.valueOf(totalProductShipped) : 0;
    }

    public Float getOrderQuantityFloat() {
        return Float.valueOf(orderQuantity) != null ? Float.valueOf(orderQuantity) : 0;
    }

    public Float getExpectedNumberOfLoadsFloat
            () {
        return Float.valueOf(expectedNumberOfLoads) != null ? Float.valueOf(expectedNumberOfLoads) : 0;
    }

    public Float getTicketsDeliveredFloat() {
        return Float.valueOf(ticketsDelivered) != null ? Float.valueOf(ticketsDelivered) : 0;
    }

    public String getBalanceProgressString() {
        return getTotalProductShippedFloat() + "㎥ / " + getOrderQuantityFloat() + "㎥";
    }

    public String getTicketsDeliveredProgressString() {
        return getTicketsDeliveredFloat() + " / " + getExpectedNumberOfLoadsFloat();
    }

    public int getTicketIndexWithId(int id) {
        for(int i = 0; i < tickets.size(); i++) {
            if(tickets.get(i).id == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.orderDateString);
        dest.writeString(this.orderCode);
        dest.writeString(this.ticketsDelivered);
        dest.writeString(this.productLine);
        dest.writeString(this.orderStatus);
        dest.writeInt(this.userId);
        dest.writeString(this.quoteNumber);
        dest.writeString(this.pricingPlant);
        dest.writeString(this.customerRequestedStartTimeString);
        dest.writeString(this.product);
        dest.writeString(this.productDescription);
        dest.writeString(this.orderQuantity);
        dest.writeInt(this.expectedNumberOfLoads);
        dest.writeString(this.totalProductShipped);
        dest.writeString(this.orderCancellationReasonCode);
        dest.writeString(this.createdAtString);
        dest.writeString(this.updatedAtString);
        dest.writeValue(this.latitude);
        dest.writeValue(this.longitude);
        dest.writeTypedList(this.tickets);
        dest.writeInt(this.id);
        dest.writeString(this.status);
        dest.writeString(this.deliveryAddress);
        dest.writeString(this.nextEtaString);
    }

    protected Order(Parcel in) {
        super(in);
        this.orderDateString = in.readString();
        this.orderCode = in.readString();
        this.ticketsDelivered = in.readString();
        this.productLine = in.readString();
        this.orderStatus = in.readString();
        this.userId = in.readInt();
        this.quoteNumber = in.readString();
        this.pricingPlant = in.readString();
        this.customerRequestedStartTimeString = in.readString();
        this.product = in.readString();
        this.productDescription = in.readString();
        this.orderQuantity = in.readString();
        this.expectedNumberOfLoads = in.readInt();
        this.totalProductShipped = in.readString();
        this.orderCancellationReasonCode = in.readString();
        this.createdAtString = in.readString();
        this.updatedAtString = in.readString();
        this.latitude = (Float) in.readValue(Float.class.getClassLoader());
        this.longitude = (Float) in.readValue(Float.class.getClassLoader());
        this.tickets = in.createTypedArrayList(Ticket.CREATOR);
        this.id = in.readInt();
        this.status = in.readString();
        this.deliveryAddress = in.readString();
        this.nextEtaString = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
