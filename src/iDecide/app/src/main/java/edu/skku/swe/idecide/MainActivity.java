package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import edu.skku.swe.idecide.entities.Laptop;
import edu.skku.swe.idecide.entities.User;
import edu.skku.swe.idecide.fragment.FragmentCart;
import edu.skku.swe.idecide.fragment.FragmentHistory;
import edu.skku.swe.idecide.fragment.FragmentHome;
import edu.skku.swe.idecide.fragment.FragmentProfile;
import edu.skku.swe.idecide.fragment.FragmentSearch;
import edu.skku.swe.idecide.utils.UpdateLaptop;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragmentHome fragmentHome;
    private FragmentProfile fragmentProfile;
    private FragmentSearch fragmentSearch;
    private FragmentHistory fragmentHistory;
    private FragmentCart fragmentCart;
    private String user_key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUserKey();

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_profile:
                        setFrag(1);
                        break;
                    case R.id.action_search:
                        setFrag(2);
                        break;
                    case R.id.action_history:
                        setFrag(3);
                        break;
                    case R.id.action_cart:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });

        fragmentHome = new FragmentHome(user_key);
        fragmentProfile = new FragmentProfile(user_key);
        fragmentSearch = new FragmentSearch(user_key);
        fragmentHistory = new FragmentHistory(user_key);
        fragmentCart = new FragmentCart(user_key);
        setFrag(0);

        try {
            UpdateLaptop.saveLaptopsToFiretore("model.txt", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFrag(int i)
    {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (i)
        {
            case 0:
                ft.replace(R.id.frameLayout_main,fragmentHome);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.frameLayout_main,fragmentProfile);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.frameLayout_main,fragmentSearch);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.frameLayout_main,fragmentHistory);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.frameLayout_main,fragmentCart);
                ft.commit();
                break;
        }
    }

    private void getUserKey()
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user_key = firebaseAuth.getCurrentUser().getEmail();
    }
}
