package com.yijian.person.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yijian.person.R;
import com.yijian.person.viewmodel.base.BaseActivity;
import com.yijian.person.viewmodel.main.contract.MainContract;
import com.yijian.person.viewmodel.main.presenter.MainPresenter;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {

    }

    protected void initInject() {
        getActivityComponent().inject(this);
    }

}
