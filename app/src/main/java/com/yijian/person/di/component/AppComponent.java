package com.yijian.person.di.component;

import com.yijian.person.application.CustomApplication;
import com.yijian.person.di.module.AppModule;
import com.yijian.person.di.module.HttpModule;
import com.yijian.person.model.DataManager;
import com.yijian.person.model.http.httphelper.RetrofitHelper;
import com.yijian.person.model.prefs.ImplPreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;



@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    CustomApplication getContext();  // 提供App的Context

    DataManager getDataManager(); //数据中心

    RetrofitHelper retrofitHelper();  //提供http的帮助类

    ImplPreferencesHelper preferencesHelper(); //提供sp帮助类
}
