package edu.skku.swe.idecide.getPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.SearchResultActivity;
import edu.skku.swe.idecide.utils.ToggleButtonGroupTableLayout;

public class p7_ranking extends AppCompatActivity {
    Button button;
    ToggleButtonGroupTableLayout rankingRadio;
    int budget = -1, purpose = -1, usage = -1, carry = -1, place_of_use = -1,
            weight = -1, screen_size = -1, os = -1, brand = -1, ranking = -1;
    String user_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p7_ranking);


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


        rankingRadio = findViewById(R.id.radio_ranking);
        button = findViewById(R.id.button_ranking_finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int temp_id = rankingRadio.getCheckedRadioButtonId();
                    if (temp_id < 0) Toast.makeText(p7_ranking.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                    else {
                        switch (temp_id)
                        {
                            case R.id.ranking_0:
                                ranking = 0;
                                break;
                            case R.id.ranking_1:
                                ranking = 1;
                                break;
                            case R.id.ranking_2:
                                ranking = 2;
                                break;
                            case R.id.ranking_3:
                                ranking = 3;
                                break;
                            case R.id.ranking_4:
                                ranking = 4;
                                break;
                            case R.id.ranking_5:
                                ranking = 5;
                                break;
                            case R.id.ranking_6:
                                ranking = 6;
                                break;
                            case R.id.ranking_7:
                                ranking = 7;
                                break;
                        }

                    }
                } catch (Exception e) { }

                if (ranking >= 0) {
                    Intent intent = new Intent(p7_ranking.this, SearchResultActivity.class);
                    intent.putExtra("user_key", user_key);
                    intent.putExtra("budget", budget);
                    intent.putExtra("main_purpose", purpose);
                    intent.putExtra("usage", usage);
                    intent.putExtra("carry", carry);
                    intent.putExtra("place_of_use", place_of_use);
                    intent.putExtra("weight", weight);
                    intent.putExtra("screen_size", screen_size);
                    intent.putExtra("os", os);
                    intent.putExtra("brand", brand);
                    intent.putExtra("ranking", ranking);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
