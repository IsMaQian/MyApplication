package com.example.mq.nine_patch;

/**
 * Created by Administrator on 2016/12/19.
 */

public class Msg {
    public static final int TYPE_RECEIVE = 0;
    public static final int TYPE_SENT = 1;
    private String content;
    private int type;

    public Msg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
