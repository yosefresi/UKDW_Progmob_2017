package com.yosefresi.testdb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.sql.Time;

public class MainActivity extends AppCompatActivity {
    //deklarasi shared preferences
    private SharedPreferences sp; //untuk baca data
    private SharedPreferences.Editor spEdit; //untuk tulis data
    //deklarasi komponen layout
    private TextView txtNamaHome;
    private EditText txtUser;
    private EditText txtPass;
    private Button btnLogin;
    private Button btnLogout;
    private Button btnSignup;
    private Button btnBankSoal;
    private Button btnFriendlist;
    private Button btnSearch;
    private Button btnTimeline;
    //login info
    private boolean flag = false;
    private String name = "";
    private String key = "";

    //Firebase
    FirebaseDatabase db;
    DatabaseReference dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inisiasi db
        db = FirebaseDatabase.getInstance();
        dbUser = db.getReference("user");

        //inisialisasi shared preferences
        sp = getSharedPreferences("grupo",MODE_PRIVATE);
        spEdit = sp.edit();
        if(sp.getBoolean("isLogin",false)){
            setContentView(R.layout.home);
            txtNamaHome = (TextView)findViewById(R.id.txtNamaHome);
            txtNamaHome.setText(getIntent().getStringExtra("USERNAME"));
            btnBankSoal = (Button) findViewById(R.id.btnBankSoal);
            btnBankSoal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToBankSoal();
                }
            });
            btnFriendlist = (Button) findViewById(R.id.btnFriendList);
            btnFriendlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToFriendlist();
                }
            });
            btnTimeline = (Button) findViewById(R.id.btnTimeline);
            btnTimeline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToTimeline();
                }
            });
            btnSearch = (Button) findViewById(R.id.btnSearch);
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToSearch();
                }
            });
            btnLogout = (Button)findViewById(R.id.btnLogout);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout();
                }
            });

        }else{
            setContentView(R.layout.activity_main);
            txtUser = (EditText)findViewById(R.id.txtUsername);
            txtPass = (EditText)findViewById(R.id.txtPassword);
            btnSignup = (Button)findViewById(R.id.btnSignUp);
            btnSignup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signUp();
                }
            });
            btnLogin = (Button)findViewById(R.id.btnSignIn);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

        }
    }

    private boolean checkLogin(){
        final String username = txtUser.getText().toString();
        final String password = txtPass.getText().toString();
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    if (username.equals(data.child("username").getValue().toString()) &&
                            password.equals(data.child("password").getValue().toString())){
                        flag = true;
                        name = data.child("nama").getValue().toString();
                        key = data.getKey();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return flag;
    }

    private void login(){
        if(checkLogin()){
            spEdit.putBoolean("isLogin",true);
            spEdit.commit();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("USERNAME",name);
            intent.putExtra("KEY",key);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"Username / Password salah",Toast.LENGTH_SHORT).show();
        }
    }

    private void signUp(){
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private void logout(){
        spEdit.putBoolean("isLogin",false);
        spEdit.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToBankSoal(){
        Intent intent = new Intent(this, SoalActivity.class);
        intent.putExtra("USERNAME",getIntent().getStringExtra("USERNAME"));
        startActivity(intent);
    }

    private void goToFriendlist(){
        Intent intent = new Intent(this, FriendActivity.class);
        intent.putExtra("KEY",getIntent().getStringExtra("KEY"));
        startActivity(intent);
    }

    private void goToTimeline(){
        Intent intent = new Intent(this, TimelineActivity.class);
        intent.putExtra("USERNAME",getIntent().getStringExtra("USERNAME"));
        startActivity(intent);
    }

    private void goToSearch(){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("USERNAME",getIntent().getStringExtra("USERNAME"));
        startActivity(intent);
    }
}
