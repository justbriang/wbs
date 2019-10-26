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

import java.util.HashMap;
import java.util.Map;

public class Location extends Fragment {
    public Button logout1, logout;

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
        final String user_id = mAuth.getCurrentUser().getUid();


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

//
        tdriver = view.findViewById(R.id.drivert);

        imageview2 = view.findViewById(R.id.image2);
        tcash = view.findViewById(R.id.casht);
        imageview3 = view.findViewById(R.id.image3);
        tdate = view.findViewById(R.id.datet);
        imageview4 = view.findViewById(R.id.image4);
        tlocation = view.findViewById(R.id.locationt);
        imageview5 = view.findViewById(R.id.image5);
        histor = new historyad();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);

                startActivity(intent);


            }
        });
//
        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                dbRef = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child(user_id).child("history").push();
                map.put("location", tdriver.getText().toString());
                map.put("driver", tdriver.getText().toString());
                map.put("date", tdate.getText().toString());
                map.put("cash", tcash.getText().toString());
                dbRef.setValue(map);
            }
        });


        return view;
    }
}

