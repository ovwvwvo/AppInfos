package com.ovwvwvo.appinfos.presenter;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.appinfos.repo.AppListRepo;
import com.ovwvwvo.appinfos.repoImpl.AppListRepoImpi;
import com.ovwvwvo.appinfos.view.AppListView;
import com.ovwvwvo.common.presenter.BasePresenter;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Copyright ©2017 by ovwvwvo
 */

public class AppListPresenter extends BasePresenter {

    private AppListView appListView;
    private AppListRepo appListRepo;

    public AppListPresenter(AppListView appListView) {
        this.appListView = appListView;
        this.appListRepo = new AppListRepoImpi();
    }

    public void getAppList(int position) {
        if (position == 1)
            getMyAppList();
        else getAllAppList();
    }

    private void getAllAppList() {
        appListRepo.getAllAppList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<AppInfoModel>>() {
                @Override
                public void call(List<AppInfoModel> appInfoModels) {
                    appListView.loadDataSuccess(appInfoModels);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    appListView.showToast(R.string.data_error);
                }
            });
    }

    private void getMyAppList() {
        appListRepo.getMyAppList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<AppInfoModel>>() {
                @Override
                public void call(List<AppInfoModel> appInfoModels) {
                    appListView.loadDataSuccess(appInfoModels);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    appListView.showToast(R.string.data_error);
                }
            });
    }


}
