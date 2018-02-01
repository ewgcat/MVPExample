package com.yijian.person.viewmodel.splash;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yijian.person.model.DataManager;
import com.yijian.person.model.bean.SplashBean;
import com.yijian.person.model.http.response.BaseResponse;
import com.yijian.person.util.RxUtil;
import com.yijian.person.viewmodel.base.RxPresenter;
import com.yijian.person.viewmodel.main.contract.MainContract;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;



public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter{


    private static final int COUNT_DOWN_TIME = 2200;

    private DataManager mDataManager;

    @Inject
    public SplashPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(SplashContract.View view) {
        super.attachView(view);
    }


    @Override
    public void getSplashInfo() {
        addSubscribe(mDataManager.getSplashInfo()
                .compose(RxUtil.<BaseResponse<SplashBean>>rxSchedulerHelper())
                .subscribe(new Consumer<BaseResponse<SplashBean>>() {
                    @Override
                    public void accept(BaseResponse<SplashBean> response) {
                        mView.showSplashView(response.getData());
                        startCountDown();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        mView.jumpToMain();
                    }
                })
        );
    }
    private void startCountDown() {
        addSubscribe(Flowable.timer(COUNT_DOWN_TIME, TimeUnit.MILLISECONDS)
                .compose(RxUtil.<Long>rxSchedulerHelper())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        mView.jumpToMain();
                    }
                })
        );
    }

}
