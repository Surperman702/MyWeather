package com.sunnyweather.android.mvp.model

import com.cxz.kotlin.baselibs.http.HttpHelper
import com.cxz.kotlin.baselibs.http.HttpResult
import com.cxz.kotlin.baselibs.mvp.BaseModel
import com.sunnyweather.android.api.WeatherService
import com.sunnyweather.android.logic.model.DailyResponse
import com.sunnyweather.android.logic.model.RealtimeResponse
import com.sunnyweather.android.mvp.contract.WeatherContract
import io.reactivex.rxjava3.core.Observable

class WeatherModel : BaseModel(), WeatherContract.Model {

    private val apiService: WeatherService by lazy {
        HttpHelper.getRetrofit().create(WeatherService::class.java)
    }

    override fun getRealtimeWeather(
        lng: String,
        lat: String
    ): Observable<HttpResult<RealtimeResponse>> {
        return apiService.getRealtimeWeather(lng, lat)
    }

    override fun getDailyWeather(lng: String, lat: String): Observable<HttpResult<DailyResponse>> {
        return apiService.getDailyWeather(lng, lat)
    }

}