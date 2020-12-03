package com.danieloloan.pbp_uts.login;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPresenter {
    private LoginView view;
    private LoginService service;
    private LoginCallback callback;
    public LoginPresenter(LoginView view, LoginService service) {
        this.view = view;
        this.service = service;
    }
    public void onLoginClicked() {
        if (view.getEmail().isEmpty()) {
            view.showEmailError("Email tidak boleh kosong");
            return;
        }else if (view.getPassword().isEmpty()) {
            view.showPasswordError("Password tidak boleh kosong");
            return;
        }else{
            service.login(view, view.getEmail(), view.getPassword(), new LoginCallback() {
                @Override
                public void onSuccess() {
                    view.startMainActivity();
                }
                @Override
                public void onError(boolean value) {
                    if (value == false){
                        view.showEmailError("Email anda belum terverifikasi, silahkan lakukan verifikasi!!!");
                    }else {
                        view.showErrorResponse("Email atau password tidak terdaftar!!!");
                    }
                }
            });
            return;
        }
    }
}
