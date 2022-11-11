package com.avasyam.homeautomation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Kitchen extends AppCompatActivity {
    ToggleButton toggleButton_light;
    ToggleButton toggleButton_fan;

    TextView texttemp,texthumid;

    ProgressBar progressBarTemp,progressBarHumid;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);


        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        toggleButton_light = findViewById(R.id.toggleButton_light_kitchen);


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        toggleButton_fan = findViewById(R.id.toggleButton_fan_kitchen);

        progressBarTemp = findViewById(R.id.progressBartemp_kitchen);
        progressBarHumid = findViewById(R.id.progressBarhumid_kitchen);
        texttemp = findViewById(R.id.temp_kitchen);
        texthumid = findViewById(R.id.humid_kitchen);

        DatabaseReference myRef = database.getReference();



        myRef.child("Kitchen/Light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);
                    if(status == 1){
                        toggleButton_light.setChecked(true);
                        toggleButton_light.setBackgroundColor(Color.parseColor("#24a0ed"));
                        toggleButton_light.setTextColor(Color.WHITE);
                        toggleButton_light.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.bulb_on_64,0);

                    }
                    else{
                        toggleButton_light.setChecked(false);
                        toggleButton_light.setBackgroundColor(Color.WHITE);
                        toggleButton_light.setTextColor(Color.BLACK);
                        toggleButton_light.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.bulb_off_64,0);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        toggleButton_light.setOnClickListener(view -> {
            if(toggleButton_light.isChecked()){

                myRef.child("Kitchen/Light").setValue(1);

            }
            else{

                myRef.child("Kitchen/Light").setValue(0);
            }
        });







        //-------------------------------------------------------------------------------------------------------------------------------------------------------
        //=====================================================================================================================================================
        //-------------------------------------------------------------------------------------------------------------------------------------------------------



        myRef.child("Kitchen/Fan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);
                    if(status == 1){
                        toggleButton_fan.setChecked(true);
                        toggleButton_fan.setBackgroundColor(Color.parseColor("#24a0ed"));
                        toggleButton_fan.setTextColor(Color.WHITE);
                        toggleButton_fan.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.fan_on_64,0);
                    }
                    else{
                        toggleButton_fan.setChecked(false);
                        toggleButton_fan.setBackgroundColor(Color.WHITE);
                        toggleButton_fan.setTextColor(Color.BLACK);
                        toggleButton_fan.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.fan_off_64,0);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        toggleButton_fan.setOnClickListener(view -> {
            if(toggleButton_fan.isChecked()){

                // Write a message to the database


                myRef.child("Kitchen/Fan").setValue(1);

            }
            else{

                myRef.child("Kitchen/Fan").setValue(0);
            }
        });


        myRef.child("Kitchen/Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);
                    texttemp.setText(status+ " \u2103");
                    progressBarTemp.setProgress(status);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        myRef.child("Kitchen/Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);
                    texthumid.setText(data);
                    progressBarHumid.setProgress(status);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });













    }
}
