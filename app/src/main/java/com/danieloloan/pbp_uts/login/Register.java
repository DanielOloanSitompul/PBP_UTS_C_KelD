package com.danieloloan.pbp_uts.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.danieloloan.pbp_uts.User;
import com.danieloloan.pbp_uts.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private TextInputLayout Fullname,Phone,Address,Birthday,Email,Password;
    private Button Register;
    private String hashPw;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        Register = (Button) findViewById(R.id.registerBtn);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        Fullname = findViewById(R.id.fullnameRegister);
        Phone = findViewById(R.id.phoneRegister);
        Address = findViewById(R.id.addessRegister);
        Birthday = findViewById(R.id.birthdayRegister);
        Email = findViewById(R.id.emailRegister);
        Password = findViewById(R.id.passwordRegister);
    }


    private void registerUser() {

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        String fullname = Fullname.getEditText().getText().toString().trim();
        String phone = Phone.getEditText().getText().toString().trim();
        String address = Address.getEditText().getText().toString().trim();
        String birthday = Birthday.getEditText().getText().toString().trim();
        String email = Email.getEditText().getText().toString().trim();
        String password = Password.getEditText().getText().toString().trim();

        if(fullname.isEmpty()){
            Fullname.setError("Full name is required");
            Fullname.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            Phone.setError("Phone is required");
            Phone.requestFocus();
            return;
        }
        if(address.isEmpty()){
            Address.setError("Address is required");
            Address.requestFocus();
            return;
        }
        if(birthday.isEmpty()){
            Birthday.setError("Birthday is required");
            Birthday.requestFocus();
            return;
        }
        if(email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Please provide valid email");
            Email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Password.setError("Password is required");
            Password.requestFocus();
            return;
        }
        if(password.length() < 6 ){
            Password.setError("Password to short");
            Password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullname,phone, address, birthday,email,password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        FirebaseAuth.getInstance().signOut();
                                        Toast.makeText(Register.this, "User has been registered", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register.this, LoginActivity.class);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(Register.this, "Failed to register! Try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(Register.this, "Register failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}