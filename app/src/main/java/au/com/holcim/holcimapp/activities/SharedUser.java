package au.com.holcim.holcimapp.activities;

import au.com.holcim.holcimapp.models.User;

/**
 * Created by chowii on 24/05/17.
 */

public class SharedUser {

    private static SharedUser uniqueInstance;
    private User user;

    public synchronized static SharedUser getInstance() {
        if (uniqueInstance == null) uniqueInstance = new SharedUser();
        return uniqueInstance;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){ return user; }

}

