package com.yijian.person.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhengzhe on 15-10-25.
 */
public class SystemInfoUtil
{
    private static final String TAG = "NameNotFoundException";

    /**
     * 获得应用版本号
     * @param app
     * @return
     */
    public static String getAppVersion(Context app) {
        if(app == null) return null;
        return getAppVersion(app, app.getPackageName());
    }

    /**
     * 从Manifest中获得应用版本号
     * @param app
     * @param packageName
     * @return
     */
    public static String getAppVersion(Context app,String packageName)
    {
        if(app == null) return null;
        String result = "0";
        PackageInfo packInfo = getPackageInfo(app, packageName);
        if(packInfo!=null)
        {
            result = packInfo.versionName+ " "+packInfo.versionCode;
        }
        return result;
    }

    /**
     * 版本号
     * @param app
     * @return
     */
    public static int getAppVersionCode(Context app)
    {
        int result = 0;
        PackageInfo packInfo = getPackageInfo(app);
        if(packInfo!=null)
        {
            result = packInfo.versionCode;
        }
        return result;
    }

    /**
     * 应用名称
     * @param app
     * @return
     */
    public static String getAppName(Context app)
    {
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        return getAppName(app, app.getPackageName());
    }

    /**
     * 应用名称
     * @param app
     * @param packageName
     * @return
     */
    public static String getAppName(Context app,String packageName)
    {
        String result = "";
        PackageInfo packInfo = getPackageInfo(app, packageName);
        if(packInfo!=null)
        {
            result = packInfo.packageName;
        }
        return result;
    }

    protected static PackageInfo getPackageInfo(Context app)
    {
        if(app == null) return null;
        return getPackageInfo(app, app.getPackageName());
    }

    /**
     * 获取当前程序正在运行的栈顶Activity
     * @param context
     * @return
     */
    public static String getTopActivityClassName(Context context)
    {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cName = am.getRunningTasks(1).get(0).topActivity;

        return cName.getClassName();
    }

