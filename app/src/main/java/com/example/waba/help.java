package com.example.waba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class help extends Fragment {
    TextView Tmobile ,Thome,Tname,Temail;
    ImageView img1,img2, img3,img4,img5;
    private DatabaseReference dbRef;
    private Button update;
    Profilead profilead;
    private FirebaseAuth mAuth;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help, container, false);
        mAuth = FirebaseAuth.getInstance();
     final String user_id=mAuth.getCurrentUser().getUid();
        dbRef = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child(user_id);



        update = view.findViewById(R.id.button1);
        img1= view.findViewById(R.id.imageView);



        img2 = view.findViewById(R.id.image5);
        Temail = view.findViewById(R.id.email);
        img3 = view.findViewById(R.id.image3);
        Tmobile = view.findViewById(R.id.mobile);
        img4= view.findViewById(R.id.image4);
        Thome = view.findViewById(R.id.home);
        img5=view.findViewById(R.id.imageView);
        Tname = view.findViewById(R.id.name);
        profilead=new Profilead();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dbRef.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if (dataSnapshot.exists()) {
                           Profilead u = dataSnapshot.getValue(Profilead.class);
                           String name = u.getName();
                           String email = u.getEmail();
                           String mobile = u.getMobile();
                           String home = u.getHome();
                           Temail.setText(email);
                           Tname.setText(name);
                           Tmobile.setText(mobile);
                           Thome.setText(home);
                       }else{
                           Toast.makeText(getActivity(), "no data exists", Toast.LENGTH_LONG).show();
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });







            }

        });


        return view;
    }
    }


