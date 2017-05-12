package au.com.holcim.holcimapp.models;

import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.SettingsHeaderItem;
import au.com.holcim.holcimapp.SwitchItemListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Jovan on 21/4/17.
 */

public class SettingsSwitchItem extends AbstractSectionableItem<SettingsSwitchItem.SettingsSwitchViewHolder, SettingsHeaderItem> {

    String mTitle = "";
    String mKey = "";
    SwitchItemListener mListener;
    boolean mChecked = false;

    private boolean callListenerOnChange = true;

    public SettingsSwitchItem(SettingsHeaderItem header, String title, boolean enabled, String key, SwitchItemListener listener) {
        super(header);
        this.mTitle = title;
        this.mKey = key;
        this.mChecked = enabled;
        this.mListener = listener;
    }

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof SettingsSwitchItem) {
            SettingsSwitchItem inItem = (SettingsSwitchItem) inObject;
            return this.mTitle == inItem.mTitle;
        }
        return false;
    }

    @Override
    public SettingsHeaderItem getHeader() {
        return header;
    }

    @Override
    public void setHeader(SettingsHeaderItem header) {
        this.header = header;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.settings_list_switch_item;
    }

    @Override
    public SettingsSwitchViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new SettingsSwitchViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, SettingsSwitchViewHolder holder, int position, List payloads) {
        holder.mTvTitle.setText(mTitle);
        callListenerOnChange = false;
        holder.mSwEnabled.setChecked(mChecked);
    }

    public void setChecked(boolean checked) {
        callListenerOnChange = false;
        this.mChecked = checked;
    }

    public class SettingsSwitchViewHolder extends FlexibleViewHolder {

        @Bind(R.id.tv_title) TextView mTvTitle;
        @Bind(R.id.sw_enabled) SwitchCompat mSwEnabled;

        public SettingsSwitchViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }

        @OnCheckedChanged(R.id.sw_enabled)
        void onChecked(boolean checked) {
            if(callListenerOnChange) {
                mListener.switchItemCheckChanged(mKey, checked);
            }
            callListenerOnChange = true;
        }
    }
}
