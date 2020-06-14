package edu.skku.swe.idecide.getPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.skku.swe.idecide.R;

public class p4_place_of_use_carry extends AppCompatActivity {
    Button button;
    RadioGroup carryRadio, placeRadio;
    int budget = -1, purpose = -1, usage = -1, carry = -1, place_of_use = -1;
    String user_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p4_place_of_use);

        Intent intent = getIntent();
        user_key = intent.getStringExtra("user_key");
        budget = intent.getIntExtra("budget", -1);
        purpose = intent.getIntExtra("main_purpose", -1);
        usage = intent.getIntExtra("usage", -1);

        carryRadio = findViewById(R.id.radio_carry);
        placeRadio = findViewById(R.id.radio_place_of_use);
        button = findViewById(R.id.button_place);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int temp_id = carryRadio.getCheckedRadioButtonId();
                    if (temp_id < 0); //Toast.makeText(p4_place_of_use_carry.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                    else {
                        switch (temp_id)
                        {
                            case R.id.carry_0:
                                carry = 0;
                                break;
                            case R.id.carry_1:
                                carry = 1;
                                break;
                            case R.id.carry_2:
                                carry = 2;
                                break;
                        }

                    }
                } catch (Exception e) { }

                try {
                    int temp_id = placeRadio.getCheckedRadioButtonId();
                    if (temp_id < 0); //Toast.makeText(p4_place_of_use_carry.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                    else {
                        switch (temp_id)
                        {
                            case R.id.place_0:
                                place_of_use = 0;
                                break;
                            case R.id.place_1:
                                place_of_use = 1;
                                break;
                            case R.id.place_2:
                                place_of_use = 2;
                                break;
                            case R.id.place_3:
                                place_of_use = 3;
                                break;
                        }

                    }
                } catch (Exception e) { }

                if (carry >= 0 && place_of_use >= 0) {
                    Intent intent = new Intent(p4_place_of_use_carry.this, p5_weight_screen_size.class);
                    intent.putExtra("user_key", user_key);
                    intent.putExtra("budget", budget);
                    intent.putExtra("main_purpose", purpose);
                    intent.putExtra("usage", usage);
                    intent.putExtra("carry", carry);
                    intent.putExtra("place_of_use", place_of_use);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
                else Toast.makeText(p4_place_of_use_carry.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
