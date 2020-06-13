package edu.skku.swe.idecide.getPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.skku.swe.idecide.R;

public class p4_place_of_use_carry extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p4_place_of_use);

        button = findViewById(R.id.button_place);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(p4_place_of_use_carry.this, p5_weight_screen_size.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
