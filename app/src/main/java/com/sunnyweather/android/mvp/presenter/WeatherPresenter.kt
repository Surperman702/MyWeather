package com.sunnyweather.android.mvp.presenter

import com.cxz.kotlin.baselibs.ext.ss
import com.cxz.kotlin.baselibs.mvp.BasePresenter
import com.sunnyweather.android.mvp.contract.WeatherContract
import com.sunnyweather.android.mvp.model.WeatherModel

class WeatherPresenter : BasePresenter<WeatherContract.Model, WeatherContract.View>(),
    WeatherContract.Presenter {

    override fun createModel(): WeatherContract.Model = WeatherModel()

    override fun getRealtimeWeather(lng: String, lat: String) {
        mModel?.getRealtimeWeather(lng, lat)?.ss(mModel, mView, onSuccess = {
            mView?.onRealtimeWeatherResult(it.data)
        }, onError = {
            mView?.onRealtimeWeatherError(it.message)
        })
    }

    override fun getDailyWeather(lng: String, lat: String) {
        mModel?.getDailyWeather(lng, lat)?.ss(mModel, mView, onSuccess = {
            mView?.onDailyWeatherResult(it.data)
        }, onError = {
            mView?.onDailyWeatherError(it.message)
        })
    }
}