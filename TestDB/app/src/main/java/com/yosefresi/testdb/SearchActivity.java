package com.yosefresi.testdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Soal> soalList = new ArrayList<Soal>();
    private List<String> keyList = new ArrayList<String>();
    private SoalAdapter soalAdapter;
    private EditText txtUpSearch;
    private Button btnSearch;

    FirebaseDatabase db;
    //child reference
    DatabaseReference dbSoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        txtUpSearch = (EditText)findViewById(R.id.txtUpSearch);
        btnSearch = (Button)findViewById(R.id.btnSearch);

        //Inisialisasi RecyclerView dan Adapter
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearch);
        soalAdapter = new SoalAdapter(soalList, this, keyList, getIntent().getStringExtra("USERNAME"));

//        //Konfigurasi RecyclerView dengan Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(soalAdapter);

        //inisiasi db
        db = FirebaseDatabase.getInstance();
        dbSoal = db.getReference("soal");


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbSoal.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        String q = txtUpSearch.getText().toString();
                        soalList.clear();
                        lihatSoal(dataSnapshot, q);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void lihatSoal(DataSnapshot d, String q){
        for (DataSnapshot ds: d.getChildren()){
            if (ds.child("judul").getChildren().toString().toLowerCase().contains(q)) {
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
            }
        }
        soalAdapter.notifyDataSetChanged();
    }
}
