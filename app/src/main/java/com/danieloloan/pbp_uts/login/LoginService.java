package com.danieloloan.pbp_uts.login;

import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.danieloloan.pbp_uts.Activity.MainActivity;
import com.danieloloan.pbp_uts.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginService extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private FirebaseAuth mAuth;


    public void login(final LoginView view, String email, String password, final LoginCallback callback) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()){
                                callback.onSuccess();
                            }else{
                                user.sendEmailVerification();
                                callback.onError(false);
                            }
                        }else{
                            callback.onError(true);
                        }
                    }
                });
//        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//        Call<UserResponse> userDAOCall = apiService.loginUser(nim, password);
//        userDAOCall.enqueue(new Callback<UserResponse>() {
//            @Override
//            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                if (response.body().getMessage().equalsIgnoreCase("berhasil login")) {
//                    callback.onSuccess(true, response.body().getUsers().get(0));
//                } else {
//                    callback.onError();
////                    view.showLoginError(this.getString(R.string.login_failed));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserResponse> call, Throwable t) {
//                view.showErrorResponse(t.getMessage());
//                callback.onError();
//            }
//        });
    }

    public Boolean getValid(final LoginView view, String nim, String password){
        final Boolean[] bool = new Boolean[1];
        login(view, nim, password, new LoginCallback() {
            @Override
            public void onSuccess() {
                bool[0] = true;
            }
            @Override
            public void onError(boolean value) {
                bool[0] = false;
            }
        });
        return bool[0];
    }

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
//                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
