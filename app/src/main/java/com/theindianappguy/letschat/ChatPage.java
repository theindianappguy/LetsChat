package com.theindianappguy.letschat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theindianappguy.letschat.CustomClasses.ChatMessageClass;
import com.theindianappguy.letschat.helpingClasses.Functions;
import com.theindianappguy.letschat.helpingClasses.SessionManagement;

public class ChatPage extends AppCompatActivity {

    /*UI Components*/
    ImageView sendMessage;
    LinearLayout backBtn;
    EditText messageEt;
    SessionManagement cookie;

    /*Variables*/
    DatabaseReference databaseReference;
    String friendsPhonenumberString = "", profilePictureUrl = "", statusString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        friendsPhonenumberString = getIntent().getStringExtra("friendsPhonenumber");
        profilePictureUrl = getIntent().getStringExtra("profilePicture");
        statusString = getIntent().getStringExtra("status");

        backBtn = findViewById(R.id.backBtn);
        sendMessage = findViewById(R.id.sendMessage);
        messageEt = findViewById(R.id.messageEt);

        cookie = new SessionManagement(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("ChatRooms");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessageClass chatMessageClass = new ChatMessageClass();
                chatMessageClass.setMessage(""+messageEt.getText());
                chatMessageClass.setTime(Functions.getData_yyyy_MM_dd_hh_mm_ss());
                // send, received, seen
                chatMessageClass.setStatus("send");
                /*databaseReference*/
                String chatroomKey = cookie.getKeyUserphonenumber().concat("").concat(friendsPhonenumberString);
                databaseReference.child(cookie.getKeyUserphonenumber()).child(Functions.getRandomString(16));
            }
        });
    }
}
