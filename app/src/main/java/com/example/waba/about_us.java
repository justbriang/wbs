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
    private String user_id;
    public Context c;
    private FirebaseRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        thist = view.findViewById(R.id.hist);
        view.findViewById(R.id.driver);
        view.findViewById(R.id.location);
        view.findViewById(R.id.cash);
        view.findViewById(R.id.date);
        view.findViewById(R.id.driver1);
        view.findViewById(R.id.cash1);
        view.findViewById(R.id.location1);
        view.findViewById(R.id.date1);
        view.findViewById(R.id.root);
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        db = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child(user_id).child("history");
        db.keepSynced(true);
        thist.setHasFixedSize(true);
        thist.setLayoutManager(new LinearLayoutManager(getContext()));
        fetch();

        return view;

    }


    public void fetch() {
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child("customer").child(user_id).child("history");

        FirebaseRecyclerOptions<historyad> options =
                new FirebaseRecyclerOptions.Builder<historyad>()
                        .setQuery(query,
                                new SnapshotParser<historyad>() {
                            @NonNull
                            @Override
                            public historyad parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new historyad(
                                        snapshot.child("cash").getValue().toString(),
                                        snapshot.child("date").getValue().toString(),
                                        snapshot.child("driver").getValue().toString(),
                                        snapshot.child("location").getValue().toString());
                            }
                        }
                        )
                        .build();

        adapter = new FirebaseRecyclerAdapter<historyad, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.history, parent, false);

                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(ViewHolder holder, final int position, historyad model) {
                holder.setCasher(model.getCash());
                holder.setDateer(model.getDate());
                holder.setDriverer(model.getDriver());
                holder.setLocationer(model.getLocation());

                holder.histxml.setOnClickListener(new View.OnClickListener() {
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

