package edu.skku.swe.idecide;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity
{
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finish();
    }
}
