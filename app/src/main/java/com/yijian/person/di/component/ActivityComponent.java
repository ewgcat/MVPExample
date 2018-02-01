package com.yijian.person.di.component;

import android.app.Activity;

import com.yijian.person.di.scope.ActivityScope;
import com.yijian.person.di.module.ActivityModule;
import com.yijian.person.ui.MainActivity;
import com.yijian.person.ui.SplashActivity;


import dagger.Component;



@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();



    void inject(MainActivity mainActivity);
    void inject(SplashActivity splashActivity);

}
