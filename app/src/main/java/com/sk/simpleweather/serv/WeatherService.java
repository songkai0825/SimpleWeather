package com.sk.simpleweather.serv;

import com.sk.simpleweather.json.WeatherBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherService {

    @GET("{city_code}")
    Observable<WeatherBean> getCall(@Path("city_code") String code);

    @GET("https://cdn.heweather.com/cond_icon/{picture_id}")
    Observable<ResponseBody> getPicture(@Path("picture_id") String id);

}
