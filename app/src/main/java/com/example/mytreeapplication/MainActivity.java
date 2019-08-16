package com.example.mytreeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.mytreeapplication.preference.Prefs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
  //  Dialog noconnectiondialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
       // noconnectiondialog = new Dialog(this);
      //  Prefs.init(this);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);


//        sharedPreferences=getSharedPreferences("prefs", Context.MODE_PRIVATE);
//        editor=sharedPreferences.edit();

//        if (shownoconnectiondialog() == true) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (Prefs.getAuth()) {
//                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                        finish();
//                    } else {
//                        // ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this, img, "imageTransition");
//                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                finish();
//                                overridePendingTransition(0, 0);
//                            }
//                        }, 1000);
//                    }
//
//                }
//            }, 3000);
//        }else{
//        nointernetdialog();
//        }
//    }
//    private boolean shownoconnectiondialog() {
//        ConnectivityManager cnm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (cnm != null) {
//            NetworkInfo info = cnm.getActiveNetworkInfo();
//            if (info != null) {
//                if (info.getState() == NetworkInfo.State.CONNECTED) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

//    private void nointernetdialog() {
//        noconnectiondialog = new Dialog(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
//        noconnectiondialog.setContentView(R.layout.netconnectiondialogdesign);
//        Button oknoconnection = noconnectiondialog.findViewById(R.id.oknetconnection);
//        oknoconnection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        noconnectiondialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        noconnectiondialog.setCancelable(false);
//        noconnectiondialog.show();
//    }

    }
}
