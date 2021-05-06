package com.example.oldpeoplehelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class TestMenuActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
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
        MenuNavigationActivity.redirectActivity(this,MapsActivity.class);
    }
    public void ClickChat(View view){
        MenuNavigationActivity.redirectActivity(this,TestMenuActivity.class);
    }
    public void ClickEmergencyCalls(View view){
        // Redirect activity to Chat : just Test
        MenuNavigationActivity.redirectActivity(this,CallsActivity.class);
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
