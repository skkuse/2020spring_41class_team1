package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.skku.swe.idecide.entities.Hardware;
import edu.skku.swe.idecide.entities.Item;
import edu.skku.swe.idecide.entities.Review;
import edu.skku.swe.idecide.entities.Vendor;
import edu.skku.swe.idecide.entities.VendorAdapter;

public class ItemDetailActivity extends AppCompatActivity implements Serializable {
    private RadarChart chart;
    TextView scoreTV;
    int hardwareColor = 0xFF64B5F6;
    int reviewColor = 0xFF9575CD;
    private RecyclerView recyclerView;
    private ProgressBar mProgressbar;

    private List<Vendor> vendors = new ArrayList<>();
    private Hardware hardware;
    private Review review;
    private String code;
    private String score;
    private String manufacture;
    private String name;
    private String num;
    private String user_key;

    Item item;

    FloatingActionButton add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);



        Intent intent = getIntent();
        user_key = intent.getStringExtra("user_key");
        code = intent.getStringExtra("code");
        hardware = (Hardware) intent.getSerializableExtra("hardware");
        review = (Review) intent.getSerializableExtra("review");
        vendors = (List<Vendor>) intent.getSerializableExtra("vendors");
        score = intent.getStringExtra("score");
        manufacture = intent.getStringExtra("manufacture");
        name = intent.getStringExtra("name");
        num = intent.getStringExtra("num");


        // toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_item_detail);
        tb.setTitle(code); // change title to clicked laptop name later (after db is made)
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressbar = findViewById(R.id.progressBar_i);

        // add to cart button
        add_button = findViewById(R.id.fab_item_detail);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                item = new Item(code, manufacture, name, num, score, hardware, review);
                // preference firestore로 전송
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> postValue = null;
                postValue = item.toMap();

                /*
                db.collection("User").document(user_key).collection("Cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                QuerySnapshot snapshots1 = task.getResult();
                                for (DocumentSnapshot documentSnapshot : snapshots1.getDocuments()) {
                                    if (documentSnapshot.getId().equals(code))
                                    {
                                        Toast toast = Toast.makeText(getApplicationContext(), "이미 장바구니 있는 아이템입니다", Toast.LENGTH_SHORT);
                                        toast.show();
                                        break;
                                    }
                                }

                            }
                        });

                 */


                try {
                    db.collection("User").document(user_key)
                            .collection("Cart").document(code).set(postValue);
                    for (int i = 0; i < vendors.size(); i++) {
                        Map<String, Object> postValue1 = null;
                        postValue1 = vendors.get(i).toMap();
                        Log.v("error", vendors.get(i).getName());
                        db.collection("User").document(user_key)
                                .collection("Cart").document(code).collection("Vendor")
                                .document(vendors.get(i).getName()).set(postValue1);
                    }

                    Toast toast = Toast.makeText(getApplicationContext(), "장바구니에 추가되었습니다", Toast.LENGTH_SHORT);
                    toast.show();
                    hideDialog();
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getApplicationContext(), "이미 장바구니에 있는 아이템입니다", Toast.LENGTH_SHORT);
                    toast.show();
                    hideDialog();
                }
            }
        });



        // SCORE
        scoreTV = findViewById(R.id.score_item_detail);
        scoreTV.setText(score);


        recyclerView = (RecyclerView) findViewById(R.id.rv_item_detail);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new VendorAdapter(vendors));



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
        /*
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mul) + min;
            entries1.add(new RadarEntry(val1));

            float val2 = (float) (Math.random() * mul) + min;
            entries2.add(new RadarEntry(val2));
        }
         */
        entries1.add(new RadarEntry(hardware.getWeight()));
        entries1.add(new RadarEntry(hardware.getDesign()));
        entries1.add(new RadarEntry(hardware.getScreen()));
        entries1.add(new RadarEntry(hardware.getPerformance()));
        entries1.add(new RadarEntry(hardware.getGraphic()));
        entries1.add(new RadarEntry(hardware.getBattery()));

        entries2.add(new RadarEntry(review.getWeight()));
        entries2.add(new RadarEntry(review.getDesign()));
        entries2.add(new RadarEntry(review.getScreen()));
        entries2.add(new RadarEntry(review.getPerformance()));
        entries2.add(new RadarEntry(review.getGraphic()));
        entries2.add(new RadarEntry(review.getBattery()));




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

    private void showDialog(){
        mProgressbar.setVisibility(View.VISIBLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideDialog(){
        if(mProgressbar.getVisibility() == View.VISIBLE){
            mProgressbar.setVisibility(View.INVISIBLE);
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

}