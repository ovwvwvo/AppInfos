package com.ovwvwvo.appinfos.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Context mContext;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private AppInfoAdapter adapter;

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppInfoAdapter(this);
        recyclerView.setAdapter(adapter);

        getData(adapter);
    }

    private void getData(final AppInfoAdapter adapter) {
        Observable<List<AppInfoModel>> dataObservable = Observable.fromCallable(new Callable<List<AppInfoModel>>() {
            @Override
            public List<AppInfoModel> call() throws Exception {
                return AppInfoUtil.getAllAppInfos(mContext);
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
                        Toast.makeText(mContext, "数据出错!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<AppInfoModel> models) {
                        adapter.setModels(models);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        getData(adapter);
    }
}
