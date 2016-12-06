package com.epicodus.messageboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import com.epicodus.messageboard.Category;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private ValueEventListener mMessageReferenceListener;
    private DatabaseReference mMessageReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    private ArrayList<String> messages = new ArrayList<>();

    private ArrayList<Category> mCategories = new ArrayList<>();

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Bind(R.id.messageButton) Button mMessageButton;
    @Bind(R.id.messageEditText) EditText mMessageEditText;

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
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        mCategories = Parcels.unwrap(getIntent().getParcelableExtra("categories"));

        int startingposition = Integer.parseInt(getIntent().getStringExtra("position"));

        mMessageButton.setOnClickListener(this);

        mMessageReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MESSAGE);
        setUpFirebaseAdapter();
    }



    @Override
    public void onClick(View v){
        if(v == mMessageButton){
            String messageText = mMessageEditText.getText().toString();

            Message message = new Message( messageText , "filler" );
            Log.d("Test name" , message.getName());

            saveMessageToFirebase(message);
        }
    }

    public void saveMessageToFirebase(Message message){
        mMessageReference.push().setValue(message);
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Message, FirebaseMessageViewHolder>
                (Message.class, R.layout.category_item_list, FirebaseMessageViewHolder.class, mMessageReference) {
            @Override
            protected void populateViewHolder(FirebaseMessageViewHolder viewHolder,
                                              Message model, int position){
                viewHolder.bindMessage(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }


    private void getCategories(String query , int i ){

    }

}


