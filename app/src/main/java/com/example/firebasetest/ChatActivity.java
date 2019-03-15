package com.example.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ChatActivity extends AppCompatActivity {

    EditText messageArea;
    ImageView sendButton;
    LinearLayout layout;
    RelativeLayout layout_2;
    ScrollView scrollView;

    String username,chatWith;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference1, reference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.layout1);
        layout_2 = findViewById(R.id.layout2);
        scrollView = findViewById(R.id.scrollView);
        messageArea = findViewById(R.id.messageArea);
        sendButton = findViewById(R.id.sendButton);

        username = auth.getCurrentUser().getEmail();
        chatWith = getIntent().getStringExtra("chatWith");

        database = FirebaseDatabase.getInstance();

        reference1 = database.getReference("messages/" + username + "_" + chatWith);
        reference2 = database.getReference("messages/" + chatWith + "_" + username);

        sendButton.setOnClickListener((v)->{
            String messageText = messageArea.getText().toString();
            if(!TextUtils.isEmpty(messageText)){
                Map<String, String>map = new HashMap<>();
                map.put("message",messageText);
                map.put("user",username);
                reference1.push().setValue(map);
                reference2.push().setValue(map);
                messageArea.setText("");
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                auth.signOut();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