    protected static PackageInfo getPackageInfo(Context app, String packageName)
    {
        if(app == null) return null;
        PackageManager packageManager = app.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(packageName,0);
        } catch (PackageManager.NameNotFoundException e) {
            //Logger.w(TAG, "getPackageInfo NameNotFoundException");
        }
        return packInfo;
    }

    /**
     * 系统版本
     * @return
     */
    public static String getSystemVersion()
    {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 系统版本，整数
     * @return
     */
    public static int getSystemVersionInt()
    {
        return android.os.Build.VERSION.SDK_INT;
    }

    //imei,设备唯一id(非手机设备和部分手机获取不到)
    public static String getIMEI(Context app)
    {
        if(app == null) return null;
        TelephonyManager mgr = (TelephonyManager)app.getSystemService(Context.TELEPHONY_SERVICE);
        return mgr.getDeviceId();
    }
    //ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被wipe后该数重置.传闻2.2上经常失效
    public static String getAndroid_ID(Context app)
    {
        if(app == null) return null;
        return Settings.Secure.getString(app.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
    //设备唯一id,暂时使用imei
    public static String getUniqueDeviceID(Context app)
    {
        if(app == null) return null;
        return getIMEI(app)+"_"+getAndroid_ID(app);
    }
    //屏幕尺寸
    public static int getDevicePixelWidth(Context app)
    {
        WindowManager mgr = (WindowManager)app.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        mgr.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getDevicePixelHeight(Context app)
    {
        WindowManager mgr = (WindowManager)app.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        mgr.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static String getDeviceScreenSize(Context app)
    {
        WindowManager mgr = (WindowManager)app.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        mgr.getDefaultDisplay().getMetrics(metrics);
        return String.format("%sX%s", metrics.widthPixels, metrics.heightPixels);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */
    public static int dip2px(Context app, float dpValue)
    {
        if(app == null)
            return 0;
        final float scale = getDeviceDisplayDensity(app);
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */
    public static int px2dip(Context app, float pxValue)
    {
        if(app == null)
            return 0;
        final float scale = getDeviceDisplayDensity(app);
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素) 
     * @param app
     * @param spValue
     * @return
     */
    public static int sp2px(Context app, float spValue)
    {
        if (app == null)
            return 0;
        final float fontScale = app.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 sp 
     * @param app
     * @param pxValue
     * @return
     */
    public static int px2sp(Context app, float pxValue)
    {
        if (app == null)
            return 0;
        final float fontScale = app.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 屏幕密度
     * @return
     */
    public static float getDeviceDisplayDensity(Context app)
    {
        if(app == null)
            return 0;
        return app.getResources().getDisplayMetrics().density;
    }

    /**
     * 屏幕密度
     * @return
     */
    public static float getDeviceDisplayDensityDpi(Context app)
    {
        if(app == null)
            return 0;
        return app.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 屏幕尺寸
     * @param app
     * @return
     */
    public static float getDeviceDisplayInches(Context app)
    {
        DisplayMetrics dm = app.getResources().getDisplayMetrics();
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
        double screenInches = Math.sqrt(x+y);
        return (float) screenInches;
    }

    /**
     * 获取手机型号
     * @return
     */
    public static String getDeviceModel()
    {
        return android.os.Build.MODEL;
    }

    /**
     * 手机名称
     * @return
     */
    public static String getDeviceName()
    {
        return android.os.Build.PRODUCT;
    }

    //获取系统语言
    public static String getSystemLanguage()
    {
        return Locale.getDefault().getLanguage();
    }
    

    /**
     * 是否SD卡已经挂载
     * @return
     */
    public static boolean isSDCardMounted()
    {
        boolean result = false;
        String stateString = Environment.getExternalStorageState();
        result = Environment.MEDIA_MOUNTED.equals(stateString);
        return result;
    }

    /**
     * 图片目录
     * @return
     */
    public static String getEnvironmentDirectoryPictures()
    {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
    }

    /**
     * DCIM目录
     * @return
     */
    public static String getEnvironmentDirectoryDCIM()
    {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
    }

    /**
     * App可用内存
     * @return
     */
    public long getFreeMemory()
    {
        return (Runtime.getRuntime().maxMemory() - (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
    }

    protected static ArrayList<WeakReference<Object>> objectCheckerList = new ArrayList<WeakReference<Object>>();

    public static void addObjectToGCObserver(Object object)
    {
        if (object != null)
        {
            boolean found = false;
            for (WeakReference<Object> ref : objectCheckerList)
            {
                if (ref.get() == object)
                {
                    found = true;
                }
            }
            if (!found)
            {
                String msg = String.format("add [%s], current Lived Object = %s", object.getClass().getName(), String.valueOf(objectCheckerList.size()));
                //Logger.d("[CG Observer]", msg);
                objectCheckerList.add(new WeakReference<Object>(object));
            }
        }
    }

    public static boolean hasObjectGoneToGC(Object object)
    {
        boolean ret = false;

        if (object != null)
        {
            for (int i = objectCheckerList.size() - 1; i >= 0; --i)
            {
                WeakReference<Object> ref = objectCheckerList.get(i);
                if (ref.get() == object)
                {
                    ret = true;
                }
                if (ref.get() == null)
                {
                    String msg = String.format("GC collected, current Lived Object = %s", String.valueOf(objectCheckerList.size()));
                    //Logger.d("[CG Observer]", msg);
                    objectCheckerList.remove(i);
                }
            }
        }

        return ret;
    }

    public static void printActivityStack(String tag,Context context,int maxnum)
    {
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE) ;
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(maxnum);
        if(runningTaskInfos != null)
        {
            for (int i = 0; i < runningTaskInfos.size(); i++)
            {
                ActivityManager.RunningTaskInfo task = runningTaskInfos.get(i);
                //Logger.e(tag, "所在包:"+task.topActivity.getPackageName());
                //Logger.i(tag, task.numActivities + "个Activity" + " 顶部为:" + task.topActivity.toString() + " 底部为:" + task.baseActivity.toString());
            }
        }

    }

    /**
     * 设置支持的最大的Bitmap宽度
     * @param canvas
     * @return
     */
    @SuppressLint("NewApi")
    public static int getDeviceMaxBitmapWidth(Canvas canvas)
    {
        if(getSystemVersionInt() >= 14)
        {
            return canvas.getMaximumBitmapWidth();
        }
        else
        {
            return 0;
        }
    }

    /**
     * 设置支持的最大的Bitmap高度
     * @param canvas
     * @return
     */
    @SuppressLint("NewApi")
    public static int getDeviceMaxBitmapHeight(Canvas canvas)
    {
        if(getSystemVersionInt() >= 14)
        {
            return canvas.getMaximumBitmapHeight();
        }
        else
        {
            return 0;
        }
    }

    /**
     * 虚拟机最大可用的内存
     * @return
     */
    public static long getRuntimeMaxMemory()
    {
        return Runtime.getRuntime().maxMemory();
    }
    /**
     * 虚拟机已经占用的总内存
     * @return
     */
    public static long getRuntimeTotalMemory()
    {
        return Runtime.getRuntime().totalMemory();
    }
    /**
     * 虚拟机已经占用但还空闲的内存
     * @return
     */
    public static long getRuntimeFreeMemory()
    {
        return Runtime.getRuntime().freeMemory();
    }
    /**
     * 虚拟机真正占用的内存
     * 相当于：getRuntimeTotalMemory() - getRuntimeFreeMemory()
     * @return
     */
    public static long getRuntimeReallyUsedMemory()
    {
        return getRuntimeTotalMemory() - getRuntimeFreeMemory();
    }
}
