package au.com.holcim.holcimapp;

import android.app.Activity;
import android.content.Intent;

import au.com.holcim.holcimapp.activities.WebViewActivity;

/**
 * Created by Jovan on 12/4/17.
 */

public class NavHelper {

    public static void showWebView(Activity activity, String url) {
        activity.startActivity(new Intent(activity, WebViewActivity.class).putExtra(Constants.Extras.WEBVIEW_URL, url));
    }

}
