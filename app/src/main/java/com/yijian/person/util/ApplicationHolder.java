package com.yijian.person.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

/**
 * Created by ottozheng on 15/9/11.
 */
public class ApplicationHolder
{
    private static final String TAG = "ApplicationHolder";
    private static Application mApplication = null;

    /**
     * 获得Application
     *
     * @return the mapplication
     */
    public static Application getmApplication()
    {
        if (mApplication == null)
        {
            //Logger.e(TAG, "Global ApplicationContext is null, Please call ApplicationHolder.setmApplication(application) at the onCreate() method of Activity and Application.");
        }
        return mApplication;
    }

    /**
     * 设置Application
     *
     * @param mapplication
     *            the mapplication to set
     */
    public static void setmApplication(Application mapplication)
    {
        if (mapplication == null)
        {
            //Logger.e(TAG, "try to set null application, return.");
            return;
        }

        if (mapplication != mApplication)
        {
            mApplication = mapplication;
        }

    }

    /**
     *判断当前应用程序处于前台还是后台
     */
    public static boolean isApplicationBroughtToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
