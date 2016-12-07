package com.epicodus.messageboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private ValueEventListener mMessageReferenceListener;
    private DatabaseReference mMessageReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    private ArrayList<String> messages = new ArrayList<>();

    private ArrayList<Category> mCategories = new ArrayList<>();

    int positionNet;

    @Bind(R.id.messageButton) Button mMessageButton;
    @Bind(R.id.messageEditText) EditText mMessageEditText;
    @Bind(R.id.listView) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        mMessageReference = FirebaseDatabase
//                .getInstance()
//                .getReference()
//                .child(Constants.FIREBASE_CHILD_MESSAGESTEXT);
//
//        mMessageReferenceListener=
//                mMessageReference.addValueEventListener(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
//                            String message = messageSnapshot.getValue().toString();
//                            Log.d("messages updated", "message: " + message);
//
//                            messages.add(message);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        mCategories = Parcels.unwrap(getIntent().getParcelableExtra("categories"));

        positionNet = getIntent().getIntExtra("position" , 0);

        mMessageButton.setOnClickListener(this);

//        mMessageReference = FirebaseDatabase
//                .getInstance()
//                .getReference()
//                .child(Constants.FIREBASE_CHILD_MESSAGESTEXT);

//        mMessageReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MESSAGESTEXT);
//        setUpFirebaseAdapter();
    }



    @Override
    public void onClick(View v){
        if(v == mMessageButton){
            String messageText = mMessageEditText.getText().toString();

            MessageText message = new MessageText( messageText , mCategories.get(positionNet).getName() );
            Log.d("Test name" , message.getName());

            mCategories.get(positionNet).addMessage(message);


//
//            DatabaseReference messageRef = FirebaseDatabase
//                    .getInstance()
//                    .getReference(Constants.FIREBASE_CHILD_MESSAGESTEXT);
//            messageRef.push().setValue(message);

            mListView = (ListView) findViewById(R.id.listView);

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mCategories.get(positionNet).getMessages());
            mListView.setAdapter(adapter);
        }
    }

    public void saveMessageToFirebase(MessageText message){
        mMessageReference.push().setValue(message);
    }


    private void getCategories(String query , int i ){

    }

}


