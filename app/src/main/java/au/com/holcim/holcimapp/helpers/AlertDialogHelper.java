package au.com.holcim.holcimapp.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by William on 26/02/16.
 */
public class AlertDialogHelper {

    public static void showAlert(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, listener)
                .setNegativeButton(android.R.string.no, (dialog, which) -> dialog.dismiss())
                .show();
    }

    public static AlertDialog showAlert(Context context, String title, String message, String yesButton, DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(yesButton, listener)
                .setNegativeButton(android.R.string.no, (dialog, which) -> dialog.dismiss())
                .show();
    }

    public static void showOk(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, listener)
                .show();
    }

    public static void showOk(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                .show();
    }

    public static void showOption(Context context, String title, String message, String yes, String no, DialogInterface.OnClickListener yesListener, DialogInterface.OnClickListener noListener) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(yes, yesListener)
                .setNegativeButton(no, noListener)
                .show();
    }
}
