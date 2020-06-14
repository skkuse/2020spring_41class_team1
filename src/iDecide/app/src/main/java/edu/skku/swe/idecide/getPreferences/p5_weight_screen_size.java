package edu.skku.swe.idecide.getPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.skku.swe.idecide.R;


public class p5_weight_screen_size extends AppCompatActivity {
    Button button;
    RadioGroup weightRadio, screenRadio;
    int budget = -1, purpose = -1, usage = -1, carry = -1, place_of_use = -1,
    weight = -1, screen_size = -1;
    String user_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p5_weight_screen_size);

        Intent intent = getIntent();
        user_key = intent.getStringExtra("user_key");
        budget = intent.getIntExtra("budget", -1);
        purpose = intent.getIntExtra("main_purpose", -1);
        usage = intent.getIntExtra("usage", -1);
        carry = intent.getIntExtra("carry", -1);
        place_of_use = intent.getIntExtra("place_of_use", -1);


        weightRadio = findViewById(R.id.radio_weight);
        screenRadio = findViewById(R.id.radio_screen_size);
        button = findViewById(R.id.button_weight_screen_size);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int temp_id = weightRadio.getCheckedRadioButtonId();
                    if (temp_id < 0);// Toast.makeText(p5_weight_screen_size.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                    else {
                        switch (temp_id)
                        {
                            case R.id.weight_0:
                                weight = 0;
                                break;
                            case R.id.weight_1:
                                weight = 1;
                                break;
                            case R.id.weight_2:
                                weight = 2;
                                break;
                        }

                    }
                } catch (Exception e) { }

                try {
                    int temp_id = screenRadio.getCheckedRadioButtonId();
                    if (temp_id < 0);// Toast.makeText(p5_weight_screen_size.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                    else {
                        switch (temp_id)
                        {
                            case R.id.screenSize_0:
                                screen_size = 0;
                                break;
                            case R.id.screenSize_1:
                                screen_size = 1;
                                break;
                            case R.id.screenSize_2:
                                screen_size = 2;
                                break;
                        }

                    }
                } catch (Exception e) { }


                if (weight >= 0 && screen_size >= 0) {
                    Intent intent = new Intent(p5_weight_screen_size.this, p6_OS_brand.class);
                    intent.putExtra("user_key", user_key);
                    intent.putExtra("budget", budget);
                    intent.putExtra("main_purpose", purpose);
                    intent.putExtra("usage", usage);
                    intent.putExtra("carry", carry);
                    intent.putExtra("place_of_use", place_of_use);
                    intent.putExtra("weight", weight);
                    intent.putExtra("screen_size", screen_size);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
                else Toast.makeText(p5_weight_screen_size.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
