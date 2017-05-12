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

    public static class Urls {
        static String faq =  "https://trackmyorder.info/faq/";
        static String howTo = "https://trackmyorder.info/how-to/";
    }

    public static class Extras {
        public static final String WEBVIEW_URL = "au.com.holcim.holcimapp.Constants.Extras.WEBVIEW_URL";
        public static final String REASON = "au.com.holcim.holcimapp.Constants.Extras.REASON";
        public static final String ORDER_ID = "au.com.holcim.holcimapp.Constants.Extras.ORDER_ID";
        public static final String ORDER = "au.com.holcim.holcimapp.Constants.Extras.ORDER";
        public static final String TICKET_ID = "au.com.holcim.holcimapp.Constants.Extras.TICKET_ID";
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
