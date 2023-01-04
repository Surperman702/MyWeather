package com.sunnyweather.android.ui.weather

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.cxz.kotlin.baselibs.base.BaseMvpActivity
import com.dylanc.viewbinding.binding
import com.sunnyweather.android.R
import com.sunnyweather.android.databinding.ActivityWeatherBinding
import com.sunnyweather.android.databinding.ForecastBinding
import com.sunnyweather.android.databinding.LifeIndexBinding
import com.sunnyweather.android.databinding.NowBinding
import com.sunnyweather.android.logic.model.DailyResponse
import com.sunnyweather.android.logic.model.RealtimeResponse
import com.sunnyweather.android.logic.model.getSky
import com.sunnyweather.android.mvp.contract.WeatherContract
import com.sunnyweather.android.mvp.presenter.WeatherPresenter
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : BaseMvpActivity<WeatherContract.View, WeatherContract.Presenter>(),
    WeatherContract.View {

    override fun createPresenter(): WeatherContract.Presenter = WeatherPresenter()

    private val binding: ActivityWeatherBinding by binding()

    private val bindingNow: NowBinding by binding()

    private val bindingForecast: ForecastBinding by binding()

    private val bindingLifeIndex: LifeIndexBinding by binding()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

//    val viewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 调用了getWindow().getDecorView()方法拿到当前Activity的DecorView，
        // 再调用它的setSystemUiVisibility()方法来改变系统UI的显示，
        // 这里传入View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN和View.SYSTEM_UI_FLAG_LAYOUT_STABLE就表示Activity的布局会显示在状态栏上面，
        // 最后调用一下setStatusBarColor()方法将状态栏设置成透明色
        val decorView = window.decorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
//        setContentView(R.layout.activity_weather)
        initViews()
    }

    private fun initViews() {
        if (locationLng.isEmpty()) {
            locationLng = intent.getStringExtra("location_lng") ?: ""
        }
        if (locationLat.isEmpty()) {
            locationLat = intent.getStringExtra("location_lat") ?: ""
        }
        if (placeName.isEmpty()) {
            placeName = intent.getStringExtra("place_name") ?: ""
        }

//        viewModel.weatherLiveData.observe(this, Observer { result ->
//            val weather = result.getOrNull()
//            if (weather != null) {
//                showWeatherInfo(weather)
//            } else {
//                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()
//                result.exceptionOrNull()?.printStackTrace()
//            }
//            binding.swipeRefresh.isRefreshing = false
//        })

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        refreshWeather()
        binding.swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
        // 调用DrawerLayout的openDrawer()方法来打开滑动菜单
        bindingNow.navBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        // 监听DrawerLayout的状态，当滑动菜单被隐藏的时候，同时也要隐藏输入法
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

            override fun onDrawerOpened(drawerView: View) {}

            override fun onDrawerStateChanged(newState: Int) {}

            override fun onDrawerClosed(drawerView: View) {
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

        })
    }

    fun refreshWeather() {
//        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
        mPresenter?.getRealtimeWeather(locationLng, locationLat)
        mPresenter?.getDailyWeather(locationLng, locationLat)
        binding.swipeRefresh.isRefreshing = true
    }

    override fun onRealtimeWeatherResult(data: RealtimeResponse?) {
        if (data != null) {
            showRealtimeWeather(data)
        } else {
            showMsg("无法成功获取天气信息")
        }
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onRealtimeWeatherError(msg: String) {
        showMsg(msg)
    }

    override fun onDailyWeatherResult(data: DailyResponse?) {
        if (data != null) {
            showDailyWeather(data)
        } else {
            showMsg("无法成功获取天气信息")
        }
        binding.swipeRefresh.isRefreshing = false
    }

    override fun onDailyWeatherError(msg: String) {
        showMsg(msg)
    }

    private fun showRealtimeWeather(data: RealtimeResponse) {
        // 向now.xml填充数据
        bindingNow.placeName.text = placeName
        val realtime = data.result.realtime
        val currentTempText = "${realtime.temperature.toInt()} ℃"
        bindingNow.currentSky.text = getSky(realtime.skycon).info
        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
        bindingNow.currentAQI.text = currentPM25Text
        bindingNow.nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
    }

    private fun showDailyWeather(data: DailyResponse) {
        // 向forecast.xml填充数据
        bindingForecast.forecastLayout.removeAllViews()
        val daily = data.result.daily
        val days = data.result.daily.skycon.size
        for (i in 0 until days) {
            val skycon = daily.skycon[i]
            val temperature = daily.temperature[i]
            val view =
                LayoutInflater.from(this)
                    .inflate(R.layout.forecast_item, bindingForecast.forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skycon.date)
            val sky = getSky(skycon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            bindingForecast.forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        bindingLifeIndex.coldRiskText.text = lifeIndex.coldRisk[0].desc
        bindingLifeIndex.dressingText.text = lifeIndex.dressing[0].desc
        bindingLifeIndex.ultravioletText.text = lifeIndex.ultraviolet[0].desc
        bindingLifeIndex.carWashingText.text = lifeIndex.carWashing[0].desc
        binding.weatherLayout.visibility = View.VISIBLE
    }

//    private fun showWeatherInfo(weather: Weather) {
//        binding.placeName.text = viewModel.placeName
//        val realtime = weather.realtime
//        val daily = weather.daily
//        // 向now.xml填充数据
//        val currentTempText = "${realtime.temperature.toInt()} ℃"
//        binding.currentTemp.text = currentTempText
//        currentSky.text = getSky(realtime.skycon).info
//        val currentPM25Text = "空气指数 ${realtime.airQuality.aqi.chn.toInt()}"
//        currentAQI.text = currentPM25Text
//        nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
//        // 向forecast.xml填充数据
//        forecastLayout.removeAllViews()
//        val days = daily.skycon.size
//        for (i in 0 until days) {
//            val skycon = daily.skycon[i]
//            val temperature = daily.temperature[i]
//            val view =
//                LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
//            val dateInfo = view.dateInfo as TextView
//            val skyIcon = view.skyIcon as ImageView
//            val skyInfo = view.skyInfo as TextView
//            val temperatureInfo = view.temperatureInfo as TextView
//            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//            dateInfo.text = simpleDateFormat.format(skycon.date)
//            val sky = getSky(skycon.value)
//            skyIcon.setImageResource(sky.icon)
//            skyInfo.text = sky.info
//            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
//            temperatureInfo.text = tempText
//            forecastLayout.addView(view)
//        }
//        // 填充life_index.xml布局中的数据
//        val lifeIndex = daily.lifeIndex
//        coldRiskText.text = lifeIndex.coldRisk[0].desc
//        dressingText.text = lifeIndex.dressing[0].desc
//        ultravioletText.text = lifeIndex.ultraviolet[0].desc
//        carWashingText.text = lifeIndex.carWashing[0].desc
//        weatherLayout.visibility = View.VISIBLE
//    }

}