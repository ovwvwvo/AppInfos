package com.ovwvwvo.appinfos.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.adapter.AppInfoAdapter;
import com.ovwvwvo.appinfos.model.eventbus.CompleteMessage;
import com.ovwvwvo.appinfos.model.eventbus.SuccessMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class InfoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private static final String POSITION = "POSITION";

    private onCallBackListener listener;
    private AppInfoAdapter adapter;
    private HomeActivity activity;
    private int position;

    public static InfoFragment newInstance(int position) {
        InfoFragment instance = new InfoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        instance.setArguments(bundle);
        return instance;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        try {
            listener = (onCallBackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getClass().getName()
                + " must implements interface onCallBackListener");
        }
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(SuccessMessage message) {
        if (activity != null) {
            adapter.clearModel();
            adapter.setModels(activity.models);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCompleteEvent(CompleteMessage message) {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (listener != null)
            listener.onRefresh();
    }

    public interface onCallBackListener {
        void onRefresh();
    }
}
