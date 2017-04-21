package au.com.holcim.holcimapp.fragments;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import au.com.holcim.holcimapp.helpers.AlertDialogHelper;
import au.com.holcim.holcimapp.helpers.NavHelper;
import au.com.holcim.holcimapp.helpers.SharedPrefsHelper;
import au.com.holcim.holcimapp.network.HttpStatusException;

/**
 * Created by Jovan on 21/4/17.
 */

public class BaseFragment extends Fragment {

    public void handleError(Throwable t) {
        handleError(t, true, null);
    }

    public void handleError(Throwable t, boolean showInternetErrors, CoordinatorLayout coordinatorLayout) {
        if (t instanceof HttpStatusException) {
            HttpStatusException httpException = (HttpStatusException) t;
            switch(httpException.errorType) {
                case General:
                    if(showInternetErrors) {
                        if (coordinatorLayout != null) {
                            Snackbar.make(coordinatorLayout, httpException.error, Snackbar.LENGTH_LONG).show();
                        } else {
                            AlertDialogHelper.showOk(getActivity(), "Error", httpException.error);
                        }
                    } else {
                        handleCustomError(httpException.error);
                    }
                    break;
                case PinReEnterRequired:
                case PinResetRequired:
                    NavHelper.showLandingActivity(getActivity(), httpException.error);
                    break;
                case ReloginRequired:
                    SharedPrefsHelper.getInstance().removeUserCredentials();
                    NavHelper.showLandingActivity(getActivity(), httpException.error);
                    break;
            }
        }
    }

    // Override to handle errors passed on by handleError when showInternetErrors == false
    public void handleCustomError(String error) {
        //To Override if needed
    }
}
