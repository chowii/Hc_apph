package au.com.holcim.holcimapp.network;


import android.util.Log;

import java.io.IOException;

import au.com.holcim.holcimapp.helpers.SharedPrefsHelper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jovan on 21/4/17.
 */
public class StatusCodeInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Response response = chain.proceed(request);

        int statusCode = response.code();
            if(statusCode < 200 || statusCode > 299 || response.body() == null) {
                Log.d("TAG", "interceptStatusCode: " + statusCode);
                handleErrorResponse(response);
        }

        return response;
    }

    private void handleErrorResponse(Response response) throws HttpStatusException {
        int statusCode = response.code();
        if(statusCode == 401) {
            throw new HttpStatusException("Your session has expired, please relogin.", HttpErrorType.ReloginRequired);
        } else if(statusCode == 419) {
            throw new HttpStatusException("Pin re-entry required to continue.", HttpErrorType.PinReEnterRequired);
        } else if(statusCode == 480) {
            throw new HttpStatusException("Pin has expired, please request a new one.", HttpErrorType.PinResetRequired);
        } else if(response == null) {
            throw new HttpStatusException(new HolcimError().error, HttpErrorType.General);
        }
        HolcimError error = HolcimError.fromResponse(response);
        throw new HttpStatusException(error.getMessage(), HttpErrorType.General);
    }
}