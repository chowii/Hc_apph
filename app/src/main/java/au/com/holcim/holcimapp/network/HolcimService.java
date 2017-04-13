package au.com.holcim.holcimapp.network;

import java.util.Map;

import au.com.holcim.holcimapp.User;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Jovan on 12/4/17.
 */

public interface HolcimService {

    @POST("sms")
    Call<User> requestSms(@Body Map<String, String> map);

    @POST("sms/verify")
    Call<User> verifySms(@Body Map<String, Object> map);

}
