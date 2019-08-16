package com.example.mytreeapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mytreeapplication.model.Data;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {
    private static final int REQUEST_TAKE_PHOTO = 1;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "ProfileActivity";
    @BindView(R.id.emailProfile)
    TextView emailProf;
    @BindView(R.id.contactProfile)
    TextView contProf;
    @BindView(R.id.topname)
    TextView topName;
    @BindView(R.id.profInnername)
    TextView innerName;
    @BindView(R.id.img1)
    CircularImageView img;
//    @BindView(R.id.imageView1)
//
//    CircularImageView cirImg;
        Uri photoUri;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Profile");
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);

        mStorageRef= FirebaseStorage.getInstance().getReference();


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        firebaseUser=firebaseAuth.getCurrentUser();
        String userKey=firebaseUser.getUid();


        databaseReference.child("Form Data").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Data data=dataSnapshot.getValue(Data.class);

               // nameProf.setText(data.getUname());
                topName.setText(data.getUname());
                innerName.setText(data.getUname());
                emailProf.setText(data.getUmail());
                contProf.setText(data.getUmob());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @OnClick(R.id.fab)
    public  void fab(){
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager())!=null){
            // Create the File where the photo should go
            File photoFile = null;
            try{
                photoFile=createImageFile();
            }catch (IOException e){
                Log.d("Exception1",e.getMessage());

            }
            if(photoFile!=null){
                photoUri= FileProvider.getUriForFile(this,
                        "com.example.mytreeapplication.fileprovider",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    Bitmap bitmap;
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK && requestCode==REQUEST_TAKE_PHOTO){
            try{
                bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),photoUri);
                img.setImageBitmap(bitmap);
              //  cirImg.setImageBitmap(bitmap);
                addToStorage();
                Log.d(TAG,"success click : ");
            }catch (IOException e){
                Log.d(TAG,"error : "+e.getMessage());

            }
        }
    }



    private Bitmap resizeBitmap(Bitmap bitmap){
        return Bitmap.createScaledBitmap(bitmap, 240, 240, false);
    }
    private StorageReference mStorageRef;
    private void addToStorage() {
        mStorageRef= FirebaseStorage.getInstance().getReference();
        final StorageReference storageReference=mStorageRef.child("profile pic")
                .child(FirebaseAuth.getInstance().getUid()+".jpg");

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
        byte[] data=byteArrayOutputStream.toByteArray();
        storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                    Log.d(TAG,taskSnapshot.toString());

                Glide.with(getApplicationContext()).load(taskSnapshot).into(img);
                Toast.makeText(ProfileActivity.this, "Successfully Loaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileActivity.this, "Error in uploading...", Toast.LENGTH_SHORT).show();
                Log.d(TAG,e.getMessage());
            }
        });

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d("success ",uri.toString());
                Log.d("Url ","url : "+storageReference.getDownloadUrl());
               // Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

               // Picasso.get().load(uri).into(img);
                img.setImageBitmap(Bitmap.createBitmap(bitmap));
                Toast.makeText(ProfileActivity.this, "Success "+uri, Toast.LENGTH_SHORT).show();
            }
        });

    }



    private File createImageFile()throws IOException {
        String imageFileName=FirebaseAuth.getInstance().getUid();
        File storeDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image=File.createTempFile(imageFileName,"jpg",storeDir);
        currentPhotoPath=image.getAbsolutePath();
        return image;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // overridePendingTransition(R.transition.left_anim, R.transition.right_anim);
        onBackPressed();

        return true;
    }




}
