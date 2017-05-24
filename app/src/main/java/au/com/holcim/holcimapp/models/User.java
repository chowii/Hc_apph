package au.com.holcim.holcimapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jovan on 13/4/17.
 */

public class User {

    public int id;
    @SerializedName("phone_number")
    public String phoneNumber;
    @SerializedName("order_state")
    public String state;
    @SerializedName("auth_token")
    public String authToken;


}
