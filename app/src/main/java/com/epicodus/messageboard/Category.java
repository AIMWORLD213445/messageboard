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
    private List<MessageText> messages = new ArrayList<MessageText>();

    public Category(String name, List<MessageText> messages) {
        this.name = name;
        this.messages = messages;
    }

    public Category() {

    }

    public String getName() {
        return name;

    }

    public List<MessageText> getMessages() {
        return messages;
    }

    public void addMessage(MessageText message){
        messages.add(message);
    }

}

