package com.sanghiang.firebasechat;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private String temp_key;
//    Button btnCreateRoom, btnIntent;
//    EditText etRoomName, etUsername;
    TextInputEditText etUsername;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = (TextInputEditText) findViewById(R.id.editTextUsername);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etUsername.getText().toString().trim().equals("")){
                    Intent I = new Intent(getApplicationContext(),ChatRoom.class);
                    I.putExtra("room_name","RobertRoom");
                    I.putExtra("user_name",etUsername.getText().toString());
                    startActivity(I);
                }else{
                    Toast.makeText(getApplicationContext(),"Username is required",Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*btnCreateRoom = (Button) findViewById(R.id.btnCreateRoom);
        etRoomName = (EditText) findViewById(R.id.etRoomName);
        btnIntent = (Button) findViewById(R.id.btnIntent);
        etUsername = (EditText) findViewById(R.id.etUsername);
        root = FirebaseDatabase.getInstance().getReference().child("TEST");
        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<String,Object>();
                map.put(etRoomName.getText().toString(),"");
                root.updateChildren(map);

            }
        });
        btnIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etUsername.getText().toString().trim().equals("")){
                    Intent I = new Intent(getApplicationContext(),ChatRoom.class);
                    I.putExtra("room_name","RobertRoom");
                    I.putExtra("user_name",etUsername.getText().toString());
                    startActivity(I);
                }else{
                    Toast.makeText(getApplicationContext(),"Username is required",Toast.LENGTH_SHORT).show();
                }

            }
        });*/


    }
}
