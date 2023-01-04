package com.cxz.kotlin.baselibs.receiver

object BroadcastConst {
    /**
     * 傅立叶 F750,F760（Andoid5.1/Andriod7.1，Android7.1）
     */
    const val SWEEP_RECEIVE_BROADCAST_NAME = "com.barcode.sendBroadcast"

    /**
     * 接收广播的标志，注册时使用,适配不是全屏有键盘的PDA的广播
     */
    const val SCANNERBROAD = "com.android.server.scannerservice.hhbroadcast"

    /**
     * 适配联想PDA的广播
     */
    const val SCANNERBROAD_LENOVO = "action.barcode.reader.value"

    /**
     * 三医院PDA广播action
     */
    const val SWEEP_SEND_BROADCAST_ACTION_SYY = "android.intent.action.BARCODEDATA"

    /**
     * PDA的广播
     */
    const val SWEEP_SEND_BROADCAST_ACTION_NEUSOFT = "com.neusoft.action.scanner.start"

    /**
     * 三台县人民医院新pda
     */
    const val DECODE_DATA = "android.intent.ACTION_DECODE_DATA"

    /**
     * 井研pda
     */
    const val DECODE_DATA_JY = "android.intent.action.SCANRESULT"

    /**
     * 霍尼韦尔 PDA  测试新设备
     */
    const val DECODE_ACTION_HNW = "scan.rcv.message"

    /**
     * 新大陆pda
     */
    const val DECODE_ACTION_XDL = "nlscan.action.SCANNER_RESULT"






//    ————————————————————————————————————————————————————————————————————————

    const val BARCODE = "BARCODE"
    const val SCANNERDATA = "scannerdata"
    const val BORCODE_VALUE = "borcode_value"
    const val BARCODE_RESULT = "barcode_result"
    const val NEUSOFT = "com.neusoft.action.scanner.read"
    const val BAR_CODE = "barcode_string"
    const val BAR_CODE_RESULT = "value"
    const val BAR_CODE_HNW = "barcodeData"
    const val BAR_CODE_XDL = "SCAN_BARCODE1"




}