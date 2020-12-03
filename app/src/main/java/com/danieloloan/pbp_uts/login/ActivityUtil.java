package com.danieloloan.pbp_uts.login;

import android.content.Context;
import android.content.Intent;

import com.danieloloan.pbp_uts.Activity.MainActivity;

public class ActivityUtil {
    private Context context;
    public ActivityUtil(Context context) {
        this.context = context;
    }
    public void startMainActivity() {
        context.startActivity(new Intent(context, MainActivity.class));
    }
//    public void restartLoginActivity(String cek) {
//        Intent i = new Intent(context, LoginActivity.class);
//        i.putExtra("cek",cek);
//        context.startActivity(i);
//    }
}
