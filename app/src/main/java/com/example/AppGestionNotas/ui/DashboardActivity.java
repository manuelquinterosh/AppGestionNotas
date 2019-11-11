package com.example.AppGestionNotas.ui;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.AppGestionNotas.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavegationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment f = null;

           switch (menuItem.getItemId()){
               case R.id.navigation_home:
                   f = new NotaFragment();
                   return true;
               case R.id.navigation_dashboard:
                   return true;
               case R.id.navigation_notifications:
                   return true;
           }

           if(f != null) {
              getSupportFragmentManager().beginTransaction()
                      .replace(R.id.container, f)
                      .commit();
            }

            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
         navView.setOnNavigationItemSelectedListener(mOnNavegationItemSelectedListener);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new NotaFragment())
                .commit();
    }


}
