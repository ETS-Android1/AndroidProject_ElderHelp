package com.example.oldpeoplehelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CallsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private static final int REQUEST_CALL =1;
    private TextView callTextPolice,callTextAmbulance,callTextFire,callTextGendarmerie,callTextCovid;
    private Button callBtnPolice,callBtnAmbulance,callBtnFire,callBtnGendarmerie,callBtnCovid;
    private String numberCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calls);

        drawerLayout = findViewById(R.id.drawer_layout_calls);
        // Emergency Calls
        // Police
        callTextPolice = findViewById(R.id.police_numb);
        callBtnPolice = findViewById(R.id.police_btn);
        callBtnPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberCall ="19";
                CallButton(numberCall);
            }
        });
        // Ambulance
        callTextAmbulance = findViewById(R.id.ambulance_numb);
        callBtnAmbulance = findViewById(R.id.ambulance_btn);
        callBtnAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberCall = "150";
                CallButton(numberCall);
            }
        });
        //Firefighters
        callTextFire = findViewById(R.id.fire_numb);
        callBtnFire = findViewById(R.id.fire_btn);
        callBtnFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberCall = "15";
                CallButton(numberCall);
            }
        });
        // Gendarmerie
        callTextGendarmerie = findViewById(R.id.gendarmerie_numb);
        callBtnGendarmerie = findViewById(R.id.gendarmerie_btn);
        callBtnGendarmerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberCall = "177";
                CallButton(numberCall);
            }
        });
        // Covid
        callTextCovid = findViewById(R.id.covid_numb);
        callBtnCovid = findViewById(R.id.covid_btn);
        callBtnCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberCall = "0801 00 47 47";
                CallButton(numberCall);
            }
        });

    }

    private void CallButton(String number) {
        if(ContextCompat.checkSelfPermission(CallsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CallsActivity.this,new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else{
            String dial = "tel:"+number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
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
        MenuNavigationActivity.redirectActivity(this,TestMenuActivity.class);
    }
    public void ClickMedicinsReminder(View view){
        MenuNavigationActivity.redirectActivity(this,TestMenuActivity.class);
    }
    public void ClickZoneMapping(View view){
        MenuNavigationActivity.redirectActivity(this,MapsActivity.class);
    }
    public void ClickChat(View view){
        MenuNavigationActivity.redirectActivity(this,TestMenuActivity.class);
    }
    public void ClickEmergencyCalls(View view){
        // Redirect activity to Chat : just Test
        recreate();
    }
    public void ClickLogout(View view){
        MenuNavigationActivity.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MenuNavigationActivity.closeDrawer(drawerLayout);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                CallButton(numberCall);
            }else{
                Toast.makeText(this,"permission DENIED",Toast.LENGTH_SHORT).show();
            }
        }
    }
}