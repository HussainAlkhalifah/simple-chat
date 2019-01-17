package com.example.pc.simplechat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    private ListView mListview;
    private ArrayAdapter<Message> mAdapter;
    private ArrayList<Message> mMessageList;
    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseRef;
    private TextInputEditText mEditView;
    private FloatingActionButton mSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        mListview = findViewById(R.id._listview);
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        // gets u the root
        mDatabaseRef = mDatabase.getReference();
        mEditView = (TextInputEditText) findViewById(R.id.editView);
        mSendButton = (FloatingActionButton) findViewById(R.id.sendBtnView);
        mMessageList = new ArrayList<Message>();
        mAdapter = new ArrayAdapter<Message>(this,R.layout.msg_item,R.id.textViewMsg,mMessageList);
        mListview.setAdapter(mAdapter);
        mDatabaseRef.child("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // it iterates through all children of message
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String msg = ds.child("content").getValue(String.class);
                   // String email = ds.child("user_email").getValue(String.class);
                   // String composed =   msg;

                    //Log.d("TAG", msg);
                    if(msg !=null) {
                        mMessageList.add(new Message(msg));
                        mAdapter.notifyDataSetChanged();
                        scrollMyListViewToBottom();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditView.getText().toString();
                String email = mCurrentUser.getEmail();
                String composed = email + "\n"+text;
                Message msg = new Message(composed);
                mEditView.getText().clear();
                // adding a child
                mDatabaseRef.child("message").push().setValue(msg);
               // mMessageList.add(msg);
                mAdapter.notifyDataSetChanged();
                scrollMyListViewToBottom();


            }
        });

    }
    private void scrollMyListViewToBottom() {
        mListview.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                mListview.setSelection(mAdapter.getCount() - 1);
            }
        });
    }

}
