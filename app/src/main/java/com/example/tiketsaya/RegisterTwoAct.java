package com.example.tiketsaya;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class RegisterTwoAct extends AppCompatActivity {

    LinearLayout btn_back;
    Button btn_continue_reg, btn_add_photo;
    ImageView pic_photo_register_user;
    EditText nama_lengkap, bio;

    DatabaseReference reference;
    StorageReference storage;

    Uri photo_location;
    Integer photo_max = 1;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        // ===========================
        getUsernameLocal();
        // ===========================


        // ============================================================================
        btn_back = findViewById(R.id.btn_back);
        btn_continue_reg  = findViewById(R.id.btn_continue_reg);
        btn_add_photo = findViewById(R.id.btn_add_photo);
        pic_photo_register_user = findViewById(R.id.pic_photo_register_user);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        // ============================================================================


        // ============================== Button Add Photo ============================
        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });
        // ============================================================================


        // ============================================================================
        btn_continue_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ------ Ubah state menjadi loading ------
                btn_continue_reg.setEnabled(false);
                btn_continue_reg.setText("Loading ...");
                // ----------------------------------------

                final String xnama_lengkap = nama_lengkap.getText().toString();
                final String xbio = bio.getText().toString();

                if (xnama_lengkap.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama tidak boleh kosong !", Toast.LENGTH_SHORT).show();
                    // ------- Ubah state menjadi CONTINUE -------
                    btn_continue_reg.setEnabled(true);
                    btn_continue_reg.setText("CONTINUE");
                    // -------------------------------------------
                } else {
                    if (xbio.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Harap Mengisi BIO/Passion anda !", Toast.LENGTH_LONG).show();
                        // Ubah state menjadi CONTINUE
                        btn_continue_reg.setEnabled(true);
                        btn_continue_reg.setText("CONTINUE");
                    } else {
                        if (xbio.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Harap Mengisi BIO/Passion anda !", Toast.LENGTH_LONG).show();
                            // Ubah state menjadi CONTINUE
                            btn_continue_reg.setEnabled(true);
                            btn_continue_reg.setText("CONTINUE");
                        } else {
                            // Save in firebase
                            reference = FirebaseDatabase.getInstance()
                                    .getReference().child("Users").child(username_key_new);
                            storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);

                            // Validation file photo
                            if (photo_location != null){
                                final StorageReference storageReference1 =
                                        storage.child(System.currentTimeMillis() + "." +
                                                getFileExtension(photo_location));

                                storageReference1.putFile(photo_location)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String uri_photo = uri.toString();
                                                        reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                                        reference.getRef().child("nama_lengkap").setValue(nama_lengkap.getText().toString());
                                                        reference.getRef().child("bio").setValue(bio.getText().toString());
                                                    }
                                                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Uri> task) {
                                                        // switch activity
                                                        Intent continuetosuccessact = new Intent(RegisterTwoAct.this,SuccessRegisterAct.class);
                                                        startActivity(continuetosuccessact);
                                                    }
                                                });


                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                    }
                                });
                            }
                        }

                    }
                }



            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
//                Intent backtoprev = new Intent(RegisterTwoAct.this,RegisterOneAct.class);
//                startActivity(backtoprev);
            }
        });
    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(pic_photo_register_user);
        }
    }

   public void getUsernameLocal(){
       SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
       username_key_new = sharedPreferences.getString(username_key, "");
   }
}