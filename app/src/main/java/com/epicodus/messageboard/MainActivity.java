package com.epicodus.messageboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ValueEventListener mMessageReferenceListener;
    private DatabaseReference mMessageReference;



    private ArrayList<String> messages = new ArrayList<>();

    @Bind(R.id.messageButton) Button mMessageButton;
    @Bind(R.id.messageEditText) EditText mMessageEditText;
    @Bind(R.id.listView) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mMessageReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_MESSAGE);

        mMessageReferenceListener=
                mMessageReference.addValueEventListener(new ValueEventListener() {

           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                   String message = messageSnapshot.getValue().toString();
                    Log.d("messages updated", "message: " + message);
                    messages.add(message);
               }
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMessageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mMessageButton){
            String message = mMessageEditText.getText().toString();

            saveMessageToFirebase(message);

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages);
            mListView.setAdapter(adapter);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMessageReference.removeEventListener(mMessageReferenceListener);
    }


    public void saveMessageToFirebase(String message){
        mMessageReference.push().setValue(message);
    }



}
