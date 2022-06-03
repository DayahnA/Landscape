package com.varsitycollege.landscape;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
// firebase importing database
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddListingFragment extends Fragment {
    ImageView imageView;
    FloatingActionButton fab;
    private static final int PICK_FROM_GALLERY = 1;
    private static final int REQUEST_IMAGE_CAPTURE_PERMISSION = 100;
    private EditText txtTitle;
    private Spinner spnCategory;
    private EditText txtCaption;
    private EditText txtDescription;
    private Button btn_save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_add_listing, container, false);
    //declare firebase
        FirebaseAuth mAuth;
        FirebaseUser mUser;

        //initialize
        final View rootView = inflater.inflate(R.layout.fragment_add_listing, container, false);
         fab = (FloatingActionButton) rootView.findViewById(R.id.fab_addimage);
        imageView = (ImageView) rootView.findViewById(R.id.image);
        txtTitle = (EditText) rootView.findViewById(R.id.txtTitle);
        txtCaption= (EditText) rootView.findViewById(R.id.txtCaption);
        txtDescription = (EditText) rootView.findViewById(R.id.txtDescription);
        btn_save = (Button) rootView.findViewById(R.id.btnSave);

        // Adding category names to spinner https://www.tutorialspoint.com/how-can-i-add-items-to-a-spinner-in-android
        Spinner spinner = rootView.findViewById(R.id.spnCategory);
        //creating an instance of database
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        /// create reference of database
        DatabaseReference dbRef = db.getReference(ListingDetails.class.getSimpleName());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Water");
        arrayList.add("Animals");
        arrayList.add("Plants/Trees");
        arrayList.add("Land");
        arrayList.add("Life");
        arrayList.add("Sky");
        arrayList.add("Flowers");
        arrayList.add("Space");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoryNames = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + categoryNames,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
   // save button listener and database PUSH method
         btn_save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ListingDetails ld = new ListingDetails(txtTitle.getText().toString(), txtCaption.getText().toString(), txtDescription.getText().toString(), spnCategory.toString());
               //push to database
                 dbRef.push().setValue(ld);
             }
         });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(container.getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    final String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                    ActivityCompat.requestPermissions(getActivity(),
                            permissions, REQUEST_IMAGE_CAPTURE_PERMISSION);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_FROM_GALLERY);

                }
            }
        });

        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Uri selectedImage  = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
