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

    public Category(String name) {
        this.name = name;

    }

    public Category() {

    }

    public String getName() {
        return name;

    }

}

