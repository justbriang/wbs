package com.example.waba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import static androidx.constraintlayout.widget.Constraints.TAG;


public class profile extends Fragment {
    private FirebaseAuth mAuth;

    private Button update;
    private ImageView imageview, imageview2, imageview3, imageview4, imageview5;
    private TextView textView, textView2;
    private EditText Temail, Tmobile, Tname, Thome;
    private DatabaseReference dbRef;
    private int nextClassifiedID;
    private String adId;
    private boolean isEdit;
    String user_id = mAuth.getCurrentUser().getUid();
    Profilead profilead;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        dbRef = FirebaseDatabase.getInstance().getReference().child("user").child("customer");

        update = view.findViewById(R.id.button1);
        imageview = view.findViewById(R.id.image1);
        textView = view.findViewById(R.id.text1);
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

                String email = Temail.getText().toString().trim();
                String name = Tname.getText().toString().trim();
                String mobile = Tmobile.getText().toString().trim();
                String home = Thome.getText().toString().trim();
                profilead.setEmail(Temail.getText().toString().trim());
                profilead.setHome(Thome.getText().toString().trim());
                profilead.setMobile(Tmobile.getText().toString().trim());
                profilead.setName(Tname.getText().toString().trim());




            }

        });


        return view;
    }}

//    private void addprofiledetails() {
//        String email = Temail.getText().toString().trim();
//        String name = Tname.getText().toString().trim();
//        String mobile = Tmobile.getText().toString().trim();
//        String home = Thome.getText().toString().trim();
//
//        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(mobile) && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(home)) {
//            String user_id = mAuth.getCurrentUser().getUid();
//            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child("profile").child(user_id);
//            Map newpost = new HashMap<>();
//            newpost.put("user_id", user_id);
//            newpost.put("username", name);
//            newpost.put("phonenumber", mobile);
//            newpost.put("email", email);
//            newpost.put("home", home);
//
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference mReference = database.getReference(Constants.);
////          current_user_db.setValue(newpost);
//            mReference.child(String.valueOf(new Date().getTime())).setValue(newpost);
//            Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_LONG).show();
//
//
//        } else {
//
//            Toast.makeText(getActivity(), "you have to fill in all fields", Toast.LENGTH_LONG).show();
//
//        }
//
//    }


//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    public void addEvent() {
//        Profilead profileadobj = createProfileadobj();
//        addprofiletodb(profileadobj);
//    }
//    public void updateEvent() {
//        Profilead classifiedAd = createprofileadobj();
//        updateToDB(classifiedAd);
//    }
//
//    private void  addprofiletodb(final Profilead profileadobj) {
//
//        String user_id=mAuth.getCurrentUser().getUid();
//        final DatabaseReference idDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child("profile").child(user_id);
//
//        idDatabaseRef.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                //create id node if it doesn't exist
//                //this code runs only once
//                if (mutableData.getValue(int.class) == null) {
//                    idDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            //set initial value
//                            if (dataSnapshot != null && dataSnapshot.getValue() == null) {
//                                idDatabaseRef.setValue(1);
//                                Log.d(TAG, "Initial id is set");
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//                    Log.d(TAG, "Classified id null so " +
//                            " transaction aborted, ");
//
//                    return Transaction.abort();
//                }
//
//                nextClassifiedID = mutableData.getValue(int.class);
//                mutableData.setValue(nextClassifiedID + 1);
//                return Transaction.success(mutableData);
//
//            }
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean state,
//                                   DataSnapshot dataSnapshot) {
//                if (state) {
//                    Log.d(TAG, "Classified id retrieved ");
//                    addprofile(profileadobj, ""+nextClassifiedID);
//                } else {
//                    Log.d(TAG, "Classified id retrieval unsuccessful " + databaseError);
//                    Toast.makeText(getActivity(),
//                            "There is a problem, please submit ad post again",
//                            Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//
//        });
//    }
//    private void addprofile(Profilead profileadobj, String user_id1) {
//        profileadobj.setAdId(user_id1);
//        dbRef.child("classified").child(user_id1)
//                .setValue(profileadobj)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            if(isEdit){
//                                addprofile();
//                            }else{
//                                restUi();
//                            }
//                            Log.d(TAG, "Classified has been added to db");
//                            Toast.makeText(getActivity(),
//                                    "Classified has been posted",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Log.d(TAG, "Classified couldn't be added to db");
//                            Toast.makeText(getActivity(),
//                                    "Classified could not be added",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//    private void populateUpdateAd() {
//
//        Temail.setText("Edit Ad");
//
//        Tmobile.setText("Edit Ad");
//
//        Thome.setText("Edit Ad");
//
//        Tname.setText("Edit Ad");
//        isEdit = true;
//
//        dbRef.child("users").child(user_id).
//                addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                       Profilead userp= dataSnapshot.getValue(Profilead.class);
//                        displayAdForUpdate(userp);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Log.d(TAG, "Error trying to get classified ad for update " +
//                                ""+databaseError);
//                        Toast.makeText(getActivity(),
//                                "Please try classified edit action again",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//    }
//    private void displayAdForUpdate(Profilead cAd){
//        ((EditText) getActivity()
//                .findViewById(R.id.email)).setText(cAd.getemail());
//        ((EditText) getActivity()
//                .findViewById(R.id.home)).setText(cAd.gethome());
//        ((EditText) getActivity()
//                .findViewById(R.id.mobile)).setText(cAd.getmobile());
//        ((EditText) getActivity()
//                .findViewById(R.id.name)).setText(cAd.getname());
//
//    }
//    private void updateToDB(Profilead profileadobj) {
//        addprofile(profileadobj, user_id);
//    }
//    Private Profilead createprofileadobj() {
//        final Profilead ad = new Profilead();
//        ad.setemail(((EditText) getActivity()
//                .findViewById(R.id.email)).getText().toString());
//        ad.sethome(((EditText) getActivity()
//                .findViewById(R.id.home)).getText().toString());
//        ad.setmobile(((EditText) getActivity()
//                .findViewById(R.id.mobile)).getText().toString());
//        ad.setname(((EditText) getActivity()
//                .findViewById(R.id.name)).getText().toString());
//
//        return ad;
//    }
//
//    private void restUi() {
//        ((EditText) getActivity()
//                .findViewById(R.id.name)).setText("");
//        ((EditText) getActivity()
//                .findViewById(R.id.home)).setText("");
//        ((EditText) getActivity()
//                .findViewById(R.id.mobile)).setText("");
//        ((EditText) getActivity()
//                .findViewById(R.id.name)).setText("");
//
//    }
//    private void addProfile() {
//        Intent i = new Intent();
//        i.setClass(getActivity(), slidemenu.class);
//        startActivity(i);
//    }



