package edu.skku.swe.idecide.getPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.skku.swe.idecide.R;

public class p2_main_purpose extends AppCompatActivity {
    Button button;
    RadioGroup purposeRadio;
    int budget = -1, purpose = -1;
    String user_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p2_main_purpose);

        Intent intent = getIntent();
        user_key = intent.getStringExtra("user_key");
        budget = intent.getIntExtra("budget", -1);


        purposeRadio = findViewById(R.id.radio_main_purpose);
        button = findViewById(R.id.button_main_purpose);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int temp_id = purposeRadio.getCheckedRadioButtonId();
                    if (temp_id < 0) Toast.makeText(p2_main_purpose.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                    else {
                        switch (temp_id)
                        {
                            case R.id.purpose_0:
                                purpose = 0;
                                break;
                            case R.id.purpose_1:
                                purpose = 1;
                                break;
                        }

                    }
                } catch (Exception e) { }
                if (purpose >= 0) {
                    Intent intent = new Intent(p2_main_purpose.this, p3_usage.class);
                    intent.putExtra("user_key", user_key);
                    intent.putExtra("budget", budget);
                    intent.putExtra("main_purpose", purpose);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });
    }
}
