package au.com.holcim.holcimapp.models;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import au.com.holcim.holcimapp.DateHelper;
import au.com.holcim.holcimapp.OrderDetailHeaderItem;
import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.SettingsHeaderItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractHeaderItem;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Jovan on 21/4/17.
 */

public class SettingsItem extends AbstractSectionableItem<SettingsItem.SettingsViewHolder, SettingsHeaderItem> {

    String mTitle;
    public Intent mIntent;

    public SettingsItem(SettingsHeaderItem header, String title, Intent intent) {
        super(header);
        this.mTitle = title;
        this.mIntent = intent;
    }

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof SettingsItem) {
            SettingsItem inItem = (SettingsItem) inObject;
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
        return R.layout.settings_list_item;
    }

    @Override
    public SettingsViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new SettingsViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, SettingsViewHolder holder, int position, List payloads) {
        holder.mTvTitle.setText(mTitle);
    }

    public class SettingsViewHolder extends FlexibleViewHolder {

        @Bind(R.id.tv_title) TextView mTvTitle;

        public SettingsViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }
    }
}
