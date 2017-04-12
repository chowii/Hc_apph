package au.com.holcim.holcimapp.network;


import java.io.IOException;

import au.com.holcim.holcimapp.SharedPrefsHelper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by williamma on 15/06/2016.
 */
public class AddAuthorizationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

//        String host = request.url().host();
        String authToken = SharedPrefsHelper.getInstance().getAuthToken();
//        if (authToken.isEmpty()) { //|| !request.url().toString().contains("holcim")) {
//            return chain.proceed(request);
//        }

        Request newRequest = request.newBuilder()
                .addHeader("X-User-Auth-Token", authToken)
                .build();

        return chain.proceed(newRequest);
    }

}
