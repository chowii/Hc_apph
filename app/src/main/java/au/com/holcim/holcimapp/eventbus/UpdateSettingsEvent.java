package au.com.holcim.holcimapp.eventbus;

import au.com.holcim.holcimapp.models.Settings;

/**
 * Created by chowii on 26/05/17.
 */

public class UpdateSettingsEvent {

    private Settings settings;

    public Settings getSettings() { return settings; }

    public UpdateSettingsEvent(Settings settings) {
        this.settings = settings;
    }
}
