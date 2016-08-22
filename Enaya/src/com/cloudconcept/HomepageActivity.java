package com.cloudconcept;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.liulishuo.magicprogresswidget.MagicProgressCircle;
import com.salesforce.androidsdk.smartsync.app.SmartSyncSDKManager;

import java.util.ArrayList;
import java.util.Random;

import custom.AnimTextView;
import custom.Fab;
import utilities.ActivitiesLauncher;
import utilities.ExceptionHandler;

/**
 * Created by Abanoub Wagdy on 1/27/2016.
 */
public class HomepageActivity extends DemoBase implements View.OnClickListener, OnChartValueSelectedListener {

    private MagicProgressCircle demoMpc;
    private final Random random = new Random();
    private MaterialSheetFab<Fab> materialSheetFab;
    private PieChart mChart;
    private AnimTextView demoTv;
    ImageView btnLogout;
    Button btnDashboard, btnProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.homepage);

        Fab fab = (Fab) findViewById(R.id.fab);
        View sheetView = findViewById(R.id.fab_sheet);
        View overlay = findViewById(R.id.overlay);
        btnLogout = (ImageView) findViewById(R.id.btnLogout);
        btnDashboard = (Button) findViewById(R.id.btnDashboard);
        btnProducts = (Button) findViewById(R.id.btnProducts);
        demoTv = (AnimTextView) findViewById(R.id.demo_tv);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartSyncSDKManager.getInstance().logout(HomepageActivity.this);
            }
        });
        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitiesLauncher.openDashboardActivity(getApplicationContext());
            }
        });

        btnProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitiesLauncher.openProductsActivity(getApplicationContext());
            }
        });

        mChart = (PieChart) findViewById(R.id.chart1);
        demoMpc = (MagicProgressCircle) findViewById(R.id.demoMpc);

        int sheetColor = getResources().getColor(R.color.noc_below_background);
        int fabColor = getResources().getColor(R.color.header_background);

        // Initialize material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay,
                sheetColor, fabColor);

        findViewById(R.id.fab_sheet_item_recording).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_reminder).setOnClickListener(this);
        findViewById(R.id.fab_sheet_item_photo).setOnClickListener(this);
        anim();
        InitializeChart();
    }

    private void anim() {
        final int score = random.nextInt(101);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(demoMpc, "percent", 0, score / 100f),
                ObjectAnimator.ofInt(demoTv, "score", 0, score)
        );
        set.setDuration(600);
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
        demoMpc.setStrokeWidth(10);
    }

    @Override
    public void onBackPressed() {
        if (materialSheetFab.isSheetVisible()) {
            materialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_sheet_item_photo:
                materialSheetFab.hideSheet();
                break;
            case R.id.fab_sheet_item_recording:
                materialSheetFab.hideSheet();
                break;
            case R.id.fab_sheet_item_reminder:
                materialSheetFab.hideSheet();
                break;
        }
    }


    public void InitializeChart() {
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

//                    tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//                    mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));

        mChart.setCenterText("Enaya\nDashboard");
        mChart.setCenterTextSize(12);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.getLegend().setEnabled(false);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        setData(3, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();


        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++) {
            xVals.add(mParties[i % mParties.length]);
        }


        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}