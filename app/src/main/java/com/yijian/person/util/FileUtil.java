package com.yijian.person.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * desc:
 *
 * @author:nickming date:2016/1/26
 * time: 15:47
 * e-mail：962570483@qq.com
 */
public class FileUtil {
    private final static String TAG = "FileUtil";

    private final static String PIC_DIR = File.separator + "baolimanager" + File.separator;

    public static String getPicPath(Context context) {
        String path = Environment.getExternalStorageDirectory().getPath() + PIC_DIR;
        Log.i(TAG, "getPicPath:" + path);
        return path;
    }

    public static String getExternalCachePath(Context context) {
        String path = context.getExternalCacheDir() + PIC_DIR;
        Log.i(TAG, "getExternalCachePath:" + path);
        return path;
    }


    /**
     * 清理缓存
     */
    public static void clearCach(Context context) {
//        String cachDir1 = FileUtil.getPicPath(getActivity());
//        FileUtil.deleteFile(cachDir1);
        String cachDir2 = context.getExternalCacheDir() + "/head/upload";
        FileUtil.deleteFile(cachDir2);
        String cachDir3 = FileUtil.getExternalCachePath(context);
        FileUtil.deleteFile(cachDir3);
    }

    /**
     * 计算缓存
     *
     * @return
     */
    public static long getCachSize(Context context) {
//        File file1 = new File(FileUtil.getPicPath(getActivity()));//巡检任务产生的图片不计算进去
//        if (!file1.exists()) {
//            file1.mkdirs();
//        }
        File file2 = new File(FileUtil.getExternalCachePath(context));
        File file3 = new File(context.getExternalCacheDir() + "/head/upload");

        if (!file2.exists()) {
            file2.mkdirs();
        }
        if (!file3.exists()) {
            file3.mkdirs();
        }

        return FileUtil.getFolderSize(file2) + FileUtil.getFolderSize(file3);
    }


    public static void deleteFile(String uri) {
        File file = new File(uri);
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
                Logger.i("FileUtil", "deleteFile :" + file.getPath());
            } else if (file.isDirectory()) { // 否则如果它是一个目录

                File[] filelist = file.listFiles();// 声明目录下所有的文件 files[];
                if (filelist == null) {
                    return;
                }
                for (int i = 0; i < filelist.length; i++) { // 遍历目录下所有的文件
                    FileUtil.deleteFile(filelist[i].getAbsolutePath()); // 把每个文件 用这个方法进行迭代
                }
                file.delete();
            }
        } else {
            Logger.i("FileUtil", "文件不存在！" + "\n");
        }
    }

    /**
     * 删除图片缩略图，和原图
     *
     * @param files
     */
    public static void deleteFiles(List<File> files) {
        if (files != null) {
            try {
                for (File file : files) {
                    if (file != null && file.exists()) {
                        file.delete();
                        String smallPath = file.getAbsolutePath();
                        String bigPath = smallPath.replace(PIC_DIR + "small_", PIC_DIR);
                        File bigFile = new File(bigPath);
                        if (bigFile.exists()) {
                            bigFile.delete();
                        }
                    }
                }
            } catch (Exception e) {
                //Logger.d(TAG,e.toString());
            }
        }
    }


    public static boolean createHeadIconFile(String imageDir, String name) {

        //判断目录是否存在，不存在则创建该目录
        File dirFile = new File(imageDir);
        if (!dirFile.exists()) {
            boolean mkdirs = dirFile.mkdirs();
        }
        //文件是否创建成功
        boolean isFileCreateSuccess = false;

        String imagePath = imageDir + "/" + name;


        //判断文件是否存在，不存在则创建该文件
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            try {
                isFileCreateSuccess = imageFile.createNewFile();//创建文件
                Logger.i(TAG, "imageFile 不存在，isFileCreateSuccess=" + isFileCreateSuccess);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            isFileCreateSuccess = true;
        }

        return isFileCreateSuccess;

    }


    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {
        long size = 0;
        if (file.exists()) {
            try {
                File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                    }
                }
            } catch (Exception e) {
                Logger.i(TAG, e.toString());
            }
        }

        return size;
    }


}
