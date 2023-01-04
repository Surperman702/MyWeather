package com.sunnyweather.android.api

import com.cxz.kotlin.baselibs.http.HttpResult
import com.sunnyweather.android.SunnyWeatherApplication
import com.sunnyweather.android.logic.model.PlaceResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    /**
     * 搜索城市数据的API中只有query这个参数是需要动态指定的，我们使用@Query注解的方式来进行实现
     */
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Observable<HttpResult<PlaceResponse>>

}