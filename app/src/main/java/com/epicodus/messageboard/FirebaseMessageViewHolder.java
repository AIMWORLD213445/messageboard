package com.epicodus.messageboard;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;



/**
 * Created by Guest on 12/5/16.
 */
public class FirebaseMessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final int MAX_WIDTH = 200;
    private static final int MAX_LENGTH = 200;

    View mView;
    Context mContext;

    public FirebaseMessageViewHolder (View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindCategory(Category category) {
        TextView nameTextView = (TextView) mView.findViewById(R.id.categoryName);

        nameTextView.setText(category.getName());

    }

    @Override
    public void onClick(View view){
        final ArrayList<Message> messages = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_MESSAGE);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    messages.add(snapshot.getValue(Message.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, CategoryActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("categories", Parcels.wrap(messages) );

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError){

            }
        });

    }
}
