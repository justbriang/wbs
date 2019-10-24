package com.example.waba;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.os.Build.VERSION_CODES.P;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class profile extends Fragment {
    private FirebaseAuth mAuth;
    public Uri inguri;

    private Button update;
    private EditText editText;
    private ImageView profileim, imageview2, imageview3, imageview4, imageview5, imageview;
    private TextView textView, textView2;
    private EditText Temail, Tmobile, Tname, Thome;
    private DatabaseReference dbRef,dbRe;
    private int nextClassifiedID;
    private String adId;
    private boolean isEdit;
    private RatingBar ratingBar;
    private TextView rate;
    private StorageReference storage;
    private static final int IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 100;
    Profilead profilead;
    private StorageTask uploadtask;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        profileim = view.findViewById(R.id.profileig);

        String user_id = mAuth.getCurrentUser().getUid();
        storage = FirebaseStorage.getInstance().getReference("users").child("customer").child(user_id).child("profile");

        dbRef = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child(user_id).child("profile");


        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child(user_id).child("profile").child("rating");
        dbRe = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child(user_id).child("profile").child("profile_pic");
        dbRe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String url = dataSnapshot.child("imageURL").getValue().toString();
                        Glide.with(getContext()).load(url).fitCenter().into(profileim);

                    }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    float rating = Float.parseFloat(dataSnapshot.getValue().toString());
                    ratingBar.setRating(rating);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
                } else {
                    Toast.makeText(getActivity(), "no data exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        rate = (TextView) view.findViewById(R.id.rate);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate.setText("value is " + rating);
                dbRef.child("rating").setValue(String.valueOf(rating));

            }
        });
        update = view.findViewById(R.id.button1);

        textView2 = view.findViewById(R.id.text2);
     
        imageview2 = view.findViewById(R.id.image2);
        Temail = view.findViewById(R.id.email);
        imageview3 = view.findViewById(R.id.image3);
        Tmobile = view.findViewById(R.id.mobile);
        imageview4 = view.findViewById(R.id.image4);
        Thome = view.findViewById(R.id.home);
        imageview5 = view.findViewById(R.id.image5);
        Tname = view.findViewById(R.id.name);
        profilead = new Profilead();
        profileim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                takepicture();
            }


        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //addprofiledetails();
                // final String user_id=mAuth.getCurrentUser().getUid()

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

    private void takepicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);

    }


    private String getextention(Uri uri) {
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadimage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading");
        pd.show();

        if (inguri != null) {
            final StorageReference filereference = storage.child(System.currentTimeMillis() + "." + getextention(inguri));

            uploadtask = filereference.putFile(inguri);
            uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filereference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String mUri = downloadUri.toString();
                        String user_id = mAuth.getCurrentUser().getUid();


                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);
                        dbRe.updateChildren(map);

                        pd.dismiss();
                    } else {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        } else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            inguri = data.getData();
            if (uploadtask != null && uploadtask.isInProgress()) {
                Toast.makeText(getContext(), "upload in progress", Toast.LENGTH_SHORT).show();

            } else {
                uploadimage();
            }

        }
    }


}




