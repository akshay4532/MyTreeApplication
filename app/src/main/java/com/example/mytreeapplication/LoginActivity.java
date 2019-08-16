package com.example.mytreeapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mytreeapplication.model.Data;
import com.example.mytreeapplication.preference.Prefs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
private long doubleTap;
    private Toast doubleTapToast;
    @BindView(R.id.mail)
    TextInputEditText emailEdit;
    @BindView(R.id.pass)
    TextInputEditText passwordEdit;
   @BindView(R.id.progress_log)
    ProgressBar pro;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    String email,password;
    StringBuffer sb=new StringBuffer();
    DatabaseReference mref= FirebaseDatabase.getInstance().getReference();
    Data data;
    Dialog noconnectiondialog;

    public void onStart() {
        super.onStart();
        firebaseUser=mAuth.getCurrentUser();
        if(!internetCheck()){
            nointernetdialog();
        }else if(firebaseUser!=null){
            updateUi(firebaseUser);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing Please wait...");
        ButterKnife.bind(this);
        Prefs.init(this);
        mAuth=FirebaseAuth.getInstance();
//        sharedPreferences=getSharedPreferences("prefs", Context.MODE_PRIVATE);
//        editor=sharedPreferences.edit();
    }

    public void updateUi(FirebaseUser user){
        Intent intent=new Intent(LoginActivity.this,MapNavDrawer.class);
        intent.putExtra("user",user.getEmail());
        startActivity(intent);
    }
    public boolean internetCheck(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null&& networkInfo.isConnected();
    }
    private void nointernetdialog() {
        noconnectiondialog = new Dialog(LoginActivity.this, android.R.style.Theme_DeviceDefault_Dialog);
        noconnectiondialog.setContentView(R.layout.netconnectiondialogdesign);
        Button oknoconnection = noconnectiondialog.findViewById(R.id.oknetconnection);
        oknoconnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        noconnectiondialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        noconnectiondialog.setCancelable(false);
        noconnectiondialog.show();
    }
    @OnClick(R.id.btn_log)
    public void login(final View view){
       // pro.setVisibility(view.VISIBLE);


        email=emailEdit.getText().toString().trim();
        password=passwordEdit.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
        emailEdit.setError("Please Enter Email id");
        return;
        }if(TextUtils.isEmpty(password)){
            passwordEdit.setError("Please Enter password ");
            return;
        }

       pro.setVisibility(view.VISIBLE);
        sb.append(email);
        sb.append(password);
        Log.d("Data Buff",sb.toString());
        progressDialog.show();
       mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                 pro.setVisibility(view.GONE);
                    Log.d("In", "signInEmail:success");

                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUi(user);
                   // fetchDetails();
                  //  startActivity(new Intent(LoginActivity.this, MapNavDrawer.class));
                    Toast.makeText(LoginActivity.this, "Login sucess", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else{
                    Log.d("out","signInEmail:fails");
                    Toast.makeText(LoginActivity.this, "Login fails", Toast.LENGTH_SHORT).show();
                    pro.setVisibility(view.GONE);
                    progressDialog.dismiss();
                }
            }
        });
           }
    @OnClick(R.id.btn_sign)
    public  void sign(View view){
//        Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
//        Bundle bndlAnimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.transition.left_anim, R.transition.right_anim).toBundle();
//
//        startActivity(intent, bndlAnimation);

        startActivity(new Intent(LoginActivity.this, SignupActivity.class));

    }
//    private  void fetchDetails(){
//        final FirebaseUser firebaseUser=mAuth.getCurrentUser();
//        mref.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                data = dataSnapshot.getValue(Data.class);
//                if (data != null) {
//                    Prefs.setUserName(data.getUname());
//                    Prefs.setEmail(data.getUmail());
//                    Prefs.setAuth(true);
//                    Intent intent=new Intent(LoginActivity.this,MapNavDrawer.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    Toast.makeText(LoginActivity.this, "Welcome"+Prefs.getUserName(), Toast.LENGTH_SHORT).show();
//                    startActivity(intent);
//                    finish();
//                    Prefs.commit();
//                }else{
//                    Toast.makeText(LoginActivity.this, "something went wrong : ", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(LoginActivity.this, "nhi hua..", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }
    @Override
    public void onBackPressed() {
        if(doubleTap + 3000>System.currentTimeMillis()){
            doubleTapToast.cancel();
            super.onBackPressed();
            return ;
        }else{
            doubleTapToast = Toast.makeText(this,"Press Back Again for Exit",Toast.LENGTH_LONG);
            doubleTapToast.show();
        }
        doubleTap = System.currentTimeMillis();
    }
    @OnClick(R.id.frgtPss)
    public void forgot(){
        startActivity(new Intent(LoginActivity.this,ForgotPsswordActivity.class));
    }
}
