package com.example.waba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class slidemenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    Button logout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidemenu);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Location()).commit();
            navigationView.setCheckedItem(R.id.location);

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
     switch (menuItem.getItemId()){

         case R.id.profile:
             getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new profile()).commit();
             break;
         case R.id.support:
             getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new help( )).commit();
             break;
         case R.id.location:
             getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Location()).commit();
             break;

         case R.id.call:
             getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new contact_driver()).commit();
             break;
         case R.id.report:
             Toast.makeText(this, "report_problem",Toast.LENGTH_SHORT).show();
             break;
         case R.id.about:
             Toast.makeText(this,"about_us",Toast.LENGTH_SHORT);
             break;
     }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }
        else{
            super.onBackPressed();
        }
    }
}
