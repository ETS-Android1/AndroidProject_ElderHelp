package com.example.oldpeoplehelp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PlanActivitiesActivity extends AppCompatActivity {
    CoordinatorLayout drawerLayout;
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

    String selectedDay, selectedMonth, selectedYear;
    String selectedDate;

    private RecyclerView recyclerViewEvents;
    EventAdapter eventAdapter;
    ArrayList<Event> activitiesList, activitiesListAllDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_activities);
        myDialog = new Dialog(this);
        drawerLayout = findViewById(R.id.drawer_layout);

        //Current User ID
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        final String currentUserId = user.getUid();

        calendarView = findViewById(R.id.calendar);

        recyclerViewEvents = findViewById(R.id.listEvents_recyclerView);
        myRef = FirebaseDatabase.getInstance().getReference().child("Event");
        recyclerViewEvents.setHasFixedSize(true);
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));

        activitiesList = new ArrayList<>();
        activitiesListAllDays = new ArrayList<>();
        eventAdapter = new EventAdapter(this, activitiesList);
        recyclerViewEvents.setAdapter(eventAdapter);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                activitiesList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Event event = dataSnapshot.getValue(Event.class);
                    Log.d("DATE", selectedDate);
                    if( currentUserId.equals(event.getCurrentUserId())){
                        activitiesListAllDays.add(event);
                        if(selectedDate.equals(event.getEventDate())){
                            activitiesList.add(event);
                        }
                    }
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        selectedDate = sdf.format(new Date(calendarView.getDate()));
        //Log.d("DATE", selectedDate);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                selectedDay = String.valueOf(dayOfMonth);
                selectedMonth = String.valueOf(month + 1); //Because month passed is 0-based, a number in the interval from 0 for January through 11 for December.
                selectedYear = String.valueOf(year);
                selectedDate = selectedDay + "/" + selectedMonth + "/" + selectedYear;

                activitiesList.clear();
                eventAdapter.notifyDataSetChanged();
                for(Event event : activitiesListAllDays){
                    if(selectedDate.equals(event.getEventDate())){
                        activitiesList.add(event);
                    }
                }
                eventAdapter.notifyDataSetChanged();
            }

        });

        eventAdapter.setOnItemClickListener(new EventAdapter.OnItemClickListener(){
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
        //calendarView.addDecorator(new CurrentDateDecorator(this));
        /*actionButton = findViewById(R.id.floatingActionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPopup(v);
            }
        });*/
    }

    public void removeItem(int position) {
        activitiesList.remove(position);
        eventAdapter.notifyItemRemoved(position);
        //myRef.child(DatabaseReference.getRef(position).getKey).removeValue();
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

        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        final String currentUserId = user.getUid();
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
                Event event = new Event(currentUserId, selectedDate, name, desc, time);
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

    /*public void ClickMenu(View view){
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
    }*/
}
