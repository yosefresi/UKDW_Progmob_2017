package com.yosefresi.testdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CommentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Comment> commentList = new ArrayList<Comment>();
    private CommentAdapter commentAdapter;
    private String key = "";
    private EditText txtUpComment;
    private Button btnSendComment;

    FirebaseDatabase db;
    //child reference
    DatabaseReference dbComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        txtUpComment = (EditText) findViewById(R.id.txtUpComment);
        btnSendComment = (Button) findViewById(R.id.btnSendComment);
        key = getIntent().getStringExtra("KEY_SOAL");

        //Inisialisasi RecyclerView dan Adapter
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewComment);
        commentAdapter = new CommentAdapter(commentList,this);

        //Konfigurasi RecyclerView dengan Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(commentAdapter);

        //inisiasi db
        db = FirebaseDatabase.getInstance();
        dbComment = db.getReference("soal/"+key+"/comment");

        btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comment comment = new Comment(
                        getIntent().getStringExtra("USERNAME"),
                        txtUpComment.getText().toString()
                );
                dbComment.push().setValue(comment);
                txtUpComment.setText("");
            }
        });

        dbComment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                lihatKomen(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void lihatKomen(DataSnapshot d){
        for (DataSnapshot ds: d.getChildren()){
            Comment comment = new Comment(
                    ds.child("nama").getValue().toString(),
                    ds.child("isiComment").getValue().toString());
            commentList.add(comment);
        }
        commentAdapter.notifyDataSetChanged();
    }
}
