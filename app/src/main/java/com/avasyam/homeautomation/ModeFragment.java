package com.avasyam.homeautomation;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import static java.lang.System.exit;


public class ModeFragment extends Fragment {
    //private CardView bedroom_card,hall_card;
//TextView led_status;
    int a;
    TextView dateAndTime;
    ImageView ML,MF,EL,EF,NS,NMS,NDS;
    private WebView webView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mode, container, false);

        Date d = new Date();
        CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());
        dateAndTime = view.findViewById(R.id.date_and_time);

        dateAndTime.setText(s);

        CardView morning_card = view.findViewById(R.id.morning_card);
        CardView evening_card = view.findViewById(R.id.evening_card);
        CardView night_card = view.findViewById(R.id.night_card);
        ML = view.findViewById(R.id.bulb_morning_mode);
        MF = view.findViewById(R.id.fan_morning_mode);
        EL = view.findViewById(R.id.bulb_evening_mode);
        EF = view.findViewById(R.id.fan_evening_mode);
        NS = view.findViewById(R.id.sensor_night_mode);
        NMS =  view.findViewById(R.id.motion_sensor_night_mode);
        NDS =  view.findViewById(R.id.door_sensor_night_mode);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        TextView t2 =view.findViewById(R.id.evening_mode_text);
        TextView t1 =view.findViewById(R.id.morning_text);
        TextView t3 =view.findViewById(R.id.night_mode_text);

        morning_card.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Morning Mode activated", Toast.LENGTH_SHORT).show();

            myRef.child("Modes/EveM").setValue(0);
            myRef.child("Modes/MorM").setValue(1);
            myRef.child("Modes/NigM").setValue(0);
        });

        evening_card.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Evening Mode activated", Toast.LENGTH_SHORT).show();

            myRef.child("Modes/EveM").setValue(1);
            myRef.child("Modes/MorM").setValue(0);
            myRef.child("Modes/NigM").setValue(0);
        });
        night_card.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Night Mode activated", Toast.LENGTH_SHORT).show();

            myRef.child("Modes/EveM").setValue(0);
            myRef.child("Modes/MorM").setValue(0);
            myRef.child("Modes/NigM").setValue(1);
        });





        myRef.child("Modes/EveM").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String data = snapshot.getValue().toString();
                int status = Integer.parseInt(data);
                if(status == 1){
                    t1.setText("");
                    t2.setText("Active");
                    t3.setText("");
                    myRef.child("Bed/Light").setValue(1);
                    myRef.child("Bed/Fan").setValue(1);
                    myRef.child("Hall/Light").setValue(1);
                    myRef.child("Hall/Fan").setValue(1);

                    EF.setImageResource(R.drawable.fan_on_ic);
                    EL.setImageResource(R.drawable.bulb_on_ic);
                    ML.setImageResource(R.drawable.none);
                    MF.setImageResource(R.drawable.none);
                    NS.setImageResource(R.drawable.none);
                    NMS.setImageResource(R.drawable.none);
                    NDS.setImageResource(R.drawable.none);





                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("Modes/MorM").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String data = snapshot.getValue().toString();
                int status = Integer.parseInt(data);
                if(status == 1){
                    t1.setText("Active");
                    t2.setText("");
                    t3.setText("");

                    myRef.child("Bed/Light").setValue(0);
                    myRef.child("Bed/Fan").setValue(0);
                    myRef.child("Hall/Light").setValue(0);
                    myRef.child("Hall/Fan").setValue(0);
                    MF.setImageResource(R.drawable.fan_off_ic);
                    ML.setImageResource(R.drawable.bulb_off_ic);
                    EF.setImageResource(R.drawable.none);
                    EL.setImageResource(R.drawable.none);
                    NS.setImageResource(R.drawable.none);
                    NMS.setImageResource(R.drawable.none);
                    NDS.setImageResource(R.drawable.none);






                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//


        myRef.child("Modes/NigM").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String data = snapshot.getValue().toString();
                int status = Integer.parseInt(data);
                if(status == 1){

                    t1.setText("");
                    t2.setText("");
                    t3.setText("Active");

                    myRef.child("Bed/Light").setValue(0);
                    myRef.child("Bed/Fan").setValue(1);
                    myRef.child("Hall/Light").setValue(0);
                    myRef.child("Hall/Fan").setValue(1);
                    NS.setImageResource(R.drawable.sensor);
                    NMS.setImageResource(R.drawable.motion_sensor);
                    NDS.setImageResource(R.drawable.door_sensor_white);
                    MF.setImageResource(R.drawable.none);
                    ML.setImageResource(R.drawable.none);
                    EF.setImageResource(R.drawable.none);
                    EL.setImageResource(R.drawable.none);




                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        return view;
    }
}













