package au.com.holcim.holcimapp.network;

import com.google.gson.GsonBuilder;

import au.com.holcim.holcimapp.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jovan on 12/4/17.
 */

public class ApiClient {

    private static HolcimService service;

    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new AddAuthorizationInterceptor())
            .addInterceptor(loggingInterceptor)
            .build();

    public static Retrofit retrofit =
            new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(Constants.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

    public static HolcimService getService() {
        if (service == null) {
            service = retrofit.create(HolcimService.class);
        }
        return service;
    }

    public static GsonConverterFactory buildGson() {
        return GsonConverterFactory.create(new GsonBuilder().create());
    }

}