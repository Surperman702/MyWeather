package com.cxz.kotlin.baselibs.utils

import android.os.Environment
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import java.io.File

/**
 * <pre>
 *     author : yitouniu
 *     time   : 2021/02/01
 *     desc   :
 *     腾讯 XLog 日志
 *     按天存储 每天一个日志文件
 * </pre>
 */
object XLogHelper {

    init {
        System.loadLibrary("c++_shared")
        System.loadLibrary("marsxlog")
    }

    //默认的日志存储位置
    private val defPath = Environment.getExternalStorageDirectory().absolutePath + "/techHuh/navigation"

    // mmap 位置 默认缓存的位置
//    private var cachePath = "$defPath/cache"

    //实际保存的log 位置
    private var logPath = "$defPath/logDir"

    //文件名称前缀 huh，生成的文件名为：huh_20210202.xlog
    private var namePreFix = "huh"

    private val defaultTag = "TAG"


    fun init() {
//        val cacheFile = File(cachePath)
        val logFile = File(logPath)
//        if (!cacheFile.exists()) {
//            cacheFile.mkdirs()
//        }
        if (!logFile.exists()) {
            logFile.mkdirs()
        }
        Log.setLogImp(Xlog())
        // 是否将log日志输出到控制台 true  输出  false  不输出
        Log.setConsoleLogOpen(true)
        // log日志文件写入  所有的log日志 均写入  异步写入  cacheday  0： 默认保存在设备中的时间为10天
        Log.appenderOpen(Xlog.LEVEL_VERBOSE, Xlog.AppednerModeAsync, null, logPath, namePreFix, 10)
    }


    //=====================================================================================
    // v
    //=====================================================================================
    fun v(msg: String) {
        Log.v(defaultTag, msg)
    }


    //=====================================================================================
    // f
    //=====================================================================================
    fun f(msg: String) {
        Log.f(defaultTag, msg)
    }


    //=====================================================================================
    // e
    //=====================================================================================
    fun e(msg: String) {
        Log.e(defaultTag, msg)
    }


    //=====================================================================================
    // w
    //=====================================================================================
    fun w(msg: String) {
        Log.w(defaultTag, msg)
    }


    //=====================================================================================
    // w
    //=====================================================================================
    fun i(msg: String) {
        Log.i(defaultTag, msg)
    }


    //=====================================================================================
    // d
    //=====================================================================================
    fun d(msg: String) {
        Log.d(defaultTag, msg)
    }


    //关闭日志，不再写入
    fun close() {
        Log.appenderClose()
    }

    //当日志写入模式为异步时，调用该接口会把内存中的日志写入到文件。
    fun flush() {
        Log.appenderFlush()
    }

}