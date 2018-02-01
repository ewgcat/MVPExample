package com.yijian.person.model;

import com.yijian.person.model.bean.SplashBean;
import com.yijian.person.model.bean.User;
import com.yijian.person.model.http.httphelper.HttpHelper;

import com.yijian.person.model.http.response.BaseResponse;
import com.yijian.person.model.prefs.PreferencesHelper;

import io.reactivex.Flowable;


public class DataManager implements HttpHelper, PreferencesHelper {

    HttpHelper mHttpHelper;
    PreferencesHelper mPreferencesHelper;

    public DataManager(HttpHelper httpHelper, PreferencesHelper preferencesHelper) {
        mHttpHelper = httpHelper;
        mPreferencesHelper = preferencesHelper;
    }

    //http 请求
    @Override
    public Flowable<BaseResponse<User>> login(String userName, String password) {
        return null;
    }



    @Override
    public Flowable<BaseResponse<SplashBean>> getSplashInfo() {
        return mHttpHelper.getSplashInfo();
    }

    //首选项
    @Override
    public boolean getNightModeState() {
        return false;
    }

    @Override
    public void setNightModeState(boolean state) {

    }
}
