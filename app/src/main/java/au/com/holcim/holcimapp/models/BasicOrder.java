package au.com.holcim.holcimapp.models;

import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;

import com.google.gson.annotations.SerializedName;

import au.com.holcim.holcimapp.R;

/**
 * Created by Jovan on 21/4/17.
 */

public class BasicOrder {

    int id;
    String status;
    @SerializedName("delivery_address")
    String deliveryAddress;
    @SerializedName("next_eta")
    String nextEtaString;

    int getStatusColour(Resources resources) {
        //TODO: Confirm correct colours
        switch(status) {
            case "arrived":
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

    String scheduledTimePrettyString() {
        //TODO: Implement correctly
        return "";
    }

}
