package com.cloudconcept;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;

import utilities.ExceptionHandler;
import utilities.storeData;

/**
 * Created by Abanoub Wagdy on 1/26/2016.
 */
public class SplashActivity extends Activity {

    private static final long SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (new storeData(getApplicationContext()).getIsIntroDisplayed()) {

                    Intent mainIntent = new Intent(SplashActivity.this,
                            HomepageActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();

                } else {
                    Intent mainIntent = new Intent(SplashActivity.this,
                            EnayaIntro.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
