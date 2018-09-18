package com.jdsfoods;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.jdsfoods.Firebase.MyFirebaseInstanceIDService;
import com.jdsfoods.Utilities.Preferences;

public class SplashActivity extends AppCompatActivity {
    int SPLASH_DISPLAY_LENGTH = 3000;
    String deviceToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar();
        Intent intent = new Intent(this, MyFirebaseInstanceIDService.class);
        startService(intent);
        if (deviceToken == null) {
            deviceToken = FirebaseInstanceId.getInstance().getToken();
        }
        Log.d("Android","Android ID : "+deviceToken);
      // Toast.makeText(getApplicationContext(),"deviceToken:"+deviceToken, Toast.LENGTH_SHORT).show();
        Preferences.getInstance().setDevice_token(deviceToken);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (com.jdsfoods.Utilities.Preferences.getInstance().isLogIn()) {
                    startMain();
                } else {
                    startLogin();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void startLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        this.finish();
    }

    private void startMain() {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        this.finish();
    }


    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}