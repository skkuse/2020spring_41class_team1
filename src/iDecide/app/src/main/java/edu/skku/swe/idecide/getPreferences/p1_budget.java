package edu.skku.swe.idecide.getPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import edu.skku.swe.idecide.LoginActivity;
import edu.skku.swe.idecide.R;

public class p1_budget extends AppCompatActivity {
    Button button;
    RadioGroup budgetRadio;
    int budget = -1;
    String user_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p1_budget);

        budgetRadio = findViewById(R.id.radio_budget);
        button = findViewById(R.id.button_budget);
        Intent intent = getIntent();
        user_key = intent.getStringExtra("user_key");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int temp_id = budgetRadio.getCheckedRadioButtonId();
                    if (temp_id < 0) Toast.makeText(p1_budget.this, "항목을 모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                    else {
                        switch (temp_id)
                        {
                            case R.id.budget_0:
                                budget = 0;
                                break;
                            case R.id.budget_1:
                                budget = 1;
                                break;
                            case R.id.budget_2:
                                budget = 2;
                                break;
                            case R.id.budget_3:
                                budget = 3;
                                break;
                            case R.id.budget_4:
                                budget = 4;
                                break;
                        }

                    }
                } catch (Exception e) { }
                if (budget >= 0) {
                    Intent intent = new Intent(p1_budget.this, p2_main_purpose.class);
                    intent.putExtra("user_key", user_key);
                    intent.putExtra("budget", budget);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                }
            }
        });
    }
}
