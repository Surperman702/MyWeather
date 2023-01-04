package com.cxz.kotlin.baselibs.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.BARCODE
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.BARCODE_RESULT
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.BAR_CODE
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.BAR_CODE_HNW
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.BAR_CODE_RESULT
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.BAR_CODE_XDL
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.BORCODE_VALUE
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.DECODE_ACTION_HNW
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.DECODE_ACTION_XDL
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.DECODE_DATA
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.DECODE_DATA_JY
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.NEUSOFT
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.SCANNERBROAD
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.SCANNERBROAD_LENOVO
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.SCANNERDATA
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.SWEEP_RECEIVE_BROADCAST_NAME
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.SWEEP_SEND_BROADCAST_ACTION_NEUSOFT
import com.cxz.kotlin.baselibs.receiver.BroadcastConst.SWEEP_SEND_BROADCAST_ACTION_SYY

class ScannerBroadcastReceiver : BroadcastReceiver() {


    private var listener: OnScannerResultListener? = null

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            SWEEP_RECEIVE_BROADCAST_NAME ->
                listener?.scannerData(intent.extras?.getString(BARCODE))
            SCANNERBROAD ->
                listener?.scannerData(intent.extras?.getString(SCANNERDATA))
            SCANNERBROAD_LENOVO ->
                listener?.scannerData(intent.extras?.getString(BORCODE_VALUE))
            SWEEP_SEND_BROADCAST_ACTION_SYY ->
                listener?.scannerData(intent.extras?.getString(BARCODE_RESULT))
            SWEEP_SEND_BROADCAST_ACTION_NEUSOFT ->
                listener?.scannerData(intent.extras?.getString(NEUSOFT))
            DECODE_DATA ->
                listener?.scannerData(intent.extras?.getString(BAR_CODE))
            DECODE_DATA_JY ->
                listener?.scannerData(intent.extras?.getString(BAR_CODE_RESULT))
            DECODE_ACTION_HNW ->
                listener?.scannerData(intent.extras?.getString(BAR_CODE_HNW))
            DECODE_ACTION_XDL ->
                listener?.scannerData(intent.extras?.getString(BAR_CODE_XDL))
            else -> {
            }
        }

    }


    fun setOnScannerResultListener(scannerListener: OnScannerResultListener) {
        this.listener = scannerListener
    }


}