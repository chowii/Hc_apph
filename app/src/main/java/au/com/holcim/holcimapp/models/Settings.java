package au.com.holcim.holcimapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Jovan on 12/5/17.
 */

public class Settings {

    @SerializedName("receive_push_notifications")
    public boolean receivePushNotifications;
    @SerializedName("alert_settings")
    public AlertSettings alertSettings;


    public class AlertSettings {

        @SerializedName("delivery_arrived")
        Boolean deliveryArrived;
        @SerializedName("delivery_in_transit")
        Boolean deliveryInTransit;
        @SerializedName("delivery_cancelled")
        Boolean deliveryCancelled;
        @SerializedName("delivery_two_hours_away")
        Boolean deliveryTwoHoursAway;
        @SerializedName("delivery_is_one_hours_away")
        Boolean deliveryIsOneHourAway;
        @SerializedName("delivery_is_fifteen_minutes_away")
        Boolean deliveryIsFifteenMinutesAway;
        @SerializedName("delivery_is_ten_minutes_away")
        Boolean deliveryIsTenMinutesAway;
        @SerializedName("delivery_is_five_minutes_away")
        Boolean deliveryIsFiveMinutesAway;

        public SortedMap<String, Boolean> getDisplayHash() {
            SortedMap<String, Boolean> displayHash = new TreeMap<>();
            displayHash.put("Delivery Arrived", deliveryArrived);
            displayHash.put("Delivery in Transit", deliveryInTransit);
            displayHash.put("Delivery Cancelled", deliveryCancelled);
            displayHash.put("Delivery is 2 Hours Away", deliveryTwoHoursAway);
            displayHash.put("Delivery is 1 Hour Away", deliveryIsOneHourAway);
            displayHash.put("Delivery is 15 Minutes Away", deliveryIsFifteenMinutesAway);
            displayHash.put("Delivery is 10 Minutes Away", deliveryIsTenMinutesAway);
            displayHash.put("Delivery is 5 Minutes Away", deliveryIsFiveMinutesAway);
            return displayHash;
        }
    }

}
