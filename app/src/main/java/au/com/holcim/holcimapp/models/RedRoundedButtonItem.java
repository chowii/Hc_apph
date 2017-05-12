package au.com.holcim.holcimapp.models;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.SettingsHeaderItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractSectionableItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Jovan on 21/4/17.
 */

public class RedRoundedButtonItem extends AbstractSectionableItem<RedRoundedButtonItem.SettingsViewHolder, SettingsHeaderItem> {

    String mTitle;
    View.OnClickListener mListener;

    public RedRoundedButtonItem(SettingsHeaderItem header, String title, View.OnClickListener listener) {
        super(header);
        mTitle = title;
        mListener = listener;
    }

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof RedRoundedButtonItem) {
            RedRoundedButtonItem inItem = (RedRoundedButtonItem) inObject;
            return this == inItem;
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
        return R.layout.button_list_item;
    }

    @Override
    public SettingsViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new SettingsViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, SettingsViewHolder holder, int position, List payloads) {
        holder.mBtnAction.setText(mTitle);
        holder.mBtnAction.setOnClickListener(mListener);
    }

    public class SettingsViewHolder extends FlexibleViewHolder {

        @Bind(R.id.btn_action) TextView mBtnAction;

        public SettingsViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }
    }
}
