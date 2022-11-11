package com.avasyam.homeautomation;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    de.hdodenhof.circleimageview.CircleImageView profile_image;
    TextView choose_file,uname;
    EditText fullname,email,phone;

    public Uri imageUri;
    public Button profile_save;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private StorageReference storageReference;

    private String currentUserID;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilefragment,container,false);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        currentUserID = mAuth.getCurrentUser().getUid();
        fullname = view.findViewById(R.id.fullName);
        email = view.findViewById(R.id.Email);
        phone = view.findViewById(R.id.phone);
        uname = view.findViewById(R.id.uname);
        profile_image = view.findViewById(R.id.profile_image);
        profile_save = view.findViewById(R.id.profile_save);
        choose_file = view.findViewById(R.id.choose_file);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        RetrieveUserInfo();
        return view;
    }

    private void RetrieveUserInfo() {
        RootRef.child("Users").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image"))))
                        {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();
                            String retrieveProfileImage = dataSnapshot.child("image").getValue().toString();

                            uname.setText(retrieveUserName);
                            Picasso.get().load(retrieveProfileImage).into(profile_image);
                        }
                        else if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name")))
                        {
                            String retrieveUserName = dataSnapshot.child("name").getValue().toString();


                            uname.setText(retrieveUserName);

                        }
                        else
                        {
                            uname.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri =data.getData();


            profile_image.setImageURI(imageUri);


            profile_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateSettings();
                    uploadPicture();

                }
            });
        }

    }

    private void UpdateSettings() {
        String setUserName = fullname.getText().toString();
        String setEmail = email.getText().toString();
        String setPhone = phone.getText().toString();

        if (TextUtils.isEmpty(setUserName))
        {
            Toast.makeText(getActivity(), "Please write your user name first....", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(setEmail))
        {
            Toast.makeText(getActivity(), "Please write your Email....", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(setPhone))
        {
            Toast.makeText(getActivity(), "Please write your Phone no....", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> profileMap = new HashMap<>();
            profileMap.put("uid", currentUserID);
            profileMap.put("name", setUserName);
            profileMap.put("email", setEmail);
            profileMap.put("phone", setPhone);
            profileMap.put("image",123);
            RootRef.child("Users").child(currentUserID).updateChildren(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                SendUserToMainActivity();
                                Toast.makeText(getActivity(), "Profile Updated Successfully...", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String message = task.getException().toString();
                                Toast.makeText(getActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }


    private void uploadPicture() {




        StorageReference riversRef = storageReference.child(currentUserID + ".jpg");

        riversRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        RootRef.child("Users").child(currentUserID).child("image")
                                .setValue(url);

                        //Do what you need to do with url
                    }
                });
            }
        });

    }
}