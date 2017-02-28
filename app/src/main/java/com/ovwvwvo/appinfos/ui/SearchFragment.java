package com.ovwvwvo.appinfos.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.adapter.AppInfoAdapter;
import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.common.widget.EditText.ClearableEditText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright ©2016 by ovwvwvo
 */

public class SearchFragment extends Fragment implements TextWatcher {

    @BindView(R.id.search_input)
    ClearableEditText searchInput;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private HomeActivity activity;

    private InputMethodManager imm;
    private MyHandler myHandler;
    private ScheduledExecutorService scheduledExecutor;

    private AppInfoAdapter adapter;

    private String searchContent;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduledExecutor = Executors.newScheduledThreadPool(50);
        myHandler = new MyHandler(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AppInfoAdapter(getContext());
        recyclerView.setAdapter(adapter);

        searchInput.addTextChangedListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showKeyboard();
                searchInput.setFocusable(true);
                searchInput.setFocusableInTouchMode(true);
                searchInput.requestFocus();
            }
        }, 200);
    }

    @OnClick(R.id.btn_back)
    void back() {
        hideKeyboard();
        getFragmentManager().popBackStack();
    }

    private void onSearch(String content) {
        List<AppInfoModel> result = new ArrayList<>();
        if (activity != null) {
            for (AppInfoModel model : activity.models) {
                String source = (model.getAppName() + model.getPackageName()).replace(" ", "").toLowerCase();
                if (source.contains((content.replace(" ", "").toLowerCase())))
                    result.add(model);
            }
        }
        adapter.setModels(result);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable != null && editable.length() > 0) {
            searchContent = editable.toString();
            scheduledExecutor.schedule(new SearchThread(editable.toString()), 200, TimeUnit.MILLISECONDS);
        } else {
            searchContent = "";
            adapter.clearModel();
        }
    }

    public class SearchThread implements Runnable {

        private String newText;

        SearchThread(String newText) {
            this.newText = newText;
        }

        @Override
        public void run() {
            if (newText != null && newText.equals(searchContent)) {
                myHandler.sendMessage(myHandler.obtainMessage(1, newText));
            }
        }
    }


    private static class MyHandler extends Handler {

        private WeakReference<SearchFragment> fragmentWeakReference;

         MyHandler(SearchFragment fragment) {
            fragmentWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                searchContent(msg.obj.toString());
            }
        }

        void searchContent(String content) {
            SearchFragment fragment = fragmentWeakReference.get();
            if (fragment != null) {
                fragment.adapter.clearModel();
                fragment.onSearch(content);
            }
        }
    }

    private void showKeyboard() {
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void hideKeyboard() {
        imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
    }
}
