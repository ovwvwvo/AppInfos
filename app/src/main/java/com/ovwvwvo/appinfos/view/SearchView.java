package com.ovwvwvo.appinfos.view;

import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.common.view.BaseView;

import java.util.List;

/**
 * Copyright ©2017 by rawer
 */

public interface SearchView extends BaseView {

    void loadDataSuccess(List<AppInfoModel> models);

    void onSearchSuccess(List<AppInfoModel> models);
}
