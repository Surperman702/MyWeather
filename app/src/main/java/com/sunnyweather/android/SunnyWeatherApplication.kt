package com.sunnyweather.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.cxz.kotlin.baselibs.config.AppConfig
import com.cxz.kotlin.baselibs.utils.MmkvUtils

class SunnyWeatherApplication : Application() {

    companion object {

        const val TOKEN = "L0ZN8Vg8N887ITTM"

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        MultiDex.install(this)
        MmkvUtils.init(this)
        AppConfig.init(this)
    }

}