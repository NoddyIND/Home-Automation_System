package com.avasyam.homeautomation;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.media.audiofx.Visualizer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.avasyam.homeautomation.App.CHANNEL_1_ID;
import static com.avasyam.homeautomation.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private DrawerLayout drawer;
    private NotificationManagerCompat notoficationManager;

    private EditText editTextTitle;
    private EditText editTextMessage;
    private int OnDataCaptureListener;
    private String currentUserID;
    private DatabaseReference RootRef;
    private FirebaseAuth mAuth;
    de.hdodenhof.circleimageview.CircleImageView profile_image;
    TextView nav_email;
    TextView nav_user;




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notoficationManager = NotificationManagerCompat.from(this);
//        EditText smoke = findViewById(R.id.smoke);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SMOKE_STATUS");

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        currentUserID = mAuth.getCurrentUser().getUid();




        myRef.addValueEventListener(new ValueEventListener() {


            //              @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);

                    if(status == 1){



                        addNotification();



                    }

                    else{
                        //  toggleButton.setChecked(false);

                    }
                }
            }


            private void addNotification() {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.fire_alarm)
                        .setContentTitle("Fire alarm")
                        .setContentText("Alert!")
                        .setVibrate(new long[] {0,500,1000})
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());

           }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        DatabaseReference myRef_motion = database.getReference("MOTION_STATUS");

        myRef_motion.addValueEventListener(new ValueEventListener() {


            //              @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String data = snapshot.getValue().toString();
                    int status = Integer.parseInt(data);

                    if(status == 1){



                        addNotification_motion();

                        DatabaseReference myRef1 = database.getReference("LED_STATUS");//LED_STATUS is Firebase database LED_STATUS
                        myRef1.setValue(1);

                    }

                    else{
                        //  toggleButton.setChecked(false);

                    }
                }
            }


            private void addNotification_motion() {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,CHANNEL_2_ID)
                        .setSmallIcon(R.drawable.fire_alarm)
                        .setContentTitle(" Motion detected !")
                        .setContentText("Turning on the lights ")
                        .setVibrate(new long[] {0,500,1000})
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, builder.build());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });













//        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
//        String strdate = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
//        TextView date = findViewById(R.id.date1);
//        date.setText(strdate);




        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener   (navListner);
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                new HomeFragment()).commit();


        NavigationView navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(this);

        RootRef.child("Users").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image")))) {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();
                            String retrieveUserEmail = dataSnapshot.child("email").getValue().toString();


                            View hView =  navigationView.getHeaderView(0);

                            TextView nav_user = (TextView)hView.findViewById(R.id.name);
                            nav_user.setText(retrieveUserName);
                            TextView nav_email = (TextView)hView.findViewById(R.id.email);
                            nav_email.setText(retrieveUserEmail);
                            profile_image =hView.findViewById(R.id.profile_image);
                            Picasso.get().load(retrieveProfileImage).into(profile_image);
                        } else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")) && (dataSnapshot.hasChild("email"))) {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveUserEmail = dataSnapshot.child("name").getValue().toString();
                            nav_user.setText(retrieveUserName);
                            nav_email.setText(retrieveUserEmail);
                        } else {


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);



        if(item.getItemId()== R.id.Home){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.myContainer, new HomeFragment());
            fragmentTransaction.commit();

        }
        if(item.getItemId()==R.id.nav_web){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Intent viewIntent =
                    new Intent("android.intent.action.VIEW",
                            Uri.parse("http://192.168.31.65/"));
            
            startActivity(viewIntent);
            fragmentTransaction.commit();

        }

        if(item.getItemId()== R.id.Profile){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
           fragmentTransaction.replace(R.id.myContainer, new ProfileFragment());
//            Intent intent=new Intent(this, ProfileFragment.class);
//            startActivity(intent);
            fragmentTransaction.commit();
            







        }

        if(item.getItemId()==R.id.nav_share){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction =fragmentManager.beginTransaction();

//            ApplicationInfo api =getApplicationContext().getApplicationInfo();
//            String apkpath = api.sourceDir;
//            Intent intent= new Intent(Intent.ACTION_SEND);
//            intent.setType("application/vnd.android.package-archive");
//            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(apkpath)));
//            startActivity(Intent.createChooser(intent,"ShareVia"));
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://mega.nz/file/2YcyjThS#K-zoLhe92Sf_GErHLyiQKX3le8noKI6a5IPP5aRCWeA");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            fragmentTransaction.commit();

        }

        if(item.getItemId()==R.id.nav_logout){

            FirebaseAuth.getInstance().signOut(); //End user session
            Toast.makeText(this, "User Logged out successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, Register.class)); //Go back to home page
            finish();



        }
        return true;
    }




    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //return false;
//                Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
//                            selectedFragment = new HomeFragment();

                            fragmentManager = getSupportFragmentManager();
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.setCustomAnimations(R.anim.slide_in_left,
                                     R.anim.slide_out_right);
                            fragmentTransaction.replace(R.id.myContainer, new HomeFragment());

                            fragmentTransaction.commit();


                            break;
                        case R.id.nav_mode:
//                            selectedFragment = new ModeFragment();
                            fragmentManager = getSupportFragmentManager();

                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                                    R.anim.slide_out_left);
                            fragmentTransaction.replace(R.id.myContainer, new ModeFragment());
                            fragmentTransaction.commit();

                            break;

                    }
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            selectedFragment);
                    return true;
                }
            };
}