package com.cxz.kotlin.baselibs.utils

import android.content.Context
import android.os.Parcelable
import com.tencent.mars.xlog.Log
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKV.MULTI_PROCESS_MODE


/**
 * SharedPreference 替代方案
 */
object MmkvUtils {
    private lateinit var mv: MMKV

    fun init(context: Context) {
        val initDir = MMKV.initialize(context)
        //MULTI_PROCESS_MODE 多进程访问标识 default id 标识
        mv = MMKV.mmkvWithID("default",MULTI_PROCESS_MODE)
        Log.d("mmkv","mmkv init dir -> $initDir")
    }

    fun encode(key: String, any: Any) {
        when (any) {
            is String -> mv.encode(key, any)
            is Int -> mv.encode(key, any)
            is Boolean -> mv.encode(key, any)
            is Float -> mv.encode(key, any)
            is Long -> mv.encode(key, any)
            is Double -> mv.encode(key, any)
            is ByteArray -> mv.encode(key, any)
            else -> mv.encode(key, any.toString())
        }
    }

        fun encodeSet(key: String, sets: Set<String>) {
        mv.encode(key, sets)
    }


    fun encodeParcelable(key: String, value: Parcelable) {
        mv.encode(key, value)
    }


    fun decodeInt(key: String): Int {
        return mv.decodeInt(key, 0)
    }

    fun decodeDouble(key: String): Double {
        return mv.decodeDouble(key, 0.00)
    }

    fun decodeLong(key: String): Long {
        return mv.decodeLong(key, 0L)
    }

    fun decodeBoolean(key: String): Boolean {
        return mv.decodeBool(key, false)
    }

    fun decodeFloat(key: String): Float {
        return mv.decodeFloat(key, 0f)
    }

    fun decodeBytes(key: String): ByteArray? {
        return mv.decodeBytes(key)
    }

    fun decodeString(key: String): String? {
        return mv.decodeString(key, null)
    }

    fun decodeStringSet(key: String): Set<String>? {
        return mv.decodeStringSet(key, null)
    }

    fun <T : Parcelable> decodeParcelable(key: String, tClass: Class<T>): T? {
        return mv.decodeParcelable(key, tClass)
    }


    fun containsKey(key: String): Boolean {
        return mv.containsKey(key)
    }


    fun removeKey(key: String) {
        mv.removeValueForKey(key)
    }

    fun clearAll() {
        mv.clearAll()
    }
}

