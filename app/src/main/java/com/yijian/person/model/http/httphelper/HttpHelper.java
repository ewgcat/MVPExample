package com.yijian.person.model.http.httphelper;


import com.yijian.person.model.bean.SplashBean;
import com.yijian.person.model.bean.User;
import com.yijian.person.model.http.response.BaseResponse;

import io.reactivex.Flowable;

public interface HttpHelper {

    //启动
    Flowable<BaseResponse<SplashBean>> getSplashInfo();

    //登录
    Flowable<BaseResponse<User>> login(String userName, String password);



}
