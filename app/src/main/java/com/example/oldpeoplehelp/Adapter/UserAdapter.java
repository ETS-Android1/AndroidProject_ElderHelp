package com.example.oldpeoplehelp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.oldpeoplehelp.MessageActivity;
import com.example.oldpeoplehelp.R;
import com.example.oldpeoplehelp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class  UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolder> {
    private Context context;

    private List<User> listUsers;

    public UserAdapter(android.content.Context context, List<User> listUsers) {
        this.context = context;
        this.listUsers = listUsers;
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView user_image;
        TextView fullname;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            user_image =itemView.findViewById(R.id.user_image);
            fullname=itemView.findViewById(R.id.fullname);
        }
    }

    @NonNull
    @Override
    public UserAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int id) {
        View view=LayoutInflater.from(context).inflate(R.layout.activity_message,viewGroup);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        //part view single chatUser
        final String hisEmail=listUsers.get(i).getEmail();

        //for display users
        String full_name=listUsers.get(i).getFullname();

        //set data
        myHolder.fullname.setText(full_name);

        try{

        }catch (Exception e){

        }
        //handle item click
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show users
                // registred
                Intent intent=new Intent(context, MessageActivity.class);
                intent.putExtra("hisEmail",hisEmail);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }


}
