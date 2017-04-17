package com.ovwvwvo.appinfos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.adapter.AppListItemAdapter;
import com.ovwvwvo.appinfos.adapter.HomeAdapter;
import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.appinfos.model.perference.SettingPreference;
import com.ovwvwvo.appinfos.presenter.AppListPresenter;
import com.ovwvwvo.appinfos.view.AppListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright ©2016 by rawer
 */

public class AppListFragment extends BaseFragment implements AppListView, SwipeRefreshLayout.OnRefreshListener, AppListItemAdapter.onItemClickListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private static final String POSITION = "POSITION";

    private AppListItemAdapter adapter;
    private AppListPresenter presenter;
    private int position;

    private InterstitialAd mInterstitialAd;

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
        position = getArguments().getInt(POSITION, HomeAdapter.ME);
        presenter = new AppListPresenter(this);
        presenter.getAppList(position);

        initAds();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applist, container, false);
        ButterKnife.bind(this, view);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AppListItemAdapter(getContext());
        adapter.setListener(this);
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

    public void initAds() {
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.ads_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();
    }

    public List<AppInfoModel> getModels() {
        return adapter.getModels();
    }

    @Override
    public void onItemClick(AppInfoModel model) {
        if (mInterstitialAd.isLoaded() && SettingPreference.getDisplayAds(getContext())) {
            mInterstitialAd.show();
        } else {
            gotoAppInfoDetail(model.getPackageName());
        }
    }

    private void gotoAppInfoDetail(String packageName) {
        Intent intent=new Intent(getActivity(), AppDetailActivity.class);
        intent.putExtra(AppDetailActivity.PACKAGE_NAME,packageName);
        startActivity(intent);
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
            .build();
        mInterstitialAd.loadAd(adRequest);
    }

}
