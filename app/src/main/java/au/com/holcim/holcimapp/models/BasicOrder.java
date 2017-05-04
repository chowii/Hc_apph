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
 * Created by Jovan on 21/4/17.
 */

public class BasicOrder {

    public int id;
    public String status;
    @SerializedName("delivery_address")
    public String deliveryAddress;
    @SerializedName("next_eta")
    public String nextEtaString;

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
        if(etaDate() != null) {
            return "Next Delivery approx " + DateUtils.getRelativeTimeSpanString(etaDate().getTime()).toString().replace("In ", "");
        }
        return "";
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
