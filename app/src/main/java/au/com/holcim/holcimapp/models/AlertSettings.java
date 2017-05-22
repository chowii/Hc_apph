package au.com.holcim.holcimapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Jovan on 12/5/17.
 */

public class AlertSettings implements Parcelable {

    @SerializedName("delivery_arrived")
    public Boolean deliveryArrived;
    @SerializedName("delivery_in_transit")
    public Boolean deliveryInTransit;
    @SerializedName("delivery_cancelled")
    public Boolean deliveryCancelled;
    @SerializedName("delivery_two_hours_away")
    public Boolean deliveryTwoHoursAway;
    @SerializedName("delivery_is_one_hours_away")
    public Boolean deliveryIsOneHourAway;
    @SerializedName("delivery_is_fifteen_minutes_away")
    public Boolean deliveryIsFifteenMinutesAway;
    @SerializedName("delivery_is_ten_minutes_away")
    public Boolean deliveryIsTenMinutesAway;
    @SerializedName("delivery_is_five_minutes_away")
    public Boolean deliveryIsFiveMinutesAway;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.deliveryArrived);
        dest.writeValue(this.deliveryInTransit);
        dest.writeValue(this.deliveryCancelled);
        dest.writeValue(this.deliveryTwoHoursAway);
        dest.writeValue(this.deliveryIsOneHourAway);
        dest.writeValue(this.deliveryIsFifteenMinutesAway);
        dest.writeValue(this.deliveryIsTenMinutesAway);
        dest.writeValue(this.deliveryIsFiveMinutesAway);
    }

    public AlertSettings() {
    }

    protected AlertSettings(Parcel in) {
        this.deliveryArrived = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.deliveryInTransit = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.deliveryCancelled = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.deliveryTwoHoursAway = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.deliveryIsOneHourAway = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.deliveryIsFifteenMinutesAway = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.deliveryIsTenMinutesAway = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.deliveryIsFiveMinutesAway = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<AlertSettings> CREATOR = new Creator<AlertSettings>() {
        @Override
        public AlertSettings createFromParcel(Parcel source) {
            return new AlertSettings(source);
        }

        @Override
        public AlertSettings[] newArray(int size) {
            return new AlertSettings[size];
        }
    };
}