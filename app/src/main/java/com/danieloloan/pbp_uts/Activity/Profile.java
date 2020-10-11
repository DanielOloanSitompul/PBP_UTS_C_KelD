package com.danieloloan.pbp_uts.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.danieloloan.pbp_uts.User;
import com.danieloloan.pbp_uts.R;
import com.danieloloan.pbp_uts.login.Login;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private ImageView setting,camera;
    private TextView logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.navigation_profile);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        logout = findViewById(R.id.exitTV);
        setting = findViewById(R.id.settingIV);
        camera = findViewById(R.id.cameraIV);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView name = (TextView) findViewById(R.id.nameUserTV);
        final TextView email = (TextView) findViewById(R.id.emailTV);
        final TextView phone = (TextView) findViewById(R.id.phoneTV);
        final TextView address = (TextView) findViewById(R.id.placeTV);
        final TextView birthday = (TextView) findViewById(R.id.birthdayTV);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);

                if(userProfile != null){
                    String Name = userProfile.fullname;
                    String Email = userProfile.email;
                    String Phone = userProfile.phone;
                    String Address = userProfile.address;
                    String Birthday = userProfile.birthday;

                    name.setText(Name);
                    email.setText(Email);
                    phone.setText(Phone);
                    address.setText(Address);
                    birthday.setText(Birthday);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                    requestStoragePermission();
                }
                else{
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,100);
                }
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext()
                        , MainActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Profile.this, "LogOut Succsessful.",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Profile.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void requestStoragePermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("test")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Profile.this ,new String[] {Manifest.permission.CAMERA}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.navigation_home:
                            startActivity(new Intent(getApplicationContext()
                                    ,MainActivity.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.navigation_search:
                            startActivity(new Intent(getApplicationContext()
                                    , DaftarMobil.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.navigation_booking:
                            startActivity(new Intent(getApplicationContext()
                                    , Booking.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.navigation_profile:
                            startActivity(new Intent(getApplicationContext()
                                    , Profile.class));
                            overridePendingTransition(0,0);
                            return true;
                    }
                    return true;
                }
            };
}