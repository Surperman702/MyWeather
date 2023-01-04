package com.cxz.kotlin.baselibs.contract

object Constant {

    /**
     * token
     */
    const val TOKEN = "device_token"

    /**
     * 组织机构代码
     * zzjgdm
     */
    const val OIB_CODE = "oibCode"

    /**
     * 纬度
     */
    const val LAT = "latitude"

    /**
     * 自动出诊的范围半径 为0就不自动出诊 否则设置为出诊的范围半径
     */
    const val AUTO_RADIUS = "auto_radius"

    /**
     * 经度
     */
    const val LNG = "longitude"


    /**
     * 车辆是否派诊
     */
    const val SF_PZ = "1"

    /**
     * 设备ID
     */
    const val DEVICE_ID = "device_id"


    /**
     * 载体代码
     */
    const val ZT_DM = "car_no"


    /**
     * 载体车牌
     */
    const val ZT_PZ = "car_no"


    /**
     * baseUrl 用户设置的
     */
//    const val URL_INFO = "https://192.168.0.5:90/" 富裕县中医院
//    const val URL_INFO = "https://schh.work:2017/"
    const val URL_INFO = "url_info"

    /**
     * 调度信息
     */
    const val DISPATCH_INFO = "dispatch_info"

    /**
     * 高德终端id
     */
    const val TERMINAL_ID = "terminal_id"

    /**
     * 高德轨迹上报id
     */
    const val TRACK_ID = "track_id"


    /**
     * 设置一个默认的值 防止初始化retort的时候出现异常
     */
//    const val DEFAULT_BASE_URL = "https://192.168.0.5:90/" 富裕县中医院
//    const val DEFAULT_BASE_URL = "https://schh.work:2017/"
//    const val DEFAULT_BASE_URL = "https://localhost:90/"
    const val DEFAULT_BASE_URL = "https://api.caiyunapp.com/"


    /**
     * 返程地址
     */
    const val BACK_LOCAL_LAT = "back_lat"

    /**
     * 返程地址
     */
    const val BACK_LOCAL_LNG = "back_lng"

    /**
     * 通知栏id
     */
    const val NOTIFICATION_ID = 1001

    /**
     * 未出诊
     */
    const val CAR_NOT_START_OFF = 0

    /**
     * 已出诊未到达目的地
     */
    const val CAR_START_NOT_ARRIVE = 1

    /**
     * 已到达目的地未返程
     */
    const val CAR_ARRIVE_NOT_BACK = 2

    /**
     * 已返程未到院
     */
    const val CAR_BACK_NOT_ARRIVE = 3

    /**
     * 已到院
     */
//    const val CAR_BACK_ARRIVE = 4

    /**
     * 地理围栏的广播
     */
    const val GEOFENCE_BROADCAST_ACTION = "com.location.apis.dispatch.broadcast"


    const val CZ_TEXT = "出诊"
    const val DD_XC_TEXT = "到达现场"
    const val WJ_FC_TEXT = "未接返程"
    const val FC_TEXT = "返程"
    const val JZ_HZ_TEXT = "救治患者"
    const val DY_TEXT = "到院"
    const val ERR_TEXT = "错误"


}