package com.sk.simpleweather.serv;

import com.sk.simpleweather.json.CityBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CityService {

    @GET("find")
    Observable<CityBean> getCall(@Query("location") String location,
                                 @Query("key") String key);

}
