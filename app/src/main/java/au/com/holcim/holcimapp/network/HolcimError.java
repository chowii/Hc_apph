package au.com.holcim.holcimapp.network;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Jovan on 12/4/17.
 */

public class HolcimError {

    @SerializedName("error") public String error;
    public boolean relogRequired = false;

    public HolcimError() {
        error = "Failed to complete request, please try again.";
    }

    public String getMessage() {
        return error;
    }

    public static HolcimError fromResponse(Response response) {
        HolcimError error = new HolcimError();
        try {
            String errorBodyString = response.body().string();
            error = fromJson(errorBodyString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return error;
    }

    public static HolcimError fromJson(String json) {
        try {
            return new Gson().fromJson(json, HolcimError.class);
        } catch(Exception e) {
            return new HolcimError();
        }
    }

}
