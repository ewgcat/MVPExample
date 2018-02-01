package com.yijian.person.viewmodel.main.presenter;

import android.Manifest;

import com.yijian.person.model.DataManager;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yijian.person.viewmodel.base.RxPresenter;
import com.yijian.person.viewmodel.main.contract.MainContract;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;


public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter{

    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
    }



}
