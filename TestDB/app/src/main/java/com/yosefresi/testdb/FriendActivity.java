package com.yosefresi.testdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<User> friendList = new ArrayList<User>();
    private FriendAdapter friendAdapter;
    private String key = "";

    FirebaseDatabase db;
    //child reference
    DatabaseReference dbSoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend);

        key = getIntent().getStringExtra("KEY");

        //Inisialisasi RecyclerView dan Adapter
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFriend);
        friendAdapter = new FriendAdapter(friendList, this);

        //Konfigurasi RecyclerView dengan Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(friendAdapter);

        //inisiasi db
        db = FirebaseDatabase.getInstance();
        dbSoal = db.getReference("user/"+key+"/friend");

        dbSoal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lihatTeman(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void lihatTeman(DataSnapshot d){
        for (DataSnapshot ds: d.getChildren()){
            User friend = new User(
                    "",
                    "",
                    ds.child("nama").getValue().toString());
            friendList.add(friend);
        }
        friendAdapter.notifyDataSetChanged();
    }
}