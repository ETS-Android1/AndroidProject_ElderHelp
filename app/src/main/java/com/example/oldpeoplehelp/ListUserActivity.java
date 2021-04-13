package com.example.oldpeoplehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListUserActivity extends AppCompatActivity implements View.OnClickListener {

    ListView myListView;
    List<User> userList;
    DatabaseReference userDBRef;
    DrawerLayout drawerLayout;
    ListView view_message;
    TextView fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        drawerLayout = findViewById(R.id.drawer_layout);
        //Intent intent=new Intent(ListUserActivity.this, MessageActivity.class);startActivity(Intent(ListUserActivity.this,MessageActivity.class));


     /*  view_message=(ListView)findViewById(R.id.myListView);
        view_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Object listItem = view_message.getItemAtPosition(position);
                Intent intent=new Intent(ListUserActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });*/
            /*@Override
            public void onItemClick(View v) {
                Intent intent=new Intent(ListUserActivity.this, MessageActivity.class);
                startActivity(intent);
            }*/


        myListView=findViewById(R.id.myListView);
        userList=new ArrayList<>();
        userDBRef= FirebaseDatabase.getInstance().getReference("Users");
        userDBRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for(DataSnapshot userDatasnap:dataSnapshot.getChildren()){
                    User user=userDatasnap.getValue(User.class);
                    userList.add(user);
                }
                UsersAdapter adapter = new UsersAdapter(ListUserActivity.this, userList);
                myListView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }



        });
    }
    @Override
    public void onClick(View v) {


    }

    public void ClickChatMessage(View view) {
        startActivity(new Intent(this,MessageActivity.class));
    }
    public void ClickMenu(View view){
        MenuNavigationActivity.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        MenuNavigationActivity.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        MenuNavigationActivity.redirectActivity(this,MenuNavigationActivity.class);
    }
    public void ClickPlanning(View view){
        // if planning is the test
        recreate();
    }
    public void ClickMedicinsReminder(View view){
        MenuNavigationActivity.redirectActivity(this,TestMenuActivity.class);
    }
    public void ClickZoneMapping(View view){
        MenuNavigationActivity.redirectActivity(this,TestMenuActivity.class);
    }
    public void ClickChat(View view){
        MenuNavigationActivity.redirectActivity(this,ListUserActivity.class);
    }
    public void ClickLogout(View view){
        MenuNavigationActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MenuNavigationActivity.closeDrawer(drawerLayout);
    }


}