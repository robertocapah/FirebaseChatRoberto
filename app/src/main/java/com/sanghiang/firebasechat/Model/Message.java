package com.sanghiang.firebasechat.Model;

import java.io.Serializable;

public class Message implements Serializable {
    private long id;
    private String date;
    private String username;
    private String content;
    private boolean fromMe;
    private boolean showTime = true;

    public Message(long id, String content, boolean fromMe, String date, String username) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.fromMe = fromMe;
        this.username = username;
    }

    public Message(long id, String content, boolean fromMe, boolean showTime, String date, String username) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.fromMe = fromMe;
        this.showTime = showTime;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public boolean isShowTime() {
        return showTime;
    }

    public String getUsername() {
        return username;
    }
}