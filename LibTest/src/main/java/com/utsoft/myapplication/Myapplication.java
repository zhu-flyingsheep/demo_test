package com.utsoft.myapplication;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.chad.baserecyclerviewadapterhelper.util.Utils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.utsoft.myapplication.tinker.MyLogImp;
import com.utsoft.myapplication.tinker.SampleApplicationContext;
import com.utsoft.myapplication.tinker.TinkerManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wangzhu on 2017/3/3.
 * func:
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.utsoft.myapplication.Myapplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class Myapplication extends DefaultApplicationLike {
    public Myapplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                         long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime,
                tinkerResultIntent);
    }

    /**
     * install multiDex before install tinker
     * so we don't need to put the tinker lib classes in the main dex
     *
     * @param base
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        SampleApplicationContext.application = getApplication();
        SampleApplicationContext.context = getApplication();
        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    private static Myapplication appContext;

    public static Myapplication getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SampleApplicationContext.application = getApplication();
        appContext = this;
        Utils.init(SampleApplicationContext.application);
        Logger
                .init("utoosoft")                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(2);          // default 0
        Realm.init(SampleApplicationContext.application);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();//3.0 文档更新了。 Builder(Context context) 不是public了
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static long toatal_len;
}
