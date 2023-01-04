package com.sunnyweather.android.api

import com.cxz.kotlin.baselibs.http.HttpResult
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.DailyResponse
import com.sunnyweather.android.logic.model.RealtimeResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    /**
     * @desc:获取实时天气信息，使用@Path注解来向请求接口中动态传入经纬度的坐标
     */
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Observable<HttpResult<RealtimeResponse>>

    /**
     * @desc:获取未来天气信息
     */
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): Observable<HttpResult<DailyResponse>>

}