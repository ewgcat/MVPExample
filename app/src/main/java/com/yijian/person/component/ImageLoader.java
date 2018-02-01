package com.yijian.person.component;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.request.RequestOptions.centerCropTransform;

public class ImageLoader {


    public static void load(Activity activity, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if(!activity.isDestroyed()) {
            RequestOptions options = centerCropTransform()
                    .priority(Priority.HIGH).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .transform(new GlideCircleTransform());
            Glide.with(activity).load(url).apply(options).into(iv);
        }


    }

    public static void loadAll(Context context, String url, ImageView iv) {    //不缓存，全部从网络加载
        Glide.with(context).load(url).into(iv);
    }

    public static void loadAll(Activity activity, String url, ImageView iv) {    //不缓存，全部从网络加载
        if(!activity.isDestroyed()) {
            RequestOptions options = centerCropTransform()
                    .priority(Priority.HIGH).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(new GlideCircleTransform());
            Glide.with(activity).load(url).apply(options).into(iv);
        }
    }
}
