package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class SearchResultActivity extends AppCompatActivity {

    private ListView mListview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        // toolbar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_search_result);
        tb.setTitle("검색 결과");
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mListview = (ListView) findViewById(R.id.listview);


        FirebaseFirestore.getInstance().collection("Laptop")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<String> laptops = new ArrayList<String>();
                    for(DocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        laptops.add((String) document.getData().get("mLaptopName"));
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_list_item_1, laptops);
                    mListview.setAdapter(arrayAdapter);
                }
            }
        });

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = mListview.getItemAtPosition(position);
                Toast.makeText(SearchResultActivity.this, listItem.toString(), Toast.LENGTH_SHORT).show();
            }
        });

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
