package com.yijian.person.di.module;

import com.yijian.person.application.CustomApplication;
import com.yijian.person.model.DataManager;
import com.yijian.person.model.http.httphelper.HttpHelper;
import com.yijian.person.model.http.httphelper.RetrofitHelper;
import com.yijian.person.model.prefs.ImplPreferencesHelper;
import com.yijian.person.model.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;



@Module
public class AppModule {
    private final CustomApplication application;

    public AppModule(CustomApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    CustomApplication provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(ImplPreferencesHelper implPreferencesHelper) {
        return implPreferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, PreferencesHelper preferencesHelper) {
        return new DataManager(httpHelper,  preferencesHelper);
    }
}
