package au.com.holcim.holcimapp.helpers;

import android.app.Activity;
import android.content.Intent;

import au.com.holcim.holcimapp.Constants;
import au.com.holcim.holcimapp.activities.LoginActivity;
import au.com.holcim.holcimapp.activities.MainActivity;
import au.com.holcim.holcimapp.activities.OrderDetailActivity;
import au.com.holcim.holcimapp.activities.WebViewActivity;

/**
 * Created by Jovan on 12/4/17.
 */

public class NavHelper {

    public static void showWebView(Activity activity, String url) {
        activity.startActivity(new Intent(activity, WebViewActivity.class).putExtra(Constants.Extras.WEBVIEW_URL, url));
    }

    public static void showMainActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    public static void showLandingActivity(Activity activity, String reason) {
        activity.startActivity(new Intent(activity, LoginActivity.class).putExtra(Constants.Extras.REASON, reason));
        activity.finish();
    }

    public static void showOrderDetailActivity(Activity activity, int id) {
        activity.startActivity(new Intent(activity, OrderDetailActivity.class).putExtra(Constants.Extras.ORDER_ID, id));
    }
}
