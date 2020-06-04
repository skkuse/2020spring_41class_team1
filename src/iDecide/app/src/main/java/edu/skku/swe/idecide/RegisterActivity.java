package edu.skku.swe.idecide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {
    Button emailBT, googleBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailBT = findViewById(R.id.email_register_button);
        googleBT = findViewById(R.id.google_register_button);


        // when register with email
        emailBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, EmailRegisterActivity.class);
                startActivity(intent);
            }
        });


        // when register with google
        googleBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 구글 계정 로그인

            }
        });
    }
}
