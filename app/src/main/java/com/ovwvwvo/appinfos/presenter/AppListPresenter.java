package com.ovwvwvo.appinfos.presenter;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.adapter.HomeAdapter;
import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.appinfos.repo.AppListRepo;
import com.ovwvwvo.appinfos.repoImpl.AppListRepoImpl;
import com.ovwvwvo.appinfos.view.AppListView;
import com.ovwvwvo.common.presenter.BasePresenter;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Copyright ©2017 by rawer
 */

public class AppListPresenter extends BasePresenter {

    private AppListView appListView;
    private AppListRepo appListRepo;

    public AppListPresenter(AppListView appListView) {
        this.appListView = appListView;
        this.appListRepo = new AppListRepoImpl();
    }

    public void getAppList(int position) {
        if (position == HomeAdapter.ME)
            getMyAppList();
        else getSystemAppList();
    }

    private void getSystemAppList() {
        appListRepo.getSystemAppList()
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
