package com.example.oldpeoplehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oldpeoplehelp.Adapter.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MessageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView fullname;
    TextView status;
    EditText messageEt;
    ImageButton sendBtn;
    ImageView user_image;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference userDbRef;

    String hisEmail;
    String myUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //init views
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        recyclerView=findViewById(R.id.chat_recycler_view);
        user_image=findViewById(R.id.user_image);
        messageEt=findViewById(R.id.messageEt);
        sendBtn=findViewById(R.id.sendBtn);

        Intent intent=getIntent();
        hisEmail=intent.getStringExtra("email");
        firebaseAuth =FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        userDbRef=firebaseDatabase.getReference("Users");

        Query userQuery=userDbRef.orderByChild("email").equalTo(hisEmail);
        //get user picture and name

        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    String name=""+ds.child("name").getValue();
                    //String name=""+ds.child("name").getValue();
                    fullname.setText(name);
                }
               // UserAdapter adapter = new UsersAdapter(MessageActivity.this, userList);
                //recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void checkUserSatatus(){
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){
            myUid=user.getUid();
        }else{
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }
}