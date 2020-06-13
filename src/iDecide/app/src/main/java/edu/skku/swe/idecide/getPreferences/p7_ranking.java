package edu.skku.swe.idecide.getPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.SearchResultActivity;

public class p7_ranking extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p7_ranking);

        button = findViewById(R.id.button_ranking_finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(p7_ranking.this, SearchResultActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
