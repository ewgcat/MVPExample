package com.yijian.person.di.component;

import android.app.Activity;


import com.yijian.person.di.module.FragmentModule;
import com.yijian.person.di.scope.FragmentScope;
import com.yijian.person.ui.WorkFragment;

import dagger.Component;



@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(WorkFragment workFragment);


}
