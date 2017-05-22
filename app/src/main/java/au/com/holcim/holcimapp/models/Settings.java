package au.com.holcim.holcimapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Jovan on 12/5/17.
 */

public class Settings implements Parcelable {

    @SerializedName("receive_push_notifications")
    public boolean receivePushNotifications;
    @SerializedName("alert_settings")
    public AlertSettings alertSettings;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.receivePushNotifications ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.alertSettings, flags);
    }

    public Settings() {
    }

    protected Settings(Parcel in) {
        this.receivePushNotifications = in.readByte() != 0;
        this.alertSettings = in.readParcelable(AlertSettings.class.getClassLoader());
    }

    public static final Parcelable.Creator<Settings> CREATOR = new Parcelable.Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel source) {
            return new Settings(source);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };
}
