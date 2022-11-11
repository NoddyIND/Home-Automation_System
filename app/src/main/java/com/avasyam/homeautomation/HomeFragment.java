package com.avasyam.homeautomation;

import android.app.ActivityOptions;
import android.content.Context;
import android.text.format.DateFormat;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

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

public class HomeFragment extends Fragment{
    TextView dateAndTime,hall_text,text_temp_hall,text_humid_hall,text_temp_kitchen,text_temp_bed,text_humid_bed,text_humid_kitchen;
    TextView kitchen_text,bed_text;
    ImageView img_hall,img_bed,img_kitchen;
    ImageView bulb_ic_hall_card,fan_ic_hall_card,bulb_ic_bed_card,bulb_ic_kitchen_card,fan_ic_bed_card,fan_ic_kitchen_card;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {





        //final ScrollView view = (ScrollView) inflater.inflate(R.layout.fragment_home,
               // container, false);

      View view = inflater.inflate(R.layout.fragment_home,container,false);


        bulb_ic_hall_card =view.findViewById(R.id.bulb_ic_hall_card);
        bulb_ic_kitchen_card = view.findViewById(R.id.bulb_ic_kitchen_card);
        bulb_ic_bed_card = view.findViewById(R.id.bulb_ic_bedroom_card);

        fan_ic_hall_card =view.findViewById(R.id.fan_ic_hall_card);
        fan_ic_bed_card = view.findViewById(R.id.fan_ic_bedroom_card);
        fan_ic_kitchen_card = view.findViewById(R.id.fan_ic_kitchen_card);

        text_humid_hall = view.findViewById(R.id.text_humid_hall);
        text_humid_bed = view.findViewById(R.id.text_humid_bed);
        text_humid_kitchen = view.findViewById(R.id.text_humid_kitchen);

        text_temp_hall = view.findViewById(R.id.text_temp_hall);
        text_temp_bed = view.findViewById(R.id.text_temp_bed);
        text_temp_kitchen = view.findViewById(R.id.text_temp_kitchen);

        CardView bedroom_card = view.findViewById(R.id.bedroom_card);
        CardView hall_card = view.findViewById(R.id.hall_card);
        CardView kitchen_card =view.findViewById(R.id.kitchen_card);

        img_kitchen = view.findViewById(R.id.kitchen_card_img);
        img_bed = view.findViewById(R.id.bed_card_img);
        img_hall = view.findViewById(R.id.hall_card_img);


        hall_text = view.findViewById(R.id.hall_text);
        kitchen_text = view.findViewById(R.id.kitchen_text);
        bed_text = view.findViewById(R.id.bed_text);

        ImageView fan_bedroom_card = view.findViewById(R.id.fan_ic_bedroom_card);
        ImageView fan_hall_card = view.findViewById(R.id.fan_ic_hall_card);
        ImageView fan_kitchen_card = view.findViewById(R.id.fan_ic_kitchen_card);

        ImageView bulb_bedroom_card = view.findViewById(R.id.bulb_ic_bedroom_card);
        ImageView bulb_hall_card = view.findViewById(R.id.bulb_ic_hall_card);
        ImageView bulb_kitchen_card = view.findViewById(R.id.bulb_ic_kitchen_card);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Date d = new Date();
        CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());
        dateAndTime = view.findViewById(R.id.date_and_time);

        dateAndTime.setText(s);

