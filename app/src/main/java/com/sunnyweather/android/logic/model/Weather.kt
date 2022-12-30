package com.sunnyweather.android.logic.model

/**
 * <pre>
 *     @author : lvkuiliang
 *     @e-mail : 1943641461@qq.com
 *     @desc   : 用于将Realtime和Daily封装起来
 *     @created: 2022/12/28 16:57
 * </pre>
 */
data class Weather(val realtime: RealtimeResponse.RealTime, val daily: DailyResponse.Daily)
