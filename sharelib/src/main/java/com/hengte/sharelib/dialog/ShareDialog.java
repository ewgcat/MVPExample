package com.hengte.sharelib.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengte.sharelib.CustomShareListener;
import com.hengte.sharelib.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;


/**
 * Created by Administrator on 2017/5/10 0010.
 */

public class ShareDialog extends DialogFragment {
    LinearLayout llWeixin;
    LinearLayout llFriends;
    LinearLayout llQQ;
    LinearLayout llZone;
    TextView tvCancel;

    String webUrl;
    String tittle;
    String content;
    String thumbUrl;
    int thumbId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Window window = getDialog().getWindow();
        View view = inflater.inflate(R.layout.dialog_share, ((ViewGroup) window.findViewById(android.R.id.content)), false);//需要用android.R.id.content这个view
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//注意此处
        window.setLayout(-1, -1);//这2行,和上面的一样,注意顺序就行;
        findView(view);
        return view;
    }

    private void findView(View v) {
        llWeixin= (LinearLayout) v.findViewById(R.id.ll_weixin);
        llFriends= (LinearLayout) v.findViewById(R.id.ll_friends);
        llQQ= (LinearLayout) v.findViewById(R.id.ll_qq);
        llZone= (LinearLayout) v.findViewById(R.id.ll_zone);
        tvCancel= (TextView) v.findViewById(R.id.tv_cancel);
        llWeixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.WEIXIN);
                dismiss();
            }
        });
        llFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
            }
        });
        llQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.QQ);
                dismiss();
            }
        });
        llZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(SHARE_MEDIA.QZONE);
                dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareDialog.this.dismiss();
            }
        });
    }




    private void share(SHARE_MEDIA share_media){

        UMWeb web = new UMWeb(webUrl);
        web.setTitle(tittle);    //标题
        if(thumbId!=0){
            UMImage thumb=new UMImage(getActivity(),thumbId);
            web.setThumb(thumb);    //缩略图
        }
        if(thumbUrl!=null&& !TextUtils.isEmpty(thumbUrl)){
            UMImage thumb=new UMImage(getActivity(),thumbUrl);
            web.setThumb(thumb);    //缩略图
        }






        web.setDescription(content);//描述

        new ShareAction(getActivity()).withMedia(web)
                .setPlatform(share_media)
                .setCallback(new CustomShareListener(getActivity()))
                .share();
    }


    private Dialog.OnClickListener onClickListener;

    public void setOnClickListener(Dialog.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private static ShareDialog choosePhotoDialog;


    public synchronized static ShareDialog getInstance(){
        if(choosePhotoDialog==null){
            choosePhotoDialog=new ShareDialog();
        }
        return choosePhotoDialog;
    }



    public void setArgs(String webUrl,String tittle,String content,String thumbUrl){
        this.webUrl=webUrl;
        this.tittle=tittle;
        this.content=content;
        this.thumbUrl=thumbUrl;
    }


    public void setArgs(String webUrl,String tittle,String content,int thumbId){
        this.webUrl=webUrl;
        this.tittle=tittle;
        this.content=content;
        this.thumbId=thumbId;
    }






}
