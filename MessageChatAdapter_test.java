package com.omarfaroke.trainingtask4_omar;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by hp on 3/14/2018.
 */

public class MessageChatAdapter_test extends BaseAdapter {

    private Context mContext;
    private List<MessageChat> mItems;


    public MessageChatAdapter_test(Context context, List<MessageChat> msgChat) {
        mContext = context;
        mItems = msgChat;
    }


    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    @Override
//    public int getViewTypeCount() {return 4;}

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getMessageType();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        int messageType = getItemViewType(position);
        View listItemMessageChat = convertView;

        if (listItemMessageChat == null) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            switch (messageType) {

                case MessageChat.TYPE_MESSAGE_TEXT_FROM_ME:
                    listItemMessageChat = inflater.inflate(
                            R.layout.item_chat_message_from_me, parent, false);
                    break;

                case MessageChat.TYPE_MESSAGE_TEXT_FROM_FRIEND:
                    listItemMessageChat = inflater.inflate(
                            R.layout.item_chat_message_from_friend, parent, false);
                    break;

                case MessageChat.TYPE_MESSAGE_PHOTO_FROM_ME:
                    listItemMessageChat = inflater.inflate(
                            R.layout.item_chat_photo_and_video_from_me, parent, false);
                    break;

                case MessageChat.TYPE_MESSAGE_PHOTO_FROM_FRIEND:
                    listItemMessageChat = inflater.inflate(
                            R.layout.item_chat_photo_and_video_from_friend, parent, false);
                    break;

                case MessageChat.TYPE_MESSAGE_VIDEO_FROM_ME:
                    listItemMessageChat = inflater.inflate(
                            R.layout.item_chat_photo_and_video_from_me, parent, false);
                    break;

                case MessageChat.TYPE_MESSAGE_VIDEO_FROM_FRIEND:
                    listItemMessageChat = inflater.inflate(
                            R.layout.item_chat_photo_and_video_from_friend, parent, false);
                    break;
            }

        }


        MessageChat msg = (MessageChat) getItem(position);


        if (msg.isMessageFromMe()) {

            ImageView imageViewMessageStatus = (ImageView) listItemMessageChat.findViewById(R.id.imageView_state_message_chat);

            switch (msg.getMessageStatus()) {
                case MessageChat.STATE_WAITING:
                    imageViewMessageStatus.setImageResource(R.drawable.msg_status_gray_waiting);
                    break;

                case MessageChat.STATE_SEND_DON:
                    imageViewMessageStatus.setImageResource(R.drawable.msg_status_server_receive);
                    break;

                case MessageChat.STATE_READ_DON:
                    imageViewMessageStatus.setImageResource(R.drawable.msg_status_client_read);
                    break;
            }

        }


        if (messageType == MessageChat.TYPE_MESSAGE_TEXT_FROM_ME || messageType == MessageChat.TYPE_MESSAGE_TEXT_FROM_FRIEND) {
            TextView textViewMessageText = (TextView) listItemMessageChat.findViewById(R.id.textView_message_chat);
            textViewMessageText.setText(msg.getMessageText());

        } else {

            ImageView imageViewMessagePhoto = listItemMessageChat.findViewById(R.id.imageView_chat_photo);

            if (messageType == MessageChat.TYPE_MESSAGE_PHOTO_FROM_ME || messageType == MessageChat.TYPE_MESSAGE_PHOTO_FROM_FRIEND) {
                //hide the button play video ^_^
                imageViewMessagePhoto.getBackground().setAlpha(0);

                //
                //imageViewMessagePhoto.setImageBitmap();


            } else if (messageType == MessageChat.TYPE_MESSAGE_VIDEO_FROM_ME || messageType == MessageChat.TYPE_MESSAGE_VIDEO_FROM_FRIEND) {
                //show the button play video ^_^
                imageViewMessagePhoto.setBackgroundResource(R.drawable.play_button_with_background);

                //
                //imageViewMessagePhoto.setImageBitmap();
            }
        }


        TextView textViewMessageTime = (TextView) listItemMessageChat.findViewById(R.id.textView_time_message_chat);

        textViewMessageTime.setText(formattingTimeMessageChat(msg.getMessageTime()));


        return listItemMessageChat;
    }



    // format time message chat
    public static String formattingTimeMessageChat(long time) {

        long now = System.currentTimeMillis();
        CharSequence ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.FORMAT_SHOW_TIME);
        SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
        String timeString = ago + "  " + format.format(time) + " ";

        return timeString;
    }

}
