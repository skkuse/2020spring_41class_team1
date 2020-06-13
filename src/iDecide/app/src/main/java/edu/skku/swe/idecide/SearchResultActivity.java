package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.skku.swe.idecide.entities.Item;
import edu.skku.swe.idecide.entities.ItemAdapter;
import edu.skku.swe.idecide.entities.Preference;
import edu.skku.swe.idecide.fragment.FragmentSearch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Item> list = new ArrayList<>();
    int budget = -1, purpose = -1, usage = -1, carry = -1, place_of_use = -1,
            weight = -1, screen_size = -1, os = -1, brand = -1, ranking = -1;
    String date_and_time = "asd", user_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_search_result);
        tb.setTitle("검색 결과");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // preference 받기
        Intent intent = getIntent();
        user_key = intent.getStringExtra("user_key");
        budget = intent.getIntExtra("budget", -1);
        purpose = intent.getIntExtra("main_purpose", -1);
        usage = intent.getIntExtra("usage", -1);
        carry = intent.getIntExtra("carry", -1);
        place_of_use = intent.getIntExtra("place_of_use", -1);
        weight = intent.getIntExtra("weight", -1);
        screen_size = intent.getIntExtra("screen_size", -1);
        os = intent.getIntExtra("os", -1);
        brand = intent.getIntExtra("brand", -1);
        ranking = intent.getIntExtra("ranking", ranking);
        date_and_time = getDate_and_time();

        Preference preference = new Preference(date_and_time, budget, purpose, usage, place_of_use,
                carry, weight, screen_size, os, brand, ranking);

        // preference firestore로 전송
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> postValue = null;
        postValue = preference.toMap();
        db.collection("User").document(user_key)
                .collection("Preference").document(date_and_time).set(postValue);


        /*
        Log.v("error", user_key);
        Log.v("error1", date_and_time);
        Log.v("error2", String.valueOf(budget));
        Log.v("error3", String.valueOf(purpose));
        Log.v("error4", String.valueOf(usage));
        Log.v("error5", String.valueOf(place_of_use));
        Log.v("error6", String.valueOf(carry));
        Log.v("error7", String.valueOf(weight));
        Log.v("error8", String.valueOf(screen_size));
        Log.v("error9", String.valueOf(os));
        Log.v("error10", String.valueOf(brand));
        Log.v("error11", String.valueOf(ranking));

         */


        // firebase에서 추천 노트북 받아오기


        // history에 추가


        // 사용자에게 보여주기
        recyclerView = findViewById(R.id.rv_search_result);

        // 원래는 파이어베이스 history detail에서 받아와야함!!
        for (int i = 0; i < 20; i++)
        {
            list.add(new Item(R.drawable.ion,"SAMSUNG","Ion", "#" + (i + 1), "90"));
        }

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new ItemAdapter(list));
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

    public String getDate_and_time()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss");
        String datestr = sdf.format(cal.getTime());

        return datestr;
    }
}
