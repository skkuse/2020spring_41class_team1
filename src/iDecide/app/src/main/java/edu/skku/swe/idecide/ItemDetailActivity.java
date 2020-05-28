package edu.skku.swe.idecide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends AppCompatActivity {
    private RadarChart hardware_chart;
    private RadarChart review_chart;
    int hardwareColor = 0xFF64B5F6;
    int reviewColor = 0xFF9575CD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_item_detail);
        tb.setTitle("상세 정보"); // change title to clicked laptop name later (after db is made)
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setChart();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:{
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void setChart()
    {
        // 차트 자체에 관한 것
        hardware_chart = findViewById(R.id.hardware_chart_item_detail);
        hardware_chart.setTouchEnabled(false);
        review_chart = findViewById(R.id.review_chart_item_detail);
        review_chart.setTouchEnabled(false);

        hardware_chart.setWebLineWidth(1f);
        hardware_chart.setWebColor(Color.LTGRAY);
        hardware_chart.setWebLineWidthInner(1f);
        hardware_chart.setWebColorInner(Color.LTGRAY);
        hardware_chart.setWebAlpha(70);
        hardware_chart.getDescription().setEnabled(false);

        review_chart.setWebLineWidth(1f);
        review_chart.setWebColor(Color.LTGRAY);
        review_chart.setWebLineWidthInner(1f);
        review_chart.setWebColorInner(Color.LTGRAY);
        review_chart.setWebAlpha(70);
        review_chart.getDescription().setEnabled(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it


        // list that saves the value
        List<RadarEntry> hardwareAL = new ArrayList<>();
        List<RadarEntry> reviewAL = new ArrayList<>();
        // 여기만 수정하며 됨
        for (int i = 0; i < 6; i++) {

            float val1 = (float) (Math.random() * 6);
            float val2 = (float) (Math.random() * 6);
            hardwareAL.add(new RadarEntry(i, val1));
            reviewAL.add(new RadarEntry(i, val2));
        }

        RadarDataSet hardwareSet = new RadarDataSet(hardwareAL, "");
        RadarDataSet reviewSet = new RadarDataSet(reviewAL, "");

        hardwareSet.setFillColor(hardwareColor);
        hardwareSet.setDrawFilled(true);
        hardwareSet.setLineWidth(2f);
        hardwareSet.setFillAlpha(30);

        reviewSet.setFillColor(reviewColor);
        reviewSet.setDrawFilled(true);
        reviewSet.setLineWidth(2f);
        reviewSet.setFillAlpha(30);


        ArrayList<IRadarDataSet> hardware_sets = new ArrayList<>();
        hardware_sets.add(hardwareSet);
        ArrayList<IRadarDataSet> review_sets = new ArrayList<>();
        review_sets.add(reviewSet);


        RadarData hardware_data = new RadarData(hardware_sets);
        hardware_data.setDrawValues(false);
        RadarData review_data = new RadarData(review_sets);
        review_data.setDrawValues(false);

        // set color of the chart
        hardwareSet.setColor(hardwareColor);
        reviewSet.setColor(reviewColor);
        hardware_chart.setData(hardware_data);
        hardware_chart.invalidate();
        review_chart.setData(review_data);
        review_chart.invalidate();

        // set data

        //chart.animateXY(1400, 1400, Easing.EaseInOutQuad);

        /*
        XAxis xAxis = chart.getXAxis();
        //xAxis.setTypeface(tfLight);
        xAxis.setTextSize(9f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {

            private final String[] mActivities = new String[]{"Burger", "Steak", "Salad", "Pasta", "Pizza"};

            public String getFormattedValue(float value) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.WHITE);
        YAxis yAxis = chart.getYAxis();
        //yAxis.setTypeface(tfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        //l.setTypeface(tfLight);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.WHITE);

         */
    }

    public class MyValueFormatter implements IValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return mFormat.format(value) + " $"; // e.g. append a dollar-sign
        }
    }
}