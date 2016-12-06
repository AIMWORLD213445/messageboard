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

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ValueEventListener mMessageReferenceListener;
    private DatabaseReference mMessageReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String mRecentCategory;


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

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentCategory = mSharedPreferences.getString(Constants.PREFERENCES_KEY_CATEGORY, null);



        mEditor = mSharedPreferences.edit();

        mMessageButton.setOnClickListener(this);

        mMessageReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MESSAGE);
        setUpFirebaseAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
                public boolean onQueryTextSubmit(String query) {
                addToSharedPreferences(query);

                for(int i = 0; i < messages.size() ; i++) {

                    Log.d("Query" , query);
                    Log.d("message" , messages.get(i).substring(6, messages.get(i).length() - 1 )) ;

                    if(query.equals(messages.get(i).substring(6, messages.get(i).length()-1 ) ))
                    {
                        Log.d("Enter", query);
                        getCategories(query);
                    }
                }

                return false;
            }

            @Override
                    public boolean onQueryTextChange(String newText){
                return false;
            }
        });

        return true;
    }

    @Override
    public void onClick(View v){
        if(v == mMessageButton){
            String message = mMessageEditText.getText().toString();

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

    private void addToSharedPreferences(String category){
        mEditor.putString(Constants.PREFERENCES_KEY_CATEGORY, category).apply();
    }

    private void getCategories(String query){
        Intent intent = new Intent(MainActivity.this, CategoryActivity.class);

        startActivity(intent);
    }

}
