package com.epicodus.messageboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;

public class CategoryActivity extends AppCompatActivity {

    @Bind(R.id.nameTextView) TextView mNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }
}
