package com.ovwvwvo.appinfos.repoImpl;

import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.appinfos.repo.SearchRepo;
import com.ovwvwvo.appinfos.util.AppInfoUtil;
import com.ovwvwvo.jkit.AppWrapper;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Copyright ©2017 by rawer
 */

public class SearchRepoImpl implements SearchRepo {

    @Override
    public Observable<List<AppInfoModel>> getAllAppList() {
        return Observable.fromCallable(new Callable<List<AppInfoModel>>() {
            @Override
            public List<AppInfoModel> call() throws Exception {
                return AppInfoUtil.getAllAppInfos(AppWrapper.getInstance().getAppContext());
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<AppInfoModel>> filterApps(final String word, List<AppInfoModel> models) {
        return Observable.from(models).filter(new Func1<AppInfoModel, Boolean>() {
            @Override
            public Boolean call(AppInfoModel appInfoModel) {
                String source = (appInfoModel.getAppName() + appInfoModel.getPackageName()).replace(" ", "").toLowerCase();
                return source.contains((word.replace(" ", "").toLowerCase()));
            }
        }).toList().subscribeOn(Schedulers.io());
    }

}
