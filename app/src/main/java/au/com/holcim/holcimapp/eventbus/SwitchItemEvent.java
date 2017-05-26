package au.com.holcim.holcimapp.eventbus;

/**
 * Created by chowii on 26/05/17.
 */

public class SwitchItemEvent {

    private String key;
    private boolean value;

    public String getKey() {
        return key;
    }

    public boolean isValue() {
        return value;
    }

    public SwitchItemEvent(String key, boolean value) {
        this.key = key;
        this.value = value;
    }
}
