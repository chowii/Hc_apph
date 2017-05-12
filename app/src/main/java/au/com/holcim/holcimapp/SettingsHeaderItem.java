package au.com.holcim.holcimapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractHeaderItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by Jovan on 21/4/17.
 */


public class SettingsHeaderItem extends AbstractHeaderItem<SettingsHeaderItem.SettingsHeaderViewHolder> {

    public String title;

    public SettingsHeaderItem(String title) {
        this.title = title;
        setHidden(false);
        setSelectable(false);
    }

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof SettingsHeaderItem) {
            SettingsHeaderItem inItem = (SettingsHeaderItem) inObject;
            return this.title.equals(inItem.title);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.settings_header_item;
    }

    @Override
    public SettingsHeaderViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        return new SettingsHeaderViewHolder(inflater.inflate(getLayoutRes(), parent, false), adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, SettingsHeaderViewHolder holder, int position, List payloads) {
        holder.mTvTitle.setText(title);
    }

    public class SettingsHeaderViewHolder extends FlexibleViewHolder {

        @Bind(R.id.txt_title) TextView mTvTitle;

        public SettingsHeaderViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            ButterKnife.bind(this, view);
        }
    }
}

