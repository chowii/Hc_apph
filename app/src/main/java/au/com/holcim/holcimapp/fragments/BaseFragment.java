package au.com.holcim.holcimapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import au.com.holcim.holcimapp.R;
import au.com.holcim.holcimapp.helpers.AlertDialogHelper;
import au.com.holcim.holcimapp.helpers.NavHelper;
import au.com.holcim.holcimapp.helpers.SharedPrefsHelper;
import au.com.holcim.holcimapp.network.HttpStatusException;
import butterknife.Bind;

/**
 * Created by Jovan on 21/4/17.
 */

public class BaseFragment extends Fragment {

    @Nullable
    @Bind(R.id.toolbar) public Toolbar mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setToolbar(String title, int image) {
        if(mToolbar != null) {
            setHasOptionsMenu(true);
            mToolbar.setTitle(title);
            mToolbar.setNavigationIcon(image);
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        }
    }

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
        } else {
            if(showInternetErrors) {
                if (coordinatorLayout != null) {
                    Snackbar.make(coordinatorLayout, "Something went wrong, please try again.", Snackbar.LENGTH_LONG).show();
                } else {
                    AlertDialogHelper.showOk(getActivity(), "Error", "Something went wrong, please try again.");
                }
            } else {
                handleCustomError("Something went wrong, please try again.");
            }
        }
    }

    // Override to handle errors passed on by handleError when showInternetErrors == false
    public void handleCustomError(String error) {
        //To Override if needed
    }
}
