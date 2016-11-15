package com.ovwvwvo.appinfos.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.adapter.AppInfoAdapter;
import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.appinfos.util.AppInfoUtil;

import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class InfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private static final String POSITION = "POSITION";

    private Subscription subscription;
    private AppInfoAdapter adapter;
    private int position;

    public static InfoFragment newInstance(int position) {
        InfoFragment instance = new InfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(POSITION, 0);
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
        adapter = new AppInfoAdapter(getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onRefresh() {
        getData(adapter);
    }

    private void getData(final AppInfoAdapter adapter) {
        Observable<List<AppInfoModel>> dataObservable = Observable.fromCallable(new Callable<List<AppInfoModel>>() {
            @Override
            public List<AppInfoModel> call() throws Exception {
                return AppInfoUtil.getAllAppInfos(getContext());
            }
        });

        subscription = dataObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<AppInfoModel>>() {
                @Override
                public void onCompleted() {
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(getContext(), "数据出错!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(List<AppInfoModel> models) {
                    adapter.setModels(models);
                }
            });
    }
}
