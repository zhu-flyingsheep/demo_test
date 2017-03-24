package com.utsoft.myapplication.Interface;

import com.utsoft.myapplication.model.DoubanUser;
import com.utsoft.myapplication.model.UserResult;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by wangzhu on 2017/3/7.
 * func:
 */

public interface Idoubanuser {
    public final String URL = "http://www.yundao91.cn/ssh2/";

    @GET("ahbei")
    Call<DoubanUser> getUser();

    @GET("{username}")
    Call<DoubanUser> getUser(@Path("username") String username);

    @POST("add")
    Call<DoubanUser> addUser(@Body DoubanUser user);

    @POST("tour")
    @FormUrlEncoded
    Call<UserResult> login(@Field("cmd") String cmd, @Field("TEL") String username, @Field("PWD") String password);

    @POST("tour")
    @FormUrlEncoded
    Observable<UserResult> login_rx(@Field("cmd") String cmd, @Field("TEL") String username, @Field("PWD") String password);


    @Multipart
    @POST("register")
    Call<DoubanUser> registerUser(@PartMap Map<String, RequestBody> params, @Part("password") RequestBody password);

    @Streaming
    @GET("laiya.apk")
    Call<ResponseBody> downloadTest();

    @Multipart
    @POST("userinfo?cmd=addUserTracePic&tel=" + tel + "&title=这是标题&adress=成都市&isshow=0")
//    Call<DoubanUser> upload_pics(@PartMap Map<String, RequestBody> params, @Part("password") RequestBody password);
    Call<Object> upload_pics(@PartMap Map<String, RequestBody> params);

    String tel = "15281060117";

    @Multipart
    @POST
    Call<Object> upload_file(@Url String up_url, @PartMap Map<String, RequestBody> files);
}
