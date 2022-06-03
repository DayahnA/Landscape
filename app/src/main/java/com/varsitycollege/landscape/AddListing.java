package com.varsitycollege.landscape;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
// firebase importing database
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.android.material.floatingactionbutton.FloatingActionButton;







    public class AddListing extends AppCompatActivity implements View.OnClickListener{
        SharedPreferences sp;
        FirebaseAuth mAuth;
        FirebaseUser mUser;
        String title,userID,caption,description;
        String cat;
        final EditText txtTitle =findViewById(R.id.txtTitle);
        final  EditText txtCaption =findViewById(R.id.txtCaption);
        final  EditText txtDescription =findViewById(R.id.txtDescription);
        final Spinner spnCategory =findViewById(R.id.spnCategory);
        //creating an instance of database
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        /// create reference of database
        DatabaseReference dbRef = db.getReference(ListingDetails.class.getSimpleName());
        //initialize  variables

        String[] categories = {"Water", "Animals", "Plants/Trees", "Land", "Life", "Sky", "Flowers", "Space"};
        Spinner category;
        AddListing ad = new AddListing();
        Button saveAddList = (Button) findViewById(R.id.saveAddList);
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Set the content of the activity to use the activity_main.xml layout file
            setContentView(R.layout.fragment_add_listing);


            sp = getSharedPreferences("myListPrefs",Context.MODE_PRIVATE);

            SharedPreferences.Editor editor= sp.edit();

            editor.putString("title",userID);
            editor.putString("title",title);
            editor.putString("caption",caption);
            editor.putString("description",description);
            editor.putString("category",cat);
            editor.commit();




        //set the listener
        saveAddList.setOnClickListener(this);





        //populating a database


        }

        @Override
        public void onClick(View view) {
         if (view.getId()== R.id.saveAddList)
         {
              userID=  mUser.getUid().toString();
              title= txtTitle.getText().toString();
              caption= txtTitle.getText().toString();
              description = txtTitle.getText().toString();
              cat= spnCategory.toString();

              ListingDetails ld = new ListingDetails(txtTitle.getText().toString(), txtCaption.getText().toString(), txtDescription.getText().toString(), spnCategory.toString());
              add(ld);
         }
        }

        public void add(ListingDetails ld)
        {

            dbRef.push().setValue(ld);



        }

        //
    }

/*
    ImageView selectedImage;
    FloatingActionButton fab;
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_add_listing);
//
//        selectedImage = findViewById(R.id.image);
//        fab = findViewById(R.id.fab_addimage);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddListing.this, "Camera fab is clicked", Toast.LENGTH_SHORT).show();
//                if (ActivityCompat.checkSelfPermission(AddListing.this,
//                        Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    final String[] permissions = {Manifest.permission.CAMERA};
//
//                    ActivityCompat.requestPermissions(AddListing.this,
//                            permissions, REQUEST_IMAGE_CAPTURE_PERMISSION);
//                } else {
//
//                    takePhoto();
//                }
//            }
//        });

    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == REQUEST_IMAGE_CAPTURE_PERMISSION &&
//                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
//                        PackageManager.PERMISSION_GRANTED) {
//
//            takePhoto();
//        }
//    }
//
//    private void takePhoto() {
//        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
//    }
//
//    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            fab.setImageBitmap(bitmap);
//
        }
 */


