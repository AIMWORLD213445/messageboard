package com.epicodus.messageboard;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 12/6/16.
 */

@Parcel
public class MessageText {
    private String name;
    private String category;

    public MessageText(String name , String category) {
        this.name = name;
        this.category = category;
    }

    public MessageText() {

    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }


}

