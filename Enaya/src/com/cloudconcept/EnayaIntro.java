package com.cloudconcept;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import utilities.ActivitiesLauncher;
import utilities.storeData;

/**
 * Created by Abanoub Wagdy on 1/26/2016.
 */
public class EnayaIntro extends AppIntro {

    @Override
    public void init(@Nullable Bundle savedInstanceState) {

        addSlide(AppIntroFragment.newInstance("Title here", "Description here...\nYeah, I've added this fragment programmatically",
                R.drawable.sf__icon, Color.parseColor("#FFC93437")));

        addSlide(AppIntroFragment.newInstance("HTML Description", Html.fromHtml("<b>Description bold...</b><br><i>Description italic...</i>"),
                R.drawable.sf__icon, Color.parseColor("#FF375BF1")));

        addSlide(AppIntroFragment.newInstance("HTML Description", Html.fromHtml("<b>Description bold...</b><br><i>Description italic...</i>"),
                R.drawable.sf__icon, Color.parseColor("#FFF7D23E")));

        addSlide(AppIntroFragment.newInstance("HTML Description", Html.fromHtml("<b>Description bold...</b><br><i>Description italic...</i>"),
                R.drawable.sf__icon, Color.parseColor("#FF34A350")));

        addSlide(AppIntroFragment.newInstance("HTML Description", Html.fromHtml("<b>Description bold...</b><br><i>Description italic...</i>"),
                R.drawable.sf__icon, Color.parseColor("#505959")));

        setBarColor(Color.parseColor("#22b2bd"));
        setSeparatorColor(Color.parseColor("#2196F3"));

        showSkipButton(true);
        new storeData(getApplicationContext()).setIsIntroDisplayed(true);

    }

    private void loadMainActivity() {
        ActivitiesLauncher.openHomepageActivity(getApplicationContext());
        finish();
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
        Toast.makeText(getApplicationContext(), getString(R.string.done_button), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNextPressed() {

    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {

    }

    public void getStarted(View v) {
        loadMainActivity();
    }
}
