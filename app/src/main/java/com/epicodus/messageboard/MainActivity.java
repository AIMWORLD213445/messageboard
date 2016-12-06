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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ValueEventListener mMessageReferenceListener;
    private DatabaseReference mMessageReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    private ArrayList<String> messages = new ArrayList<>();

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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMessageButton.setOnClickListener(this);

        mMessageReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MESSAGE);
        setUpFirebaseAdapter();
    }

    @Override
    public void onClick(View v){
        if(v == mMessageButton){
            String message = mMessageEditText.getText().toString();
            List<String> messages = new ArrayList<>();

            Category category = new Category(message, messages );
            Log.d("Test name" , category.getName());

            saveMessageToFirebase(category);
        }
    }

    public void saveMessageToFirebase(Category message){
        mMessageReference.push().setValue(message);
    }

    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Category, FirebaseCategoryViewHolder>
                (Category.class, R.layout.category_item_list, FirebaseCategoryViewHolder.class, mMessageReference) {
            @Override
            protected void populateViewHolder(FirebaseCategoryViewHolder viewHolder,
                                              Category model, int position){
                viewHolder.bindCategory(model);
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

}
