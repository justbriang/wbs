package com.example.waba;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class contact_driver extends Fragment {
    private static final int REQUEST_CALL=1;
    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private EditText phonenumber;
    private ImageView imageView;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_driver,container,false);
        phonenumber=view.findViewById(R.id.phone);
        imageView=view.findViewById(R.id.call);
        mAuth = FirebaseAuth.getInstance();
        String user_id=mAuth.getCurrentUser().getUid();

        dbRef = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child(user_id).child("profile");




        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Profilead u = dataSnapshot.getValue(Profilead.class);

                    String mobile = u.getMobile();

                    phonenumber.setText(mobile);

                }else{
                    Toast.makeText(getActivity(), "No mobile phone is provided", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
           imageView.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {
                   makecall();
               }


           });


        return view;

    }
    private void makecall() {
        String number=phonenumber.getText().toString();
        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)!=
            PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

            }else{
                String dial="tel:";

                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }else{
            Toast.makeText(getActivity(), "No mobile phone is provided", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makecall();
            }else{
                Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}

