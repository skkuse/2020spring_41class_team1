package edu.skku.swe.idecide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import edu.skku.swe.idecide.entities.Item;
import edu.skku.swe.idecide.entities.ItemAdapter;

public class HistoryDetailActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Item> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        // toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_history_detail);
        tb.setTitle("검색 기록");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView = findViewById(R.id.rv_history_detail);

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
}