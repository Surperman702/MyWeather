package com.sunnyweather.android.mvp.contract

import com.cxz.kotlin.baselibs.http.HttpResult
import com.cxz.kotlin.baselibs.mvp.IModel
import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView
import com.sunnyweather.android.logic.model.DailyResponse
import com.sunnyweather.android.logic.model.RealtimeResponse
import io.reactivex.rxjava3.core.Observable

interface WeatherContract {

    interface View : IView {
        fun onRealtimeWeatherResult(data: RealtimeResponse?)

        fun onRealtimeWeatherError(msg: String)

        fun onDailyWeatherResult(data: DailyResponse?)

        fun onDailyWeatherError(msg: String)
    }

    interface Presenter : IPresenter<View> {
        fun getRealtimeWeather(lng: String, lat: String)

        fun getDailyWeather(lng: String, lat: String)
    }

    interface Model : IModel {
        fun getRealtimeWeather(lng: String, lat: String): Observable<HttpResult<RealtimeResponse>>

        fun getDailyWeather(lng: String, lat: String): Observable<HttpResult<DailyResponse>>
    }

}