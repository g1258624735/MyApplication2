package com.example.administrator.myapplication.intefece;


import com.example.administrator.myapplication.bean.NewsBody;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by hugo on 2016/2/16 0016.
 */
public interface ApiInterface {
    String HOST = "http://183.60.90.147/grcapp/entry/";
//    String HOST = "http://m.9niu.com/user/login/";

//@FormUrlEncoded
//@POST("/user/edit")
//User updateUser(@Field("first_name") String first, @Field("last_name") String last);
    @POST("/")
    Observable<NewsBody> mPostAPI(@Field("first_name") String first);

    //而且在Retrofit 2.0中我们还可以在@Url里面定义完整的URL：这种情况下Base URL会被忽略。
    //@Query("api_token") String api_token
    @GET("http://emgw1.apps123.cn/EPlus/member_commonMemberLogin.action?jsoncallback=appjsoncallback&identity=1&appId=4028835353a64f530153a74a51d70158&branchInfoId=4028835353a64f530153a74a5495015c&deviceType=3&token=APPS123868013028987927d74971f65aff474ea7f840a402fa502e&mobilePhone=13538951807&password=123456")
    Observable<String> mGetAPI();

    @GET("http://gank.io/api/day/2016/4/{day}")
    Observable<String> mGetPicList(@Path("day") int  day);
    @POST("/")
    Observable<String> mGetLoginAPI(@Query("username") String username, @Query("password") String password);
}
