package au.com.holcim.holcimapp.network;

import java.util.Map;

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
    Call<ResponseBody> requestSms(@Body Map<String, String> map);

    @POST("sms/verify")
    Call<ResponseBody> verifySms(@Body Map<String, String> map);

}
