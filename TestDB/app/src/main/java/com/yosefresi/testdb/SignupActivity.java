package com.yosefresi.testdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private EditText txtUpUsername;
    private EditText txtUpPassword;
    private EditText txtUpNama;
    private Button btnSignupFinal;
//    private User user;

    FirebaseDatabase db;
    //child reference
    DatabaseReference dbUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtUpUsername = (EditText)findViewById(R.id.txtUpUsername);
        txtUpPassword = (EditText)findViewById(R.id.txtUpPassword);
        txtUpNama = (EditText)findViewById(R.id.txtUpNama);
        btnSignupFinal = (Button)findViewById(R.id.btnSignupFinal);

        //inisiasi db
        db = FirebaseDatabase.getInstance();
        dbUser = db.getReference("user");

        btnSignupFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbUser.push().setValue(new User(
                        txtUpUsername.getText().toString(),
                        txtUpPassword.getText().toString(),
                        txtUpNama.getText().toString()));
                Toast.makeText(SignupActivity.this, "Berhasil Sign Up", Toast.LENGTH_SHORT).show();
                goToLogin();
            }
        });
    }

    private void goToLogin(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
