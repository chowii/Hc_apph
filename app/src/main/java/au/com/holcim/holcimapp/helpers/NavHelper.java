package au.com.holcim.holcimapp.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import au.com.holcim.holcimapp.Constants;
import au.com.holcim.holcimapp.activities.AlertSettingsActivity;
import au.com.holcim.holcimapp.activities.AppTour.AppTourActivity;
import au.com.holcim.holcimapp.activities.LoginActivity;
import au.com.holcim.holcimapp.activities.MainActivity;
import au.com.holcim.holcimapp.activities.OrderDetailActivity;
import au.com.holcim.holcimapp.activities.TicketsMapActivity;
import au.com.holcim.holcimapp.activities.WebViewActivity;
import au.com.holcim.holcimapp.models.Order;
import au.com.holcim.holcimapp.models.Settings;
import au.com.holcim.holcimapp.models.Ticket;
import au.com.holcim.holcimapp.network.ApiClient;

/**
 * Created by Jovan on 12/4/17.
 */

public class NavHelper {

    public static void logout(Activity activity) {
        SharedPrefsHelper.getInstance().removeUserCredentials();
        activity.startActivity(new Intent(activity, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_NEW_TASK));
        activity.finish();
    }

    // MARK: - ================== Show Actions ==================

    public static void showWebView(Activity activity, String url) {
        activity.startActivity(new Intent(activity, WebViewActivity.class)
                .putExtra(Constants.Extras.WEBVIEW_URL, url));
    }

    public static void showMainActivity(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();
    }

    public static void showLandingActivity(Activity activity, String reason) {
        activity.startActivity(new Intent(activity, LoginActivity.class)
                .putExtra(Constants.Extras.REASON, reason));
        activity.finish();
    }

    public static void showOrderDetailActivity(Activity activity, int id) {
        activity.startActivity(new Intent(activity, OrderDetailActivity.class)
                .putExtra(Constants.Extras.ORDER_ID, id));
    }

    public static void showTicketsMapActivity(Activity activity, Order order, int selectedTicketId) {
        activity.startActivity(new Intent(activity, TicketsMapActivity.class)
                .putExtra(Constants.Extras.ORDER, order)
                .putExtra(Constants.Extras.TICKET_ID, selectedTicketId));
    }

    public static void showAlertSettingsActivity(Activity activity, Settings settings) {
        activity.startActivity(new Intent(activity, AlertSettingsActivity.class)
                .putExtra(Constants.Extras.SETTINGS, settings));
    }

    // MARK: - ================== Return Intent ==================

    public static Intent webViewActivityIntent(Context context, String url) {
        return new Intent(context, WebViewActivity.class)
                .putExtra(Constants.Extras.WEBVIEW_URL, url);
    }

    public static Intent alertSettingsActivityIntent(Context context, Settings settings) {
        return new Intent(context, AlertSettingsActivity.class)
                .putExtra(Constants.Extras.SETTINGS, settings);
    }

    public static Intent appTourIntent(Context context, Settings settings) {
        return new Intent(context, AppTourActivity.class)
                .putExtra(Constants.Extras.SETTINGS, settings);
    }
}
