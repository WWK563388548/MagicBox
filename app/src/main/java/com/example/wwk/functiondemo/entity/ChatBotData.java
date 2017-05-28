package com.example.wwk.functiondemo.entity;

/**
 * Created by wwk on 17/5/28.
 *
 * Class of Chat list
 */

public class ChatBotData {

    // Type: it's can distinguish left and right
    private int type;
    // Text of dialogue
    private String textOfChat;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTextOfChat() {
        return textOfChat;
    }

    public void setTextOfChat(String textOfChat) {
        this.textOfChat = textOfChat;
    }
}

