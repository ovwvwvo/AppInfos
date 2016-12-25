package com.ovwvwvo.appinfos.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ovwvwvo.appinfos.R;
import com.ovwvwvo.appinfos.adapter.HomeAdapter;
import com.ovwvwvo.appinfos.model.AppInfoModel;
import com.ovwvwvo.appinfos.model.eventbus.CompleteMessage;
import com.ovwvwvo.appinfos.model.eventbus.SuccessMessage;
import com.ovwvwvo.appinfos.util.AppInfoUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.ShareBoardConfig;

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

public class HomeActivity extends BaseActivity implements InfoFragment.onCallBackListener {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
        } else if (item.getItemId() == R.id.action_share) {
            ShareBoardConfig config = new ShareBoardConfig();
            config.setShareboardBackgroundColor(Color.WHITE)
                    .setCancelButtonVisibility(false)
                    .setIndicatorColor(Color.WHITE, Color.WHITE)
                    .setTitleVisibility(false);
            new ShareAction(HomeActivity.this)
                    .withText(getString(R.string.share_content))
                    .withExtra(new UMImage(mContext, R.mipmap.ic_launcher))
                    .withTitle(getString(R.string.app_name))
                    .withTargetUrl(getString(R.string.share_url))
                    .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                            SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.MORE)
                    .setCallback(umShareListener)
                    .open(config);
        }
        return super.onOptionsItemSelected(item);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t != null) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    };

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