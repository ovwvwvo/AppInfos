package com.ovwvwvo.appinfos.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.adapter.AppListItemAdapter;
import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.appinfos.presenter.SearchPresenter;
import com.ovwvwvo.appinfos.view.SearchView;
import com.ovwvwvo.common.widget.EditText.ClearableEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Copyright ©2016 by rawer
 */

public class SearchFragment extends BaseFragment implements TextWatcher, SearchView, AppListItemAdapter.onItemClickListener {

    @BindView(R.id.search_input)
    ClearableEditText searchInput;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private InputMethodManager imm;

    private SearchPresenter presenter;
    private AppListItemAdapter adapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        presenter = new SearchPresenter(this);
        presenter.getAllAppList();
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
        adapter = new AppListItemAdapter(getContext());
        adapter.setListener(this);
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable != null) {
            presenter.searchApp(editable.toString());
        }
    }

    @Override
    public void loadDataSuccess(List<AppInfoModel> models) {
        presenter.setModels(models);
        adapter.clearModel();
        adapter.setModels(models);
    }

    @Override
    public void onSearchSuccess(List<AppInfoModel> models) {
        adapter.clearModel();
        adapter.setModels(models);
    }

    private void showKeyboard() {
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void hideKeyboard() {
        imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
    }

    @Override
    public void onItemClick(AppInfoModel model) {
        gotoAppInfoDetail(model.getPackageName());
    }

    private void gotoAppInfoDetail(String packageName) {
        Intent intent = new Intent(getActivity(), AppDetailActivity.class);
        intent.putExtra(AppDetailActivity.PACKAGE_NAME, packageName);
        startActivity(intent);
    }
}
