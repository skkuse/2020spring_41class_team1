package edu.skku.swe.idecide.getPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.skku.swe.idecide.R;


public class p3_usage extends AppCompatActivity {
    Button button;
    RadioGroup usageRadio;
    int budget = -1, purpose = -1, usage = -1;
    String user_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p3_usage);

        Intent intent = getIntent();
        user_key = intent.getStringExtra("user_key");
        budget = intent.getIntExtra("budget", -1);
        purpose = intent.getIntExtra("main_purpose", -1);

        usageRadio = findViewById(R.id.radio_usage);
        button = findViewById(R.id.button_usage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int temp_id = usageRadio.getCheckedRadioButtonId();
                    if (temp_id < 0) Toast.makeText(p3_usage.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                    else {
                        switch (temp_id)
                        {
                            case R.id.usage_0:
                                usage = 0;
                                break;
                            case R.id.usage_1:
                                usage = 1;
                                break;
                            case R.id.usage_2:
                                usage = 2;
                                break;
                            case R.id.usage_3:
                                usage = 3;
                                break;
                            case R.id.usage_4:
                                usage = 4;
                                break;
                        }

                    }
                } catch (Exception e) { }
                if (usage >= 0) {
                    Intent intent = new Intent(p3_usage.this, p4_place_of_use_carry.class);
                    intent.putExtra("user_key", user_key);
                    intent.putExtra("budget", budget);
                    intent.putExtra("main_purpose", purpose);
                    intent.putExtra("usage", usage);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
