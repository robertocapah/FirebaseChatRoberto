package com.sanghiang.firebasechat;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sanghiang.firebasechat.Adapter.AdapterChatWhatsapp;
import com.sanghiang.firebasechat.Model.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatRoom extends AppCompatActivity {
    private FloatingActionButton btn_send;
    private EditText et_content, et_username;
    private AdapterChatWhatsapp adapter;
    private RecyclerView recycler_view;
    private ActionBar actionBar;

    private String user_name, room_name;
    private DatabaseReference root;
    private String temp_key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_whatsapp);
        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        setTitle("Room - " + room_name);
        root = FirebaseDatabase.getInstance().getReference().child(room_name);
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

//                append_chat_conversatin(dataSnapshot);
//                sendChat(dataSnapshot);
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    String chat_msg = (String) ((DataSnapshot) i.next()).getValue();
                    String chat_user_name = (String) ((DataSnapshot) i.next()).getValue();
                    String chat_time = (String) ((DataSnapshot) i.next()).getValue();

//                    chat_conversation.append(chat_user_name + " : "+chat_msg +"\n");
                    if (!user_name.equals(chat_user_name)) {
                        adapter.insertItem(new Message(adapter.getItemCount(), chat_msg, false, adapter.getItemCount() % 5 == 0, chat_time, chat_user_name));
                        recycler_view.scrollToPosition(adapter.getItemCount() - 1);

                    } else {
                        adapter.insertItem(new Message(adapter.getItemCount(), chat_msg, true, adapter.getItemCount() % 5 == 0, chat_time, chat_user_name));
                        recycler_view.scrollToPosition(adapter.getItemCount() - 1);

                    }


                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    String chat_msg = (String) ((DataSnapshot) i.next()).getValue();
                    String chat_user_name = (String) ((DataSnapshot) i.next()).getValue();
                    adapter.insertItem(new Message(adapter.getItemCount(), chat_msg, true, adapter.getItemCount() % 5 == 0, Tools.getFormattedTimeEvent(System.currentTimeMillis()), chat_user_name));
                    }
                recycler_view.scrollToPosition(adapter.getItemCount() - 1);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int c = 1;
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        initToolbar();
        iniComponent();
    }

    public void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setTitle(null);
//        Tools.setSystemBarColorInt(this, Color.parseColor("#054D44"));
    }

    public void iniComponent() {
        recycler_view = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        recycler_view.setHasFixedSize(true);

        adapter = new AdapterChatWhatsapp(this);
        recycler_view.setAdapter(adapter);
//        adapter.insertItem(new Message(adapter.getItemCount(), "Hai..", false, adapter.getItemCount() % 5 == 0, Tools.getFormattedTimeEvent(System.currentTimeMillis())));
//        adapter.insertItem(new Message(adapter.getItemCount(), "Hello!", true, adapter.getItemCount() % 5 == 0, Tools.getFormattedTimeEvent(System.currentTimeMillis())));

        btn_send = findViewById(R.id.btn_send);
        et_content = findViewById(R.id.text_content);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendChat();
            }
        });
        et_content.addTextChangedListener(contentWatcher);

        (findViewById(R.id.lyt_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private TextWatcher contentWatcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable etd) {
            if (etd.toString().trim().length() == 0) {
                btn_send.setImageResource(R.drawable.ic_mic);
            } else {
                btn_send.setImageResource(R.drawable.ic_send);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }
    };

    private void sendChat() {
        Map<String, Object> map = new HashMap<String, Object>();

        temp_key = root.push().getKey();
        root.updateChildren(map);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat spf = new SimpleDateFormat("hh:mm");
        String hour = spf.format(currentTime);
        DatabaseReference message_root = root.child(temp_key);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("name", user_name);
        map2.put("msg", et_content.getText().toString());
        map2.put("time", hour);
        message_root.updateChildren(map2);
        et_content.setText("");
    }
}
