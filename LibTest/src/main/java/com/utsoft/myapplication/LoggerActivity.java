package com.utsoft.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * logger 简单使用
 */
public class LoggerActivity extends Activity {

    public final String TAG = "LoggerActivity";
    public final String XMLString = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" +
            "<note><to>George</to><from>John</from><heading>Reminder</heading><body>" +
            "Don't forget the meeting!</body></note>\n" +
            "               ";
//    public final String JSONString = "{\"weatherinfo\":{\"city\":\"北京\",\"cityid\":\"1010" +
//            "10100\",\"temp1\":\"-2℃\",\"temp2\":\"16℃\",\"weather\":\"晴\",\"img1\":\"n0.gif\"" +
//            ",\"img2\":\"d0.gif\",\"ptime\":\"18:00\"}}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.looger_activity);
        // 初始化一般放在 Application中 ，logLevel函数的参数通过设置枚举变量可以打开关闭日志
        Logger
                .init("utoosoft")                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(2);          // default 0
//                .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter


        Log.d(TAG, "hello");
        Logger.d("hello");
        Logger.d("hello %s %d", "world", 5);   // String.format
        Logger.d("hello");
        Logger.e("hello");
        Logger.w("hello");
        Logger.v("hello");
        Logger.wtf("hello");
        Logger.xml(XMLString);
//        Logger.json(JSONString);
    }
}
