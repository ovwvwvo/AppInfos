package com.ovwvwvo.appinfos.repo;

import com.ovwvwvo.appinfos.model.AppInfoModel;

import java.util.List;

import rx.Observable;

/**
 * Copyright ©2017 by ovwvwvo
 */

public interface SearchRepo {

    Observable<List<AppInfoModel>> getAllAppList();

    Observable<List<AppInfoModel>> filterApps(String word, List<AppInfoModel> models);
}
