package au.com.holcim.holcimapp.eventbus;

import android.view.View;

/**
 * Created by chowii on 26/05/17.
 */

public class SaveSettingsEvent {

    public View view;

    public SaveSettingsEvent(View view) {
        this.view = view;
    }
}
