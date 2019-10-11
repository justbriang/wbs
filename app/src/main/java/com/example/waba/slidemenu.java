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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class slidemenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    Button logout1;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private TextView namet,phonet;
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
        View view=navigationView.getHeaderView(0);

        mAuth = FirebaseAuth.getInstance();
        String user_id=mAuth.getCurrentUser().getUid();
        namet=view.findViewById(R.id.sidename);
        phonet=view.findViewById(R.id.sidephone);
        dbRef = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child("profile").child(user_id);



        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Profilead u = dataSnapshot.getValue(Profilead.class);

                    String name = u.getName();
                    String phone=u.getMobile();

                   namet.setText(name);
                   phonet.setText(phone);

                }else{
                    Toast.makeText(slidemenu.this, "No mobile phone is provided", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
