package com.ovwvwvo.appinfos.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.adapter.AppListItemAdapter;
import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.appinfos.presenter.AppListPresenter;
import com.ovwvwvo.appinfos.view.AppListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class AppListFragment extends BaseFragment implements AppListView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private static final String POSITION = "POSITION";

    private AppListItemAdapter adapter;
    private AppListPresenter presenter;
    private int position;

    public static AppListFragment newInstance(int position) {
        AppListFragment instance = new AppListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(POSITION, 0);
        presenter = new AppListPresenter(this);
        presenter.getAppList(position);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AppListItemAdapter(getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onRefresh() {
        presenter.getAppList(position);
    }

    @Override
    public void loadDataSuccess(List<AppInfoModel> models) {
        refreshLayout.setRefreshing(false);
        adapter.clearModel();
        adapter.setModels(models);
    }

    public List<AppInfoModel> getModels() {
        return adapter.getModels();
    }
}
