package au.com.holcim.holcimapp;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static HolcimError fromResponse(ResponseBody body, int statusCode) {
        HolcimError error = new HolcimError();
        try {
            String errorBodyString = body.string();
            error = fromJson(errorBodyString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        error.relogRequired = statusCode == 401;
        return error;
    }

    public static HolcimError fromJson(String json) {
        return new Gson().fromJson(json, HolcimError.class);
    }

    public static void handleError(ResponseBody body, int statusCode, Context ctx, View view) {
        HolcimError error = HolcimError.fromResponse(body, statusCode);
        if(error.relogRequired) {
            //TODO: Handle this correctly
//            SharedUser.getInstance().logout(ctx, true);
        } else {
            Snackbar.make(view, error.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
}
