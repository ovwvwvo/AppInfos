package com.ovwvwvo.appinfos.repoImpl;

import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.appinfos.repo.AppListRepo;
import com.ovwvwvo.appinfos.util.AppInfoUtil;
import com.ovwvwvo.jkit.AppWrapper;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Copyright ©2017 by rawer
 */

public class AppListRepoImpl implements AppListRepo {

    @Override
    public Observable<List<AppInfoModel>> getSystemAppList() {
        return Observable.fromCallable(new Callable<List<AppInfoModel>>() {
            @Override
            public List<AppInfoModel> call() throws Exception {
                return AppInfoUtil.getSystemAppInfos(AppWrapper.getInstance().getAppContext());
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<AppInfoModel>> getMyAppList() {
        return Observable.fromCallable(new Callable<List<AppInfoModel>>() {
            @Override
            public List<AppInfoModel> call() throws Exception {
                return AppInfoUtil.getMyAppInfos(AppWrapper.getInstance().getAppContext());
            }
        }).subscribeOn(Schedulers.io());
    }
}
