package edu.skku.swe.idecide.getPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.skku.swe.idecide.R;
import edu.skku.swe.idecide.utils.ToggleButtonGroupTableLayout;

public class p6_OS_brand extends AppCompatActivity {
    Button button;
    RadioGroup osRadio;
    ToggleButtonGroupTableLayout brandRadio;
    int budget = -1, purpose = -1, usage = -1, carry = -1, place_of_use = -1,
            weight = -1, screen_size = -1, os = -1, brand = -1;
    String user_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p6_os_brand);


        Intent intent = getIntent();
        user_key = intent.getStringExtra("user_key");
        budget = intent.getIntExtra("budget", -1);
        purpose = intent.getIntExtra("main_purpose", -1);
        usage = intent.getIntExtra("usage", -1);
        carry = intent.getIntExtra("carry", -1);
        place_of_use = intent.getIntExtra("place_of_use", -1);
        weight = intent.getIntExtra("weight", -1);
        screen_size = intent.getIntExtra("screen_size", -1);

        osRadio = findViewById(R.id.radio_os);
        brandRadio = findViewById(R.id.radio_brand);
        button = findViewById(R.id.button_os_brand);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int temp_id = osRadio.getCheckedRadioButtonId();
                    if (temp_id < 0) ; //Toast.makeText(p6_OS_brand.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                    else {
                        switch (temp_id)
                        {
                            case R.id.os_0:
                                os = 0;
                                break;
                            case R.id.os_1:
                                os = 1;
                                break;
                            case R.id.os_2:
                                os = 2;
                                break;
                        }

                    }
                } catch (Exception e) { }

                try {
                    int temp_id = brandRadio.getCheckedRadioButtonId();
                    if (temp_id < 0) ; //Toast.makeText(p6_OS_brand.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                    else {
                        switch (temp_id)
                        {
                            case R.id.brand_0:
                                brand = 0;
                                break;
                            case R.id.brand_1:
                                brand = 1;
                                break;
                            case R.id.brand_2:
                                brand = 2;
                                break;
                            case R.id.brand_3:
                                brand = 3;
                                break;
                            case R.id.brand_4:
                                brand = 4;
                                break;
                            case R.id.brand_5:
                                brand = 5;
                                break;
                            case R.id.brand_6:
                                brand = 6;
                                break;
                            case R.id.brand_7:
                                brand = 7;
                                break;
                            case R.id.brand_8:
                                brand = 8;
                                break;
                            case R.id.brand_9:
                                brand = 9;
                                break;
                        }

                    }
                } catch (Exception e) { }


                if (os >= 0 && brand >= 0) {
                    Intent intent = new Intent(p6_OS_brand.this, p7_ranking.class);
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
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
                else {
                    Toast.makeText(p6_OS_brand.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                    Log.v("error os", String.valueOf(os));
                    Log.v("error brand", String.valueOf(brand));
                }

            }
        });
    }
}
