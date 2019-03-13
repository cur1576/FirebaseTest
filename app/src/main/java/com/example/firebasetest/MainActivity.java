package com.example.firebasetest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText input, user;
    TextView output;

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference childReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
        user = findViewById(R.id.user);
        output = findViewById(R.id.output);
        output.setText("");
        Message fromServer = new Message();


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("messages");
//        childReference = reference.child("message");


//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Message value = dataSnapshot.getValue(Message.class);
//                if(value!=null){
//                    output.setText(value.getUser() + ": " + value.getText());
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(MainActivity.this, "Fehler beim lesen der Daten", Toast.LENGTH_SHORT).show();
//            }
//        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message value = dataSnapshot.getValue(Message.class);
                if(value!=null){
                    output.append(value.getUser() + ": " + value.getText() + "\n");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void send(View view) {
        Message message = new Message(user.getText().toString(),input.getText().toString());
        childReference = reference.push();
        childReference.setValue(message);
    }
}
