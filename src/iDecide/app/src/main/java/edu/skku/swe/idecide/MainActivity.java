package edu.skku.swe.idecide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private FragmentHome fragmentHome;
    private FragmentProfile fragmentProfile;
    private FragmentSearch fragmentSearch;
    private FragmentHistory fragmentHistory;
    private FragmentCart fragmentCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

        fragmentHome = new FragmentHome();
        fragmentProfile = new FragmentProfile();
        fragmentSearch = new FragmentSearch();
        fragmentHistory = new FragmentHistory();
        fragmentCart = new FragmentCart();
        setFrag(0);
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

}
