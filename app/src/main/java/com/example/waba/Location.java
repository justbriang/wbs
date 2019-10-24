package com.example.waba;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Location extends Fragment {
    public Button logout1,logout;

    public EditText tdriver, tlocation, tcash, tdate;
    private DatabaseReference dbRef;
    private ImageView imageview, imageview2, imageview3, imageview4, imageview5;
    private FirebaseAuth mAuth;
   private historyad histor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_location, container, false);
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();

        dbRef = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child(user_id).child("history");


        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    historyad u = dataSnapshot.getValue(historyad.class);
                    String location = u.getLocation();
                    String cash = u.getCash();
                    String date = u.getDate();
                    String driver = u.getDriver();

                    tdriver.setText(driver);
                    tlocation.setText(location);
                    tcash.setText(cash);
                    tdate.setText(date);
                } else {
                   // Toast.makeText(getActivity(), "no data exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        logout = view.findViewById(R.id.logout);

        logout1 = view.findViewById(R.id.up);



        tdriver = view.findViewById(R.id.drivert);

        imageview2 = view.findViewById(R.id.image2);
        tcash = view.findViewById(R.id.casht);
        imageview3 = view.findViewById(R.id.image3);
        tdate = view.findViewById(R.id.datet);
        imageview4 = view.findViewById(R.id.image4);
        tlocation = view.findViewById(R.id.locationt);
        imageview5 = view.findViewById(R.id.image5);
        histor = new historyad();
        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String driver = tdriver.getText().toString().trim();
                String name = tcash.getText().toString().trim();
                String Phonenumber = tdate.getText().toString().trim();
                String home = tlocation.getText().toString().trim();


                histor.setDriver(tdriver.getText().toString().trim());
                histor.setCash(tcash.getText().toString().trim());
                histor.setDate(tdate.getText().toString().trim());
                histor.setLocation(tlocation.getText().toString().trim());
                Toast.makeText(getActivity(), "your data has been entered", Toast.LENGTH_LONG).show();
                dbRef.setValue(histor);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);

                startActivity(intent);


            }
        });






        return view;
    }
}

