package com.yijian.person.component;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;

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

        //初始化内存泄漏检测
        LeakCanary.install(CustomApplication.getInstance());

        //初始化过度绘制检测
        BlockCanary.install(getApplicationContext(), new AppBlockCanaryContext()).start();

    }


}