        myRef.child("Bed/Fan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);
                    if(status == 1){
                        fan_bedroom_card.setImageResource(R.drawable.fan_on_34);
                    }
                    else{

                        fan_bedroom_card.setImageResource(R.drawable.fan_off_34);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        myRef.child("Hall/Fan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);
                    if(status == 1){
                        //toggleButton.setChecked(true);
                        //t.setText(" on ");
                        fan_hall_card.setImageResource(R.drawable.fan_on_34);
                    }
                    else{

                        fan_hall_card.setImageResource(R.drawable.fan_off_34);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        myRef.child("Kitchen/Fan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);
                    if(status == 1){
                        fan_kitchen_card.setImageResource(R.drawable.fan_on_34);
                    }
                    else{

                        fan_kitchen_card.setImageResource(R.drawable.fan_off_34);
                    }
                }
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

                        bulb_bedroom_card.setImageResource(R.drawable.bulb_on_34);

                    }
                    else{
                        bulb_bedroom_card.setImageResource(R.drawable.bulb_off_ic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        myRef.child("Hall/Light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);
                    if(status == 1){

                        bulb_hall_card.setImageResource(R.drawable.bulb_on_34);

                    }
                    else{

                        bulb_hall_card.setImageResource(R.drawable.bulb_off_ic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        myRef.child("Kitchen/Light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);
                    if(status == 1){
                        //toggleButton.setChecked(true);
                        // t1.setText(" on ");
                        bulb_kitchen_card.setImageResource(R.drawable.bulb_on_34);

                    }
                    else{
                        //toggleButton.setChe0cked(false);
                        // t1.setText(" off ");
                        bulb_kitchen_card.setImageResource(R.drawable.bulb_off_ic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        myRef.child("Bed/Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);

                        text_temp_bed.setText(data+ " \u2103");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        myRef.child("Bed/Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String data = snapshot.getValue().toString();
                int status = Integer.parseInt(data);

                text_humid_bed.setText(data);

                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        myRef.child("Kitchen/Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);

                    text_temp_kitchen.setText(data+ " \u2103");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.child("Kitchen/Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String data = snapshot.getValue().toString();
                int status = Integer.parseInt(data);

                text_humid_kitchen.setText(data);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





        myRef.child("Hall/Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);

                    text_temp_hall.setText(data +" \u2103");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        myRef.child("Hall/Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String data = snapshot.getValue().toString();
                int status = Integer.parseInt(data);

                text_humid_hall.setText(data);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






















        bedroom_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),Bedroom.class);
                Pair[] pairs = new Pair[6];
                pairs[0] = new Pair<View,String>(img_bed,"bed_image");
                pairs[1] = new Pair<View,String>(bed_text,"bed_text_anim");
                pairs[2] = new Pair<View,String>(bulb_ic_bed_card,"bed_bulb_anim");
                pairs[3] = new Pair<View,String>(fan_ic_bed_card,"bed_fan_anim");
                pairs[4] = new Pair<View,String>(text_temp_bed,"text_temp_bed_anim");
                pairs[5] = new Pair<View,String>(text_humid_bed,"text_humid_bed_anim");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),pairs);
                    startActivity(in,options.toBundle());

                }
            }
        });





        hall_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),Hall.class);

                Pair[] pairs = new Pair[6];
                pairs[0] = new Pair<View,String>(img_hall,"hall_image");
                pairs[1] = new Pair<View,String>(hall_text,"hall_text_anim");
                pairs[2] = new Pair<View,String>(bulb_ic_hall_card,"hall_bulb_anim");
                pairs[3] = new Pair<View,String>(fan_ic_hall_card,"hall_fan_anim");
                pairs[4] = new Pair<View,String>(text_temp_hall,"text_temp_hall_anim");
                pairs[5] = new Pair<View,String>(text_humid_hall,"text_humid_hall_anim");


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),pairs);
                    startActivity(in,options.toBundle());

                }



            }
        });


        kitchen_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),Kitchen.class);

                Pair[] pairs = new Pair[6];
                pairs[0] = new Pair<View,String>(img_kitchen,"kitchen_image");
                pairs[1] = new Pair<View,String>(kitchen_text,"kitchen_text_anim");
                pairs[2] = new Pair<View,String>(bulb_ic_kitchen_card,"kitchen_bulb_anim");
                pairs[3] = new Pair<View,String>(fan_ic_kitchen_card,"kitchen_fan_anim");
                pairs[4] = new Pair<View,String>(text_temp_kitchen,"text_temp_kitchen_anim");
                pairs[5] = new Pair<View,String>(text_humid_kitchen,"text_humid_kitchen_anim");


                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),pairs);
                    startActivity(in,options.toBundle());

                }
            }
        });


        return view;
    }


}
