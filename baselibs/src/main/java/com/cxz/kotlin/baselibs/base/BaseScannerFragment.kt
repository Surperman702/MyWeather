package com.cxz.kotlin.baselibs.base

import androidx.annotation.LayoutRes
import com.cxz.kotlin.baselibs.constance.ScanType
import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView
import com.cxz.kotlin.baselibs.receiver.OnScannerResultListener
import com.cxz.kotlin.baselibs.receiver.ScannerBroadcastReceiver
import com.cxz.kotlin.baselibs.utils.QrCodeUtils

abstract class BaseScannerFragment<in V : IView, P : IPresenter<V>>(@LayoutRes layoutId: Int) :
    BaseMvpFragment<V, P>(layoutId) {


    private var scannerReceiver: ScannerBroadcastReceiver? = null

    override fun onResume() {
        super.onResume()
        registerScannerBroadcastReceiver()
    }

    private fun registerScannerBroadcastReceiver() {
        val intentFilter = ScanType.getFilter()
        scannerReceiver = ScannerBroadcastReceiver()
        scannerReceiver?.setOnScannerResultListener(scanResultListener)
        context?.registerReceiver(scannerReceiver, intentFilter)
    }


    override fun onStop() {
        super.onStop()
        scannerReceiver?.let {
            context?.unregisterReceiver(it)
        }
    }


    private val scanResultListener = object : OnScannerResultListener {
        override fun scannerData(data: String?) {
            if (data.isNullOrBlank()) {
                showMsg("未识别到二维码信息")
            } else {
                when (QrCodeUtils.getInstance().matchType(data)) {
                    QrCodeUtils.QrType.JY_CODE -> onJyQrCodeResult(data)
                    QrCodeUtils.QrType.WD_CODE -> onWdQrCodeResult(data)
                    QrCodeUtils.QrType.YZ_CODE -> onYzQrCodeResult(data)
                    else -> null
                }

            }
        }
    }

    /**
     * 医嘱的编号
     */
    open fun onYzQrCodeResult(scanData: String) {}

    /**
     * 腕带的编号
     */
    open fun onWdQrCodeResult(scanData: String) {}

    /**
     * 检验医嘱的编号
     */
    open fun onJyQrCodeResult(scanData: String) {}

}