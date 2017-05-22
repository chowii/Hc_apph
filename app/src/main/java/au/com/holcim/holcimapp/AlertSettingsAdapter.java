package au.com.holcim.holcimapp;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import au.com.holcim.holcimapp.helpers.NavHelper;
import au.com.holcim.holcimapp.models.RedRoundedButtonItem;
import au.com.holcim.holcimapp.models.Settings;
import au.com.holcim.holcimapp.models.SettingsItem;
import au.com.holcim.holcimapp.models.SettingsSwitchItem;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;

/**
 * Created by Jovan on 14/09/15.
 */

public class AlertSettingsAdapter extends FlexibleAdapter<AbstractSectionableItem> {

    private Context mContext;
    private SwitchItemListener mSwitchListener;
    private Settings mSettings;

    public AlertSettingsAdapter(Context context, SwitchItemListener switchListener, Settings settings) {
        super(new ArrayList<>(), context);
        mContext = context;
        mSwitchListener = switchListener;
        mSettings = settings;
        addItems(0, createSettingsItems());
    }

    private List<AbstractSectionableItem> createSettingsItems() {
        List<AbstractSectionableItem> items = new ArrayList<>();
        SettingsHeaderItem settingsHeader = new SettingsHeaderItem("ALERT SETTINGS");
        if(mSettings != null && mSettings.alertSettings != null) {
            items.add(new SettingsSwitchItem(settingsHeader, "Delivery Arrived", mSettings.alertSettings.deliveryArrived, "delivery_arrived", mSwitchListener));
            items.add(new SettingsSwitchItem(settingsHeader, "Delivery in Transit", mSettings.alertSettings.deliveryInTransit, "delivery_in_transit", mSwitchListener));
            items.add(new SettingsSwitchItem(settingsHeader, "Delivery Cancelled", mSettings.alertSettings.deliveryCancelled, "delivery_cancelled", mSwitchListener));
            items.add(new SettingsSwitchItem(settingsHeader, "Delivery is 2 Hours Away", mSettings.alertSettings.deliveryTwoHoursAway, "delivery_two_hours_away", mSwitchListener));
            items.add(new SettingsSwitchItem(settingsHeader, "Delivery is 1 Hour Away", mSettings.alertSettings.deliveryIsOneHourAway, "delivery_is_one_hours_away", mSwitchListener));
            items.add(new SettingsSwitchItem(settingsHeader, "Delivery is 15 Minutes Away", mSettings.alertSettings.deliveryIsFifteenMinutesAway, "delivery_is_fifteen_minutes_away", mSwitchListener));
            items.add(new SettingsSwitchItem(settingsHeader, "Delivery is 10 Minutes Away", mSettings.alertSettings.deliveryIsTenMinutesAway, "delivery_is_ten_minutes_away", mSwitchListener));
            items.add(new SettingsSwitchItem(settingsHeader, "Delivery is 5 Minutes Away", mSettings.alertSettings.deliveryIsFiveMinutesAway, "delivery_is_five_minutes_away", mSwitchListener));
        }
        return items;
    }
}


