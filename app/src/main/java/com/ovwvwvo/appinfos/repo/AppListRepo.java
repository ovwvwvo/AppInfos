package com.ovwvwvo.appinfos.repo;

import com.ovwvwvo.appinfos.model.AppInfoModel;

import java.util.List;

import rx.Observable;

/**
 * Copyright ©2017 by rawer
 */

public interface AppListRepo {

    Observable<List<AppInfoModel>> getMyAppList();

    Observable<List<AppInfoModel>> getSystemAppList();

}
