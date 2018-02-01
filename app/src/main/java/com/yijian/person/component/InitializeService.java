package com.yijian.person.component;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.yijian.person.application.CustomApplication;
import com.yijian.person.model.db.DBManager;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.yijian.person.util.Logger;
import com.yijian.person.widget.AppBlockCanaryContext;


public class InitializeService extends IntentService {

    private static final String ACTION_INIT = "initApplication";
    private static final String TAG = InitializeService.class.getSimpleName();

    public InitializeService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                initApplication();
            }
        }
    }

    private void initApplication() {
        //初始化数据库
        DBManager.init(this);

        //初始化x5内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("QbSdk", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

        //初始化内存泄漏检测
        LeakCanary.install(CustomApplication.getInstance());

        //初始化过度绘制检测
        BlockCanary.install(getApplicationContext(), new AppBlockCanaryContext()).start();

    }


}
