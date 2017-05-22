package com.yosefresi.testdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ItemSoalActivity extends AppCompatActivity {
    private TextView txtJudulSoal;
    private TextView txtKeterangan;
    private Button btnComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soal);

        txtJudulSoal = (TextView)findViewById(R.id.txtJudulSoal);
        txtJudulSoal.setText(getIntent().getStringExtra("JUDUL_SOAL"));
        txtKeterangan = (TextView)findViewById(R.id.txtKeterangan);
        txtKeterangan.setText(getIntent().getStringExtra("KETERANGAN"));
        btnComment = (Button)findViewById(R.id.btnComment);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToComments();
            }
        });
    }

    public void goToComments(){
        Intent intent = new Intent(this,CommentActivity.class);
        intent.putExtra("KEY_SOAL",getIntent().getStringExtra("KEY_SOAL"));
        intent.putExtra("USERNAME",getIntent().getStringExtra("USERNAME"));
        startActivity(intent);
    }
}
