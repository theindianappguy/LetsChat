package com.theindianappguy.letschat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.theindianappguy.letschat.ChatPage;
import com.theindianappguy.letschat.CustomClasses.ChatRoomListClass;
import com.theindianappguy.letschat.R;
import com.theindianappguy.letschat.helpingClasses.SessionManagement;

import java.util.ArrayList;

public class ChatRoomsListAdapter extends RecyclerView.Adapter<ChatRoomsListAdapter.MyViewHolder> {

    ArrayList<ChatRoomListClass> chatRoomListClasses ;
    Context context;
    SessionManagement cookies;

    public ChatRoomsListAdapter(Context context, ArrayList<ChatRoomListClass> chatRoomListClasses){
        this.chatRoomListClasses = chatRoomListClasses;
        this.context = context;

    }

    public class MyViewHolder extends ViewHolder {
        //
        ImageView profilePic;
        TextView userNameTv, lastChatTv, lastChatTime;
        LinearLayout mainCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cookies = new SessionManagement(context);
            profilePic = itemView.findViewById(R.id.profilePic);
            userNameTv = itemView.findViewById(R.id.userNameTv);
            lastChatTv = itemView.findViewById(R.id.lastChatTv);
            lastChatTime = itemView.findViewById(R.id.lastChatTime);
            mainCard = itemView.findViewById(R.id.mainCard);
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_card,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        if(chatRoomListClasses.get(position).getUserKey1().equals(cookies.getKeyUserkey())){
            //holder.profilePic
            holder.userNameTv.setText(chatRoomListClasses.get(position).getUserName1());

        }else if(chatRoomListClasses.get(position).getUserKey2().equals(cookies.getKeyUserkey())){

        }

        holder.lastChatTv.setText(chatRoomListClasses.get(position).getLastChatMessage());
        holder.lastChatTime.setText(chatRoomListClasses.get(position).getLastChatTime());
        holder.mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatPage.class);
                intent.putExtra("chatRoomKey","");
                if(chatRoomListClasses.get(position).getUserKey1().equals(cookies.getKeyUserkey())){
                    intent.putExtra("friendsPhonenumber",chatRoomListClasses.get(position).getPhoneNumber2());
                    intent.putExtra("profilePicture",chatRoomListClasses.get(position).getProfilepic2());
                    intent.putExtra("status",chatRoomListClasses.get(position).getUserStatus2());
                }else if(chatRoomListClasses.get(position).getUserKey2().equals(cookies.getKeyUserkey())){
                    intent.putExtra("friendsPhonenumber",chatRoomListClasses.get(position).getPhoneNumber1());
                    intent.putExtra("profilePicture",chatRoomListClasses.get(position).getProfilepic1());
                    intent.putExtra("status",chatRoomListClasses.get(position).getUserStatus1());
                }
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return chatRoomListClasses.size();
    }
}
