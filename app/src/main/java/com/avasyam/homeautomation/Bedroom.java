package com.avasyam.homeautomation;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bedroom extends AppCompatActivity {

    ToggleButton toggleButton_light;
    ToggleButton toggleButton_fan;
    TextView texttemp,texthumid;
    ProgressBar progressBarTemp,progressBarHumid;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bedroom);


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        toggleButton_light = findViewById(R.id.toggleButton_light_bed);
        toggleButton_fan = findViewById(R.id.toggleButton_fan_bed);

        progressBarTemp = findViewById(R.id.progressBarTemp_bed);
        progressBarHumid = findViewById(R.id.progressBarHumid_bed);
        texttemp = findViewById(R.id.temp_bed);
        texthumid = findViewById(R.id.humid_bed);
        DatabaseReference myRef = database.getReference();

        myRef.child("Bed/Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = snapshot.getValue().toString();
                int i = Integer.parseInt(data);
                texttemp.setText(data);
                progressBarTemp.setProgress(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myRef.child("Bed/Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = snapshot.getValue().toString();
                int i = Integer.parseInt(data);
                texthumid.setText(data);
                progressBarHumid.setProgress(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        myRef.child("Bed/Light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);
                    if(status == 1){
//
                        toggleButton_light.setChecked(true);
                        toggleButton_light.setBackgroundColor(Color.parseColor("#24a0ed"));
                        toggleButton_light.setTextColor(Color.WHITE);
                        toggleButton_light.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.bulb_on_64,0);
                    }
                    else{
//                        toggleButton.setChecked(false);
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

                myRef.child("Bed/Light").setValue(1);

            }
            else{
                myRef.child("Bed/Light").setValue(0);
            }
        });


        //==============================================================================================================================








        myRef.child("Bed/Fan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);
                    if(status == 1){
//                        toggleButton_fan.setChecked(true);
                        toggleButton_fan.setChecked(true);
                        toggleButton_fan.setBackgroundColor(Color.parseColor("#24a0ed"));
                        toggleButton_fan.setTextColor(Color.WHITE);
                        toggleButton_fan.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.fan_on_64,0);
                    }
                    else{
//                        toggleButton_fan.setChecked(false);
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

                myRef.child("Bed/Fan").setValue(1);

            }
            else{

                myRef.child("Bed/Fan").setValue(0);

            }
        });
        //==============================================================================================================================
    }
}


