package au.com.holcim.holcimapp;

import android.content.Context;


/**
 * Put it all there baybay
 */
public class Constants {

    public static String ENDPOINT;

    public static void init(Context ctx) {
        ENDPOINT = ctx.getString(R.string.endpoint);
    }

    public static class Extras {
        public static final String Customer = "Constants.Extras.Customer";
        public static final String Alert = "Constants.Extras.Alert";
        public static final String LinkedCustomer = "Constants.Extras.LinkedCustomer";
        public static final String Note = "Constants.Extras.Note";
        public static final String CustomerType = "Constants.Extras.CustomerType";
        public static final String LaunchedFromNotification = "Constants.Extras.LaunchedFromNotification";
        public static final String Unauthorized = "Constants.Extras.Unauthorized";
    }

    public static class RequestKey {
        public static final int requestPhonePermission = 301;
        public static final int requestEmailPermission = 302;
        public static final int addNote = 303;
        public static final int viewAlertDetails = 304;
        public static final int composeEmail = 305;
    }

    public static class ResultKey {
        public static final int result_alert_deleted = -301;
    }
}
