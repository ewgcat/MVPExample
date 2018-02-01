package com.yijian.person.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * desc:
 *
 * @author:nickming date:2016/4/1
 * time: 10:32
 * e-mail：962570483@qq.com
 */

public class PictureUtil {

    private final static String TAG="PictureUtil";

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {

        File file = new File(filePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int height = options.outHeight;
        int width = options.outWidth;
        //double scale = Math.sqrt((float)file.length()/(40*1024));

        double scale = Math.sqrt(file.length() / (80 * 1024));

        options.outHeight = (int) (height / scale);
        options.outWidth = (int) (width / scale);
        options.inSampleSize = (int) (scale + 0.5);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }





    /**
     * 获取保存图片的目录
     *
     * @return
     */
    public static File getAlbumDir(Context context) {
        File dir = new File(FileUtil.getPicPath(context));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 保存文件到sd上面
     *
     * @param photoBitmap 需要保存的图片
     * @param path        保存的路径
     */
    public static boolean  savePhotoToCach(Bitmap photoBitmap, String path) {
        boolean isSaveSuccessed=false;
        if (checkSDCardAvailable()) {
            Logger.i(TAG,"path="+path);
            File photoFile = new File(path);
            if (!photoFile.exists()){
                photoFile.mkdirs();
            }
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null&&fileOutputStream!=null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
                        isSaveSuccessed=true;

                    }
                }
            } catch (Exception e) {
                if (photoFile!=null){
                    photoFile.delete();
                }
                Logger.i(TAG,e.toString());
            } finally {
                try {
                    if (fileOutputStream!=null){
                        fileOutputStream.close();
                    }
                } catch (Exception e) {
                    Logger.i(TAG,e.toString());
                }
            }
        }
        return isSaveSuccessed;
    }

    /**
     * Check the SD card
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }


    /**
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }


    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            if (path.endsWith(".jpg")||path.endsWith(".Jpeg")||path.endsWith(".JPEG")){
                ExifInterface exifInterface = new ExifInterface(path);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }

        } catch (IOException e) {
            Logger.i(TAG,e.toString());
        }
        return degree;
    }


    /**
     * 修正图片显示
     * @param path
     * @return
     */
    public static Bitmap modifyImageDisplay(String path) {
        Bitmap bitmap=null;
        Bitmap cbitmap=null;
        /**
         * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
         */
        int degree=readPictureDegree( path);

        BitmapFactory.Options opts=new BitmapFactory.Options();//获取缩略图显示到屏幕上

        Boolean loginFromPad = SharePreferenceUtil.getLoginFromBetterPad();
        if (loginFromPad){
            opts.inSampleSize=6;
            cbitmap=BitmapFactory.decodeFile(path,opts);
        }else {
            opts.inSampleSize=1;
            cbitmap=BitmapFactory.decodeFile(path,opts);
        }


        /**
         * 把图片旋转为正的方向
         */
        if (cbitmap!=null){
            bitmap = rotaingImageView(degree, cbitmap);
        }

        return bitmap;
    }

}
