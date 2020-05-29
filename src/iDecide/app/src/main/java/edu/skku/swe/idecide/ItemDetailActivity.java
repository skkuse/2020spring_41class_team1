package edu.skku.swe.idecide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewDebug;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ItemDetailActivity extends AppCompatActivity {
    private RadarChart chart;
    TextView score;
    int hardwareColor = 0xFF64B5F6;
    int reviewColor = 0xFF9575CD;
    private RecyclerView recyclerView;
    private List<Vendor> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_item_detail);
        tb.setTitle("상세 정보"); // change title to clicked laptop name later (after db is made)
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // SCORE
        score = findViewById(R.id.score_item_detail);
        score.setText(Integer.toString((int) (Math.random() * 100)));



        recyclerView = (RecyclerView) findViewById(R.id.rv_item_detail);
        // 원래는 파이어베이스 item에서 받아와야함!!
        // 원래는 name, price, shipping, sitelink 받아와야 함(이미지 말고)
        if (list.isEmpty()) {
            list.add(new Vendor(R.drawable.vendor_11st, 1200000, 2500, "https://11st.co.kr"));
            list.add(new Vendor(R.drawable.vendor_coupang, 1200000, 0, "https://www.coupang.com/"));
            list.add(new Vendor(R.drawable.vendor_gmarket, 1200000, 2500, "https://www.gmarket.co.kr/"));
            list.add(new Vendor(R.drawable.vendor_interpark, 1200000, 0, "http://interpark.com/"));
            list.add(new Vendor(R.drawable.vendor_tmon, 1200000, 2500, "http://tmon.co.kr/"));
            list.add(new Vendor(R.drawable.vendor_11st, 1200000, 2500, "https://11st.co.kr"));
            list.add(new Vendor(R.drawable.vendor_coupang, 1200000, 0, "https://www.coupang.com/"));
            list.add(new Vendor(R.drawable.vendor_gmarket, 1200000, 2500, "https://www.gmarket.co.kr/"));
            list.add(new Vendor(R.drawable.vendor_interpark, 1200000, 0, "http://interpark.com/"));
            list.add(new Vendor(R.drawable.vendor_tmon, 1200000, 2500, "http://tmon.co.kr/"));
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new VendorAdapter(list));



        /* ABOUT CHART */
        // 전체화면
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("RadarChartActivity");
        chart = findViewById(R.id.chart_item_detail);
        //chart.setBackgroundColor(Color.rgb(60, 65, 82));
        chart.getDescription().setEnabled(false);
        chart.setWebLineWidth(1f);
        chart.setWebColor(Color.LTGRAY);
        chart.setWebLineWidthInner(1f);
        chart.setWebColorInner(Color.LTGRAY);
        chart.setWebAlpha(80);
        chart.setTouchEnabled(false);

        setData();

        // 애니메이션 효과
        chart.animateXY(1000, 1000);

        XAxis xAxis = chart.getXAxis();
        xAxis.setTextSize(15f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new MyValueFormatter());
        xAxis.setTextColor(Color.BLACK);
        YAxis yAxis = chart.getYAxis();
        yAxis.setLabelCount(4, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setDrawLabels(false);

        // 차트 어떤건지
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setYOffset(-10);
        l.setYEntrySpace(0);
        l.setTextSize(15);
        l.setTextColor(Color.BLACK);
    }

    private void setData() {

        float mul = 100;
        float min = 0;
        int cnt = 6;

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // 값 설정!!
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mul) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mul) + min;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "하드웨어 스펙");
        set1.setColor(hardwareColor);
        set1.setFillColor(hardwareColor);
        set1.setDrawFilled(true);
        set1.setFillAlpha(70);
        set1.setLineWidth(1f);
        //set1.setDrawHighlightCircleEnabled(true);
        //set1.setDrawHighlightIndicators(false);

        RadarDataSet set2 = new RadarDataSet(entries2, "사용자 리뷰 분석");
        set2.setColor(reviewColor);
        set2.setFillColor(reviewColor);
        set2.setDrawFilled(true);
        set2.setFillAlpha(70);
        set2.setLineWidth(1f);
        //set2.setDrawHighlightCircleEnabled(true);
        //set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setDrawValues(false);
        // 데이터 값 표시
        /*
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.BLACK);
         */

        chart.setData(data);
        chart.invalidate();
    }


    public class MyValueFormatter implements IAxisValueFormatter {

        private DecimalFormat mFormat;
        private final String[] mActivities = new String[]{"무게", "디자인", "화면 크기", "성능", "그래픽", "배터리"};

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mActivities[(int) value % mActivities.length];
        }
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

}