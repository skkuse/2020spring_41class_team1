package edu.skku.swe.idecide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import edu.skku.swe.idecide.R;

public class GetProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_profile);
    }



    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen(){
        Intent intent = new Intent(GetProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
