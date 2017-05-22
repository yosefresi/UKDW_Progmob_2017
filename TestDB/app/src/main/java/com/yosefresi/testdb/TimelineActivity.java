package com.yosefresi.testdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TimelineActivity extends AppCompatActivity {
    FirebaseDatabase db;
    //child reference
    DatabaseReference dbSoal;
    private RecyclerView recyclerView;
    private List<Soal> soalList = new ArrayList<Soal>();
    private List<String> keyList = new ArrayList<String>();
    private SoalAdapter soalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_soal);

        //Inisialisasi RecyclerView dan Adapter
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        soalAdapter = new SoalAdapter(soalList, this, keyList, getIntent().getStringExtra("USERNAME"));

//        //Konfigurasi RecyclerView dengan Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(soalAdapter);

        //inisiasi db
        db = FirebaseDatabase.getInstance();
        dbSoal = db.getReference("soal");

        dbSoal.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                soalList.clear();
                lihatSoal(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void lihatSoal(DataSnapshot d) {
        for (DataSnapshot ds : d.getChildren()) {
            Soal soal = new Soal(
                    ds.child("judul").getValue().toString(),
                    ds.child("mapel").getValue().toString(),
                    ds.child("kelas").getValue().toString(),
                    ds.child("keterangan").getValue().toString(),
                    ds.child("tanggal").getValue().toString(),
                    ds.child("author").getValue().toString(),
                    ds.child("jumlahComment").getValue().toString()
            );
            soalList.add(soal);
            keyList.add(ds.getKey());
//            soalAdapter = new SoalAdapter(soalList,this);
//            recyclerView.setAdapter(soalAdapter);
        }
        soalAdapter.notifyDataSetChanged();
    }
}
