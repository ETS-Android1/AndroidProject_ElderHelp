package com.example.oldpeoplehelp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PlanActivitiesActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    CalendarView calendarView;
    FloatingActionButton actionButton;
    Dialog myDialog;
    int t1Hour, t1Minute;
    EditText eventName, eventDescription;
    TextView eventTime;
    Button saveButton;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_activities);
        myDialog = new Dialog(this);
        drawerLayout = findViewById(R.id.drawer_layout);
        calendarView = findViewById(R.id.calendar);
        //calendarView.addDecorator(new CurrentDateDecorator(this));
        /*actionButton = findViewById(R.id.floatingActionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);
            }
        });*/
    }

    public void ShowPopup(View v) {
        TextView txtclose;
        Button btnFollow;
        myDialog.setContentView(R.layout.activity_add_event_popup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        //txtclose.setText("M");
        //btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        insertData();
    }

    public void SelectTime(View v){
        final TextView timer = (TextView) myDialog.findViewById(R.id.timer);
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                PlanActivitiesActivity.this,
                new TimePickerDialog.OnTimeSetListener(){

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //Initialize hour and minute
                        t1Hour = hourOfDay;
                        t1Minute = minute;
                        //Initialize calendar
                        Calendar calendar = Calendar.getInstance();
                        //Set hour and minute
                        calendar.set(0, 0, 0, t1Hour, t1Minute);
                        //Set selected time on TextView

                        timer.setText(DateFormat.format( "hh:mm aa", calendar));
                    }
                }, 12, 0, false

        );
        //Display previous selected time
        timePickerDialog.updateTime(t1Hour, t1Minute);
        //Show Dialog
        timePickerDialog.show();
    }

    private void insertData(){
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        final String currentUserId = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("Event");
        eventName = myDialog.findViewById(R.id.event_name);
        eventDescription = myDialog.findViewById(R.id.event_description);
        eventTime = myDialog.findViewById(R.id.timer);
        saveButton = myDialog.findViewById(R.id.saveEvent);
        saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String name = eventName.getText().toString();
                String desc = eventDescription.getText().toString();
                String time = eventTime.getText().toString();
                Event event = new Event(currentUserId, name, desc, time);
                long mDateTime = 9999999999999L - System.currentTimeMillis();
                String mOrderTime = String.valueOf(mDateTime);
                myRef.child(mOrderTime).setValue(event).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        myDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Event added successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
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


    @Override
    protected void onPause() {
        super.onPause();
        MenuNavigationActivity.closeDrawer(drawerLayout);
    }
}
