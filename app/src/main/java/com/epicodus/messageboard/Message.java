package com.epicodus.messageboard;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 12/6/16.
 */

@Parcel
public class Message {
    private String name;
    private String category;

    public Message(String names , String category) {

        this.name = name;
        this.category = category;
    }

    public Message() {

    }

    public String getName() {
        return name;

    }

}

