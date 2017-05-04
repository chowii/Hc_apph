package au.com.holcim.holcimapp.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import au.com.holcim.holcimapp.DateHelper;

/**
 * Created by Jovan on 13/4/17.
 */

public class Order extends BasicOrder {

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
}
