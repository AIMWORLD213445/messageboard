package com.epicodus.messageboard;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

    public void bindMessage(MessageText message) {
        TextView nameTextView = (TextView) mView.findViewById(R.id.categoryName);

        nameTextView.setText(message.getName());
    }

    @Override
    public void onClick(View view){


    }
}
