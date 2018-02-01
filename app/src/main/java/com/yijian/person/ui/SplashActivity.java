package com.yijian.person.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yijian.person.R;
import com.yijian.person.model.bean.SplashBean;
import com.yijian.person.rx.RxUtil;
import com.yijian.person.util.ImageLoader;
import com.yijian.person.util.NotificationsUtil;
import com.yijian.person.viewmodel.base.BaseActivity;
import com.yijian.person.viewmodel.splash.SplashContract;
import com.yijian.person.viewmodel.splash.SplashPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;


public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    String[] permissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private int index = 0;


    @BindView(R.id.iv_splash_bg)
    ImageView ivSplashBg;
    @BindView(R.id.tv_splash_author)
    TextView tvSplashAuthor;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEventAndData() {
        mPresenter.getSplashInfo();

    }

    public void jumpToMain() {
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void checkPremession() {
        initRxPermissions(index, permissions);
    }


    @Override
    public void showSplashView(SplashBean splashBean) {
        ImageLoader.load(this, splashBean.getImg(), ivSplashBg);
        ivSplashBg.animate().scaleX(1.12f).scaleY(1.12f).setDuration(2000).setStartDelay(100).start();
        tvSplashAuthor.setText(splashBean.getText());
    }





    /**
     * 运行时请求权限
     */
    private void initRxPermissions(int i, String[] permissions) {
        RxPermissions rxPermissions = new RxPermissions(this);
        Observable.timer(200, TimeUnit.MILLISECONDS)
                .compose(rxPermissions.ensureEach(
                        permissions[i]
                ))
                .compose(RxUtil.rxObservableSchedulerHelper())
                .subscribe(permission -> {
                    index = i + 1;
                    if (permissions.length > index) {
                        initRxPermissions(index, permissions);
                    } else {
                        String msg = checkPermissions();
                        if (!TextUtils.isEmpty(msg) && msg.contains("【通知与状态栏权限】")) {
                            requestPermissions("    【通知与状态栏权限】\n");
                        } else {
                            jumpToMain();
                        }
                    }
                });
    }

    /**
     * 请求通知权限  100
     */
    private void requestPermissions(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this)
                .setTitle("获取必要权限")
                .setMessage(msg)
                .setPositiveButton("立即获取", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 100);
                })
                .setNegativeButton("拒绝", (dialog, which) -> {
                    jumpToMain();
                }).create();

        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    private String checkPermissions() {
        String msg = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !NotificationsUtil.isNotificationEnabled(this)) {
            msg = "    【通知与状态栏权限】\n";
        }
        return msg;
    }





    /**
     * 请求权限回调
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                String msg = checkPermissions();
                if (!TextUtils.isEmpty(msg)) {
                    Toast.makeText(this, "缺少" + msg, Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

                break;
        }
    }

    protected long mExitTime;

    /**
     * 监听按键的点击
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if ((secondTime - mExitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次返回键退出程序!", Toast.LENGTH_SHORT).show();
                mExitTime = secondTime;
                return true;
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
        return true;
    }


}
