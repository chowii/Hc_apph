package au.com.holcim.holcimapp.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import au.com.holcim.holcimapp.DateHelper;

/**
 * Created by Jovan on 13/4/17.
 */

public class Order extends BasicOrder {

    @SerializedName("order_date")
    String orderDateString;
    @SerializedName("order_code")
    String orderCode;
    @SerializedName("tickets_delivered")
    String ticketsDelivered;
    @SerializedName("product_line")
    String productLine;
    @SerializedName("order_status")
    String orderStatus;
    @SerializedName("user_id")
    int userId;
    @SerializedName("quote_number")
    String quoteNumber;
    @SerializedName("pricing_plant")
    String pricingPlant;
    @SerializedName("customer_requested_start_time")
    String customerRequestedStartTimeString;
    String product;
    @SerializedName("product_description")
    String productDescription;
    @SerializedName("order_quantity")
    String orderQuantity;
    @SerializedName("expected_number_of_loads")
    int expectedNumberOfLoads;
    @SerializedName("total_product_shipped")
    String totalProductShipped;
    @SerializedName("order_cancellation_reason_code")
    String orderCancellationReasonCode;
    @SerializedName("created_at")
    String createdAtString;
    @SerializedName("updated_at")
    String updatedAtString;
    Float latitude;
    Float longitude;

    public Order() {

    }

    @Nullable
    public Date etaDate() {
        return DateHelper.stringToDate(nextEtaString, DateHelper.unixTimeStampFormat);
    }

    public String sectionDateFormat() {
        Date date = etaDate();
        String formattedDate = DateHelper.ddMMMYYYY.format(date);
        return "" + (date != null ? formattedDate : "Unkown");
    }

}
