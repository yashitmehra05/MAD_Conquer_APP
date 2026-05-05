package com.example.conquer_app.models;

public class MessageThread {
    private String requestId;
    private String name;
    private String handle;
    private String lastMessage;
    private int avatarResId;

    public MessageThread(String requestId, String name, String handle,
                         String lastMessage, int avatarResId) {
        this.requestId   = requestId;
        this.name        = name;
        this.handle      = handle;
        this.lastMessage = lastMessage;
        this.avatarResId = avatarResId;
    }

    public String getRequestId()   { return requestId; }
    public String getName()        { return name; }
    public String getHandle()      { return handle; }
    public String getLastMessage() { return lastMessage; }
    public int getAvatarResId()    { return avatarResId; }
}