package com.ovwvwvo.appinfos.presenter;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.appinfos.repo.SearchRepo;
import com.ovwvwvo.appinfos.repoImpl.SearchRepoImpl;
import com.ovwvwvo.appinfos.view.SearchView;
import com.ovwvwvo.common.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Copyright ©2017 by ovwvwvo
 */

public class SearchPresenter extends BasePresenter {

    private SearchRepo searchRepo;
    private SearchView searchView;
    private List<AppInfoModel> models;

    public SearchPresenter(SearchView searchView) {
        searchRepo = new SearchRepoImpl();
        this.searchView = searchView;
    }

    public void setModels(List<AppInfoModel> models) {
        this.models = new ArrayList<>(models);
    }

    public void getAllAppList() {
        searchView.showProgressBar();
        searchRepo.getAllAppList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<AppInfoModel>>() {
                @Override
                public void call(List<AppInfoModel> appInfoModels) {
                    searchView.dismissProgressBar();
                    searchView.loadDataSuccess(appInfoModels);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    searchView.dismissProgressBar();
                    searchView.showToast(R.string.data_error);
                }
            });
    }

    public void searchApp(final String content) {
        if (models == null || models.size() == 0)
            return;
        searchView.showProgressBar();
        searchRepo.filterApps(content, models)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<AppInfoModel>>() {
                @Override
                public void call(List<AppInfoModel> appInfoModels) {
                    searchView.dismissProgressBar();
                    searchView.onSearchSuccess(appInfoModels);
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    searchView.dismissProgressBar();
                    searchView.showToast(R.string.search_error);
                }
            });
    }
}