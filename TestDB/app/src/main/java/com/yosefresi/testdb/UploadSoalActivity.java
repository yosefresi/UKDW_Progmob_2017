package com.yosefresi.testdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.text.DateFormat;

//@TargetApi(24)
public class UploadSoalActivity extends AppCompatActivity {
    private Button btnUploadFinal;
    private EditText txtUpJudul;
    private EditText txtUpCat;
    private EditText txtUpClass;
    private EditText txtUpKet;
    private List<Soal> soalList = new ArrayList<Soal>();
    String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

    FirebaseDatabase db;
    //child reference
    DatabaseReference dbSoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_soal);

        txtUpCat = (EditText) findViewById(R.id.txtUpCat);
        txtUpJudul = (EditText) findViewById(R.id.txtUpJudul);
        txtUpKet = (EditText) findViewById(R.id.txtUpKet);
        txtUpClass = (EditText) findViewById(R.id.txtUpClass);

        db = FirebaseDatabase.getInstance();
        dbSoal = db.getReference("soal");

        btnUploadFinal = (Button) findViewById(R.id.btnUploadFinal);
        btnUploadFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbSoal.push().setValue(new Soal(
                        txtUpJudul.getText().toString(),
                        txtUpCat.getText().toString(),
                        txtUpClass.getText().toString(),
                        txtUpKet.getText().toString(),
                        currentDateTimeString,
                        getIntent().getStringExtra("USERNAME"),
                        "0"));
                finish();
            }
        });
    }

    private void goToBankSoal(){
        Intent intent = new Intent(this, SoalActivity.class);
        startActivity(intent);
        finish();
    }
}
