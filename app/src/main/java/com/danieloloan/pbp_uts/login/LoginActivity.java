package com.danieloloan.pbp_uts.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.danieloloan.pbp_uts.Activity.MainActivity;
import com.danieloloan.pbp_uts.R;
import com.danieloloan.pbp_uts.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements LoginView  {

    private TextView register,forgot;
    private TextInputLayout Email,Password,Phone;
    private FirebaseAuth mAuth;
    private Button login;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private LoginPresenter presenter;
    private String cek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
//        register.setOnClickListener(this);

        login = findViewById(R.id.loginBtn);
//        login.setOnClickListener(this);


        forgot = findViewById(R.id.forgot);
//        forgot.setOnClickListener(this);

        Email = findViewById(R.id.emailLogim);
        Password = findViewById(R.id.passwordLogim);

        presenter = new LoginPresenter(this, new LoginService());

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users");
            userID = user.getUid();

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User userProfile = dataSnapshot.getValue(User.class);
                    if(userProfile != null){
                        String Name = userProfile.fullname;
                        Toast.makeText(LoginActivity.this, "Welcome " + Name, Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLoginClicked();
            }
        });
    }

    @Override
    public String getEmail() {
        return Email.getEditText().getText().toString().trim();
    }
    @Override
    public void showEmailError(String message) {
        Email.setError(message);
    }
    @Override
    public String getPassword() {
        return Password.getEditText().getText().toString().trim();
    }
    @Override
    public void showPasswordError(String message) {
        Password.setError(message);
    }
    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }
    //    @Override
//    public void startUserProfileActivity(UserDAO user) {
//        new ActivityUtil(this).startUserProfile(user);
//    }
    @Override
    public void showLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.register :
//                startActivity(new Intent(this, Register.class));
//                break;
//
//            case R.id.loginBtn:
//                presenter.onLoginClicked();
//                break;
//
//            case R.id.forgot:
//                startActivity(new Intent(this, ForgotPassword.class));
//                break;
//        }
//    }

//    private void userLogin() {
//        String email = Email.getEditText().getText().toString().trim();
//        String password = Password.getEditText().getText().toString().trim();
//
//        if(email.isEmpty()){
//            Email.setError("Email is required");
//            Email.requestFocus();
//            return;
//        }
//        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            Email.setError("Please provide valid email");
//            Email.requestFocus();
//            return;
//        }
//
//        if(password.isEmpty()){
//            Password.setError("Password is required");
//            Password.requestFocus();
//            return;
//        }
//        if(password.length() < 6 ){
//            Password.setError("Password to short");
//            Password.requestFocus();
//            return;
//        }
//
//        mAuth.signInWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            if (user.isEmailVerified()){
//                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                                startActivity(intent);
//                                Toast.makeText(LoginActivity.this,"Login Succsessful",Toast.LENGTH_LONG).show();
//                            }else{
//                                user.sendEmailVerification();
//                                Toast.makeText(LoginActivity.this,"Check your email to verify your account",Toast.LENGTH_LONG).show();
//                            }
//
//                        }else{
//                            Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }


}