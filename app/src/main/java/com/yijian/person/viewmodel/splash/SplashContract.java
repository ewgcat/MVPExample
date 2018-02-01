package com.yijian.person.viewmodel.splash;


import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yijian.person.model.bean.SplashBean;
import com.yijian.person.viewmodel.base.BasePresenter;
import com.yijian.person.viewmodel.base.BaseView;
import com.yijian.person.viewmodel.main.contract.MainContract;

public interface SplashContract {

    interface View extends BaseView {

        void showSplashView(SplashBean splashBean);
        void jumpToMain();

    }

    interface  Presenter extends BasePresenter<SplashContract.View> {


        void getSplashInfo();



    }
}
