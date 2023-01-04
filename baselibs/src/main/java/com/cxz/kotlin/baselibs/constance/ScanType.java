package com.cxz.kotlin.baselibs.constance;

import android.content.IntentFilter;

/**
 * <pre>
 *     author : 王蒙
 *     e-mail : 840185240@qq.com
 *     time   : 2021/03/01
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ScanType {
    public class Action {
        /**
         * 傅立叶 F750,F760（Andoid5.1/Andriod7.1，Android7.1）
         */
        public static final String SWEEP_RECEIVE_BROADCAST_NAME = "com.barcode.sendBroadcast";
        /**
         * 接收广播的标志，注册时使用,适配不是全屏有键盘的PDA的广播
         */
        public static final String SCANNERBROAD = "com.android.server.scannerservice.hhbroadcast";
        /**
         * 适配联想PDA的广播
         */
        public static final String SCANNERBROAD_LENOVO = "action.barcode.reader.value";
        /**
         * 三医院PDA广播action
         */
        public static final String SWEEP_SEND_BROADCAST_ACTION_SYY = "android.intent.action.BARCODEDATA";
        /**
         * PDA的广播
         */
        public static final String SWEEP_SEND_BROADCAST_ACTION_NEUSOFT = "com.neusoft.action.scanner.start";

        /**
         * 三台县人民医院新pda
         */
        public static final String DECODE_DATA = "android.intent.ACTION_DECODE_DATA";
        /**
         * 井研pda
         */
        public static final String DECODE_DATA_JY = "android.intent.action.SCANRESULT";

        /**
         * 霍尼韦尔 PDA  测试新设备
         */
        public static final String DECODE_ACTION_HNW = "scan.rcv.message";
        /**
         * 新大陆pda
         */
        public static final String DECODE_ACTION_XDL = "nlscan.action.SCANNER_RESULT";

    }

    public class ReadKey {
        public static final String BARCODE = "BARCODE";
        public static final String SCANNERDATA = "scannerdata";
        public static final String BORCODE_VALUE = "borcode_value";
        public static final String BARCODE_RESULT = "barcode_result";
        public static final String NEUSOFT = "com.neusoft.action.scanner.read";
        public static final String BAR_CODE = "barcode_string";
        public static final String BAR_CODE_RESULT = "value";
        public static final String BAR_CODE_HNW = "barcodeData";
        public static final String BAR_CODE_XDL = "SCAN_BARCODE1";
    }

    public static IntentFilter getFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Action.SWEEP_RECEIVE_BROADCAST_NAME);
        intentFilter.addAction(Action.SCANNERBROAD);
        intentFilter.addAction(Action.SCANNERBROAD_LENOVO);
        intentFilter.addAction(Action.SWEEP_SEND_BROADCAST_ACTION_SYY);
        intentFilter.addAction(Action.SWEEP_SEND_BROADCAST_ACTION_NEUSOFT);
        intentFilter.addAction(Action.DECODE_DATA);
        intentFilter.addAction(Action.DECODE_DATA_JY);
        intentFilter.addAction(Action.DECODE_ACTION_HNW);
        intentFilter.addAction(Action.DECODE_ACTION_XDL);
        return intentFilter;
    }

}
