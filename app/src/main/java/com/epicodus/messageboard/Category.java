package com.epicodus.messageboard;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 12/5/16.
 */

@Parcel
public class Category {
    private String name;
    List<String> mMessages = new ArrayList<>();

    public Category(String name, List messages) {
        this.name = name;
        this.mMessages = messages;
    }

    public Category() {

    }

    public String getName() {
        return name;

    }

    public List getMessages() {
        return mMessages;
    }
}

