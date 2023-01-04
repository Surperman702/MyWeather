package com.cxz.kotlin.baselibs.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import java.util.*

object DeviceUtils {


    /**
     * 读取设备的IMEI号
     */
    @SuppressLint("HardwareIds")
    private fun getDevicesIMEI(context: Context): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            null
        } else {
            val tm: TelephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            tm.deviceId
        }
    }

    /**
     * 随机生成一串设备号
     */
    private fun getDevicesUUID(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }


    fun getDeviceId(context: Context): String {
        var deviceId = getDevicesIMEI(context)
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = getDevicesUUID()
        }
        return MD5Utils.getMD5(deviceId!!, false)
    }

}