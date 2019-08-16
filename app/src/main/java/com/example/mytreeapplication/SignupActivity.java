package com.example.mytreeapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mytreeapplication.model.Data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {
    @BindView(R.id.name)
    TextInputEditText naam;

    @BindView(R.id.mob)
    TextInputEditText phn;
    @BindView(R.id.mail)
    TextInputEditText email;
    @BindView(R.id.pass)
    TextInputEditText password;
   @BindView(R.id.progress_sign)
    ProgressBar progressbar;
   ProgressDialog progressDialog;
    String name,mob,mail,pas;


    private FirebaseAuth auth;
   // FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference();


    StringBuffer stringBuffer=new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setTitle("Sign up");
        setContentView(R.layout.activity_signup);
       // getActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
       // getSupportActionBar().hide();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Data Submitted Please wait...");
        auth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);
    }
    @Override
    public boolean onSupportNavigateUp() {
       // overridePendingTransition(R.transition.left_anim, R.transition.right_anim);
        onBackPressed();

        return true;
    }
    @OnClick(R.id.btn_sub)
    public void submit(){

        name=naam.getText().toString();
        mob=phn.getText().toString();
        mail=email.getText().toString();
        pas=password.getText().toString();

        if(TextUtils.isEmpty(name)){
            naam.setError("please enter  your name");
            return;
        }
        if(TextUtils.isEmpty(mob)){
            phn.setError("please enter your mobile no");
            return;
        } if(TextUtils.isEmpty(mail)){
            email.setError("please enter your email_id");
            return;
        } if(TextUtils.isEmpty(pas)){
            password.setError("please enter password");
            return;
        }
        if(pas.length()<5){
            password.setError("password must be 8 characters");
        }
        stringBuffer.append(naam);
        stringBuffer.append(mob);
        stringBuffer.append(mail);
        stringBuffer.append(pas);

        Log.d("DataEnter",stringBuffer.toString());

        progressDialog.show();
        firebaseReg(name,mob,mail,pas);

    }
    public void firebaseReg(final String name,final String mob,final String mai,final String pas){
       progressbar.setVisibility(View.VISIBLE);
        auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(mai,pas).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("sucess","createUser:sucess");
                    FirebaseUser user=auth.getCurrentUser();
                    Data data=new Data(name,mob,mai,pas);
                    Log.d("DataEnterd hua",data.toString());
                    saveUserData(data,user);
                    startActivity(new Intent(SignupActivity.this,MapNavDrawer.class));
                    finish();
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(SignupActivity.this, " Signup Sucess ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignupActivity.this, "enter vaild info", Toast.LENGTH_SHORT).show();
                    Log.d("fail","createUser: fail");
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
        progressDialog.dismiss();
    }

    private void saveUserData(Data data, FirebaseUser user) {
        Log.d("Data",data.toString());
        databaseReference.child("Form Data").child(user.getUid()).setValue(data);

    }


}

