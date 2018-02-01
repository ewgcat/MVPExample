package com.yijian.person.share;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hengte.sharelib.dialog.ShareDialog;
import com.yijian.person.R;
import com.yijian.person.viewmodel.base.SimpleActivity;



/**
 * Created by lishuaihua on 2018/2/1.
 */

public class ShareActivity extends SimpleActivity {



    private void share(String url, String title, String text, String imgurl) {

        ShareDialog.getInstance().setArgs(url, title, text, imgurl);

        ShareDialog.getInstance().show(getSupportFragmentManager(), "share");

    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initEventAndData() {

    }
}
