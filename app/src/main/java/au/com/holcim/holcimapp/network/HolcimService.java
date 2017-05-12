package au.com.holcim.holcimapp.network;

import java.util.List;
import java.util.Map;

import au.com.holcim.holcimapp.models.BasicOrder;
import au.com.holcim.holcimapp.models.Order;
import au.com.holcim.holcimapp.models.Settings;
import au.com.holcim.holcimapp.models.User;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Jovan on 12/4/17.
 */

public interface HolcimService {

    @GET("users")
    Call<User> getCurrentUser();

    @POST("sms")
    Call<User> requestSms(@Body Map<String, String> map);

    @POST("sms/verify")
    Call<User> verifySms(@Body Map<String, Object> map);

    @GET("orders")
    Call<List<BasicOrder>> getOrders();

    @GET("orders/{id}")
    Call<Order> getOrder(@Path("id") int id);

    @GET("settings")
    Call<Settings> getSettings();

    @PUT("settings")
    Call<Settings> updateSettings(@Body Map<String, Object> map);

    @DELETE("devices")
    Call<Response> removeDeviceToken(@Body Map<String, Object> map);

}
