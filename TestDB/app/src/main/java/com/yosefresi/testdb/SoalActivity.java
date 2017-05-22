package com.yosefresi.testdb;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SoalActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Soal> soalList = new ArrayList<Soal>();
    private List<String> keyList = new ArrayList<String>();
    private SoalAdapter soalAdapter;
    private Button btnUpload;

    FirebaseDatabase db;
    //child reference
    DatabaseReference dbSoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_soal);

        //setting button upload
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUpload();
            }
        });

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

    private void lihatSoal(DataSnapshot d){
        for (DataSnapshot ds: d.getChildren()){
            int jmlKomen = 0;
            if ((ds.child("author").getValue().toString().equals(
                    getIntent().getStringExtra("USERNAME")))) {
                for (DataSnapshot dchild : d.child("comment").getChildren()){
                    jmlKomen++;
                }
                Soal soal = new Soal(
                        ds.child("judul").getValue().toString(),
                        ds.child("mapel").getValue().toString(),
                        ds.child("kelas").getValue().toString(),
                        ds.child("keterangan").getValue().toString(),
                        ds.child("tanggal").getValue().toString(),
                        ds.child("author").getValue().toString(),
                        String.valueOf(jmlKomen)
                );
                soalList.add(soal);
                keyList.add(ds.getKey());
            }
        }
        soalAdapter.notifyDataSetChanged();
    }

    private void goToUpload(){
        Intent intent = new Intent(this, UploadSoalActivity.class);
        intent.putExtra("USERNAME",getIntent().getStringExtra("USERNAME"));
        startActivity(intent);
    }
}