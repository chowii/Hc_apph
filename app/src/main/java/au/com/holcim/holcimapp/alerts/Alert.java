package au.com.holcim.holcimapp.alerts;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

/**
 * Created by chowii on 26/05/17.
 */

public class Alert implements Alertable {

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("alert_type")
    private String alertType;

    @SerializedName("message")
    private String additionalMessage;

    @SerializedName("address")
    private String address;

    @SerializedName("active?")
    private String active;

    @SerializedName("alertable")
    private AlertAttachment alertAttachment;

    private int id;
    private static Alert uniqueAlertInstance;

    Alert(){}

    public static Alert newInstance() {
        if(uniqueAlertInstance == null) uniqueAlertInstance = new Alert();
        return uniqueAlertInstance;
    }

    public Alert(String deliveryAddress) { this.address = deliveryAddress; }

    public int getId(){ return id; }

    @NotNull
    @Override
    public String getCreatedAt() { return createdAt; }

    @Nullable
    @Override
    public String getAdditionalMessage() { return additionalMessage; }

    @Nullable
    @Override
    public String getAddress() { return address; }

    @NotNull
    @Override
    public String getAlertType() { return alertType; }

    @NotNull
    @Override
    public AlertAttachment getAlertAttachment() { return alertAttachment; }
}
