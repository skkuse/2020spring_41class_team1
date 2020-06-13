package edu.skku.swe.idecide.getPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.skku.swe.idecide.R;


public class p5_weight_screen_size extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p5_weight_screen_size);

        button = findViewById(R.id.button_weight_screen_size);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(p5_weight_screen_size.this, p6_OS_brand.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
