package com.ovwvwvo.appinfos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.adapter.HomeAdapter;
import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.appinfos.model.eventbus.CompleteMessage;
import com.ovwvwvo.appinfos.model.eventbus.SuccessMessage;
import com.ovwvwvo.appinfos.util.AppInfoUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeActivity extends BaseActivity implements onCallBackListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPager;

    private Subscription subscription;
    public List<AppInfoModel> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        viewPager.setAdapter(new HomeAdapter(mContext, getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        getData();
    }

    @Override
    public void onDestroy() {
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

    public void onDataLoadSuccess(List<AppInfoModel> models) {
        this.models = models;
        EventBus.getDefault().post(new SuccessMessage());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        } else if (item.getItemId() == R.id.action_search) {
            getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .add(R.id.fragment_container, SearchFragment.newInstance())
                .addToBackStack(null)
                .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
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
                    EventBus.getDefault().post(new CompleteMessage());
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(mContext, R.string.data_error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(List<AppInfoModel> models) {
                    onDataLoadSuccess(models);
                }
            });
    }


    @Override
    public void onRefresh() {
        getData();
    }
}

interface onCallBackListener {
    void onRefresh();
}