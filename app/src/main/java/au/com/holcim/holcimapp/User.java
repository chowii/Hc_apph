package au.com.holcim.holcimapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jovan on 13/4/17.
 */

public class User {

    int id;
    @SerializedName("phone_number")
    String phoneNumber;
    @SerializedName("order_state")
    String state;
    @SerializedName("auth_token")
    String authToken;

}
