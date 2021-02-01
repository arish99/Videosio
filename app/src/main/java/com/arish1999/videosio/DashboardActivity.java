package com.arish1999.videosio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class DashboardActivity extends AppCompatActivity {
    EditText code;
    Button joinBtn;
    Button shareBtn;
    BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bnv=findViewById(R.id.bottomNavigationView);

        code=findViewById(R.id.codeBox);
        joinBtn=findViewById(R.id.joinBtn);
        shareBtn=findViewById(R.id.shareBtn);

        URL serverurl;

        try {
            serverurl= new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultoptions =
                    new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverurl)
                    .setWelcomePageEnabled(false)
                    .build();
            JitsiMeet.setDefaultConferenceOptions(defaultoptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.logout:
                        startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                }
                return false;
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(code.getText().toString())
                        .setWelcomePageEnabled(false)
                        .build();

                JitsiMeetActivity.launch(DashboardActivity.this,options);
            }
        });
    }

}