package com.example.waba;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;

public class about_us extends Fragment {
    private RecyclerView thist;
    private DatabaseReference db;

    private FirebaseAuth mAuth;
    public List<historyad> historyadList;

    public Context c;
    private FirebaseRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        view.findViewById(R.id.root);

        view.findViewById(R.id.driver);

        view.findViewById(R.id.location);

        view.findViewById(R.id.cash);
        view.findViewById(R.id.date);
        view.findViewById(R.id.driver1);
        view.findViewById(R.id.cash1);
        view.findViewById(R.id.location1);
        view.findViewById(R.id.date1);
        view.findViewById(R.id.root);



//        mAuth = FirebaseAuth.getInstance();
//        String user_id = mAuth.getCurrentUser().getUid();
//
//        db = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child(user_id).child("history");
//        db.keepSynced(true);

        ;
//
        thist = view.findViewById(R.id.hist);
        thist.setHasFixedSize(true);
        thist.setLayoutManager(new LinearLayoutManager(getContext()));
//
//
//
//
//        historyadList = new ArrayList<historyad>();

//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
////                        historyad u = dataSnapshot1.getValue(historyad.class);
//
//                        historyad u=new historyad();
//
//                        u.setDriver(dataSnapshot1.child("driver").getValue(String.class));
//                        u.setCash(dataSnapshot1.child("cash").getValue(String.class));
//                        u.setDate(dataSnapshot1.child("date").getValue(String.class));
//                        u.setLocation(dataSnapshot1.child("location").getValue(String.class));
//                        historyadList.add(u);
//
//
//
//                    }
//                    adapter=new historyAdapter(getActivity(),historyadList);
//                    thist.setAdapter(adapter);
//                } else {
//                    Toast.makeText(getActivity(), "no history  exists", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        fetch();
        return view;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tdriver1, tdriver, tcash, tdate, tlocation;
        private LinearLayout thisto;

        public ViewHolder(View itemView) {
            super(itemView);
            thisto = itemView.findViewById(R.id.root);

            tdriver = itemView.findViewById(R.id.driver);

            tlocation = itemView.findViewById(R.id.location);

            tcash = itemView.findViewById(R.id.cash);
            tdate = itemView.findViewById(R.id.date);

        }

        public void setTdriver1(String driver) {
            tdriver.setText(driver);
        }


        public void setTcash(String cash) {
            tcash.setText( cash);
        }


        public void setTdate(String date) {
            tdate.setText( date);
        }


        public void setTlocation(String location) {
            tlocation.setText(location);
        }


    }

    private void fetch() {
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child(user_id).child("history");


//        FirebaseRecyclerOptions<historyad> options =
//                new FirebaseRecyclerOptions.Builder<historyad>()
//                        .setQuery(query, historyad.class)
//                        .build();
        FirebaseRecyclerOptions<historyad> options =
                new FirebaseRecyclerOptions.Builder<historyad>()
                        .setQuery(query, historyad.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<historyad, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.history, parent, false);

                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, historyad histo) {


               holder.setTdriver1(histo.getDriver());
//                holder.tdriver.setText(histo.getDriver());
//                holder.tdate.setText(histo.getDate());
//                holder.tcash.setText(histo.getCash());
//                holder.tlocation.setText(histo.getLocation());
                holder.setTlocation(histo.getLocation());
                holder.setTcash(histo.getCash());
               holder.setTdate(histo.getDate());

                holder.thisto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        };


        thist.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
