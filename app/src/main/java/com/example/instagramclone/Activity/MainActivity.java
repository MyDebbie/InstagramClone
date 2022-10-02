package com.example.instagramclone.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.instagramclone.R;
import com.example.instagramclone.fragments.HomeFragment;
import com.example.instagramclone.fragments.PostFragment;
import com.example.instagramclone.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    final Fragment PostFragment = new PostFragment();
    final Fragment HomeFragment = new HomeFragment();
    final Fragment Profilefragment = new ProfileFragment();
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.camera);
        getSupportActionBar().setTitle("       Instagram");

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.add_post:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, PostFragment).commit();
                                break;

                            case R.id.home:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, HomeFragment).commit();
                                break;

                            case R.id.profile:
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, Profilefragment).commit();
                                break;
                        }
                        return true;
                    }
                });
                bottomNavigationView.setSelectedItemId(R.id.home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_menu, menu);
        MenuItem Send= menu.findItem(R.id.send);

        return true;
    }


}