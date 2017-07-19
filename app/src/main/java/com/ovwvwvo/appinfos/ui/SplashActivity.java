package com.ovwvwvo.appinfos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ovwvwvo.appinfos.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright ©2016 by rawer
 */

public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv)
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        imageView.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                next();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    private void next() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
