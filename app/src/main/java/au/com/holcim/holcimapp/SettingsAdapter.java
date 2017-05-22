package au.com.holcim.holcimapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import au.com.holcim.holcimapp.helpers.NavHelper;
import au.com.holcim.holcimapp.models.BasicOrder;
import au.com.holcim.holcimapp.models.OrderItem;
import au.com.holcim.holcimapp.models.RedRoundedButtonItem;
import au.com.holcim.holcimapp.models.Settings;
import au.com.holcim.holcimapp.models.SettingsItem;
import au.com.holcim.holcimapp.models.SettingsSwitchItem;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;

/**
 * Created by Jovan on 14/09/15.
 */

public class SettingsAdapter extends FlexibleAdapter<AbstractSectionableItem> {

    private Context mContext;
    private Settings mSettings;
    private SwitchItemListener mSwitchListener;
    private View.OnClickListener mOnClickListener;

    public SettingsAdapter(Context context, View.OnClickListener onClickListener, SwitchItemListener switchListener, Settings settings) {
        super(new ArrayList<>(), context);
        mContext = context;
        mSettings = settings;
        mSwitchListener = switchListener;
        mOnClickListener = onClickListener;
        addItems(0, createSettingsItems());
    }

    private List<AbstractSectionableItem> createSettingsItems() {
        List<AbstractSectionableItem> items = new ArrayList<>();
        SettingsHeaderItem settingsHeader = new SettingsHeaderItem("SETTINGS");
        items.add(new SettingsSwitchItem(settingsHeader, "Push Notifications", mSettings.receivePushNotifications, "receive_push_notifications", mSwitchListener));
        items.add(new SettingsItem(settingsHeader, "Alerts", NavHelper.alertSettingsActivityIntent(mContext, mSettings)));
        SettingsHeaderItem helpHeader = new SettingsHeaderItem("HELP");
        items.add(new SettingsItem(helpHeader, "How To Order", NavHelper.webViewActivityIntent(mContext, Constants.Urls.howTo)));
        items.add(new SettingsItem(helpHeader, "FAQ", NavHelper.webViewActivityIntent(mContext, Constants.Urls.faq)));
        items.add(new SettingsItem(helpHeader, "App Tour", NavHelper.webViewActivityIntent(mContext, Constants.Urls.faq)));
        items.add(new RedRoundedButtonItem(helpHeader, "Logout", mOnClickListener));
        return items;
    }

    public void updatePushNotificationsEnabled(boolean enabled) {
        ((SettingsSwitchItem) getItem(1)).setChecked(enabled);
        notifyDataSetChanged();
    }
}


