package com.omarfaroke.trainingtask4_omar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {


    private Context chatContext;
    private Activity chatActivity;




    private ListView listViewMessagesChat;
    private static List<MessageChat> messagesChatArrayList;
    private MessageChatAdapter_test messageChatAdapter;



    private ImageButton buttonCameraChat;
    private ImageButton buttonSendMessage;
    private EditText editTextChatMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //hide the Soft Keyboard after touch any view another editText
        setupUI(findViewById(R.id.layout_open_chat));


        //set custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //
        findViewById(R.id.back_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        configureViewsAndObjects();

        linkViewsEvents();

        configureCameraPopupWindow();
    }





    private void configureViewsAndObjects(){

        chatContext=getApplicationContext();
        chatActivity=ChatActivity.this;

        listViewMessagesChat=(ListView) findViewById(R.id.listView_messages_chat);

        //scroll the ListView to Last item  after update
        listViewMessagesChat.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        //scroll the ListView to the last item  in open a ListView (Activity)
        listViewMessagesChat.setStackFromBottom(true);



        //create a new ArrayList chat Message
        messagesChatArrayList =new ArrayList<MessageChat>();

        //link the ArrayList with Adapter
        messageChatAdapter=new MessageChatAdapter_test(this,messagesChatArrayList);

        //link the listView with Adapter
        listViewMessagesChat.setAdapter(messageChatAdapter);


        editTextChatMessage =(EditText) findViewById(R.id.edittext_chat_message);
        buttonCameraChat=(ImageButton) findViewById(R.id.button_cam_chat);
        buttonSendMessage=(ImageButton) findViewById(R.id.button_send_message);

    }






    private void linkViewsEvents(){


        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageString;

                messageString= editTextChatMessage.getText().toString();

                if (messageString.equalsIgnoreCase("")){
                    Toast.makeText(chatContext,getResources().getString(R.string.enter_the_message), Toast.LENGTH_SHORT).show();
                    return;
                }


                editTextChatMessage.setText("");

                buttonCameraChat.setVisibility(View.VISIBLE);
                buttonCameraChat.setVisibility(View.VISIBLE);
                //buttonCameraChat.refreshDrawableState();

                messagesChatArrayList.add(new MessageChat(messageString,MessageChat.TYPE_MESSAGE_TEXT_FROM_ME));

                messageChatAdapter.notifyDataSetChanged();
                hideSoftKeyboard(chatActivity);

            }
        });


        editTextChatMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                buttonCameraChat.setVisibility(View.GONE);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editTextChatMessage.getText().toString().equalsIgnoreCase( "")) {
                    buttonCameraChat.setVisibility(View.VISIBLE);
                    buttonCameraChat.setVisibility(View.VISIBLE);
                }
            }
        });


        editTextChatMessage.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    buttonSendMessage.callOnClick();
                    return true;
                }
                return false;
            }
        });


    }





    private  void configureCameraPopupWindow(){

        buttonCameraChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = getLayoutInflater();
                // Inflate the custom layout/view
                View cameraView = inflater.inflate(R.layout.layout_popup_camera,null);


                final Dialog dialog = new Dialog(chatActivity, R.style.Theme_AppCompat_Dialog_Alert);
                dialog.setContentView(cameraView);

                TextView capturePhoto = (TextView) cameraView.findViewById(R.id.capture_photo);
                TextView capturevideo = (TextView) cameraView.findViewById(R.id.capture_video);



                capturePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });


                capturevideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });



                dialog.show();
            }
        });
    }






    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    //hiding keyboard after touch any Views
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(chatActivity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.send_all_message:

                for (MessageChat msg : messagesChatArrayList) {
                    if (msg.getMessageStatus() == MessageChat.STATE_WAITING)
                        msg.setMessageStatus(MessageChat.STATE_SEND_DON);
                }

                messageChatAdapter.notifyDataSetChanged();
                break;

            case R.id.read_all_message:

                for (MessageChat msg : messagesChatArrayList)
                    msg.setMessageStatus(MessageChat.STATE_READ_DON);

                messageChatAdapter.notifyDataSetChanged();
                break;

            case R.id.repetition_last_message_from_friend:

                if(messagesChatArrayList.isEmpty())
                    break;


                int indexLastMessage =messagesChatArrayList.size()-1;

                MessageChat copyLastMessage = new MessageChat(messagesChatArrayList.get(indexLastMessage).getMessageText()
                        ,MessageChat.TYPE_MESSAGE_TEXT_FROM_FRIEND
                        , messagesChatArrayList.get(indexLastMessage).getMessageTime());



                messagesChatArrayList.add(copyLastMessage);

                messageChatAdapter.notifyDataSetChanged();
                break;

        }

        return super.onOptionsItemSelected(item);
    }


}
