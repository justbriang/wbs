package com.example.waba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION_CODES.P;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class profile extends Fragment {
    private FirebaseAuth mAuth;

    private Button update;
    private EditText editText;
    private ImageView imageview, imageview2, imageview3, imageview4, imageview5;
    private TextView textView, textView2;
    private EditText Temail, Tmobile, Tname, Thome;
    private DatabaseReference dbRef;
    private int nextClassifiedID;
    private String adId;
    private boolean isEdit;




    Profilead profilead;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        String user_id=mAuth.getCurrentUser().getUid();

        dbRef = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child("profile").child(user_id);





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












        update = view.findViewById(R.id.button1);
        imageview = view.findViewById(R.id.imageView);

        textView2 = view.findViewById(R.id.text2);

        imageview2 = view.findViewById(R.id.image2);
        Temail = view.findViewById(R.id.email);
        imageview3 = view.findViewById(R.id.image3);
        Tmobile = view.findViewById(R.id.mobile);
        imageview4 = view.findViewById(R.id.image4);
        Thome = view.findViewById(R.id.home);
        imageview5 = view.findViewById(R.id.image5);
        Tname = view.findViewById(R.id.name);
        profilead=new Profilead();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //addprofiledetails();
              // final String user_id=mAuth.getCurrentUser().getUid();

                String email = Temail.getText().toString().trim();
                String name = Tname.getText().toString().trim();
                String Phonenumber = Tmobile.getText().toString().trim();
                String home = Thome.getText().toString().trim();
                profilead.setEmail(Temail.getText().toString().trim());
                profilead.setHome(Thome.getText().toString().trim());
                profilead.setMobile(Tmobile.getText().toString().trim());
                profilead.setName(Tname.getText().toString().trim());
                Toast.makeText(getActivity(), "your data has been entered", Toast.LENGTH_LONG).show();
                dbRef.setValue(profilead);




            }

        });


        return view;
    }
}

