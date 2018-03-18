package com.omarfaroke.trainingtask4_omar;


/**
 * Created omarfaroke hp on 3/14/2018.
 */

public class MessageChat {


    public static final int STATE_WAITING=0;
    public static final int STATE_SEND_DON=1;
    public static final int STATE_READ_DON=2;

    public static final int TYPE_MESSAGE_TEXT_FROM_ME=10;
    public static final int TYPE_MESSAGE_PHOTO_FROM_ME=11;
    public static final int TYPE_MESSAGE_VIDEO_FROM_ME=12;
    public static final int TYPE_MESSAGE_TEXT_FROM_FRIEND=20;
    public static final int TYPE_MESSAGE_PHOTO_FROM_FRIEND=21;
    public static final int TYPE_MESSAGE_VIDEO_FROM_FRIEND=22;



    private String messageText;

    private long messageTime;

    private int messageStatus;

    private int messageType;

    private boolean messageFromMe;


    // message from me
    public MessageChat(String msgText ,int msgType ){
        messageText=msgText;
        messageType=msgType;
        messageFromMe=true;
        messageStatus=STATE_WAITING;
        messageTime=System.currentTimeMillis();

    }


    // message from friend
    public MessageChat(String msgText ,int msgType , long msgTime  ){
        messageText=msgText;
        messageType=msgType;
        messageTime=msgTime;
        messageFromMe=false;

    }


    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageText() {
        return messageText;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public int getMessageStatus() {
        return messageStatus;
    }

    public boolean isMessageFromMe() {
        return messageFromMe;
    }

    public int getMessageType() {return messageType;}



}
