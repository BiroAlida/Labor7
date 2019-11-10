package com.example.labor7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinnerLocation, spinnerDepartment;
    private ImageView profilePic;
    private static final int PICK_IMAGE = 1;
    private Uri imageUri;
    private Button pickDate, send;
    private TextView tvDate;
    private EditText etName, etExpectation;
    private RadioGroup rbgender, rbyear;
    private RadioButton rbmale,rbfemale,rbI,rbII,rbIII,rbIV;
    private CheckBox cbdance, cbswim, cbdraw;
    private User user;
    private String name, date, gender, hobby, studyYear, expectation, url, userId;
    private String location = "", department = "";
    private ArrayList<String> hobbies = new ArrayList<String>();
    private DatabaseReference ref;
    private FirebaseAuth mauth;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ref = FirebaseDatabase.getInstance().getReference().child("User");
        mStorageRef = FirebaseStorage.getInstance().getReference("User");
        user = new User();

        etName = findViewById(R.id.editTextName);
        rbgender = findViewById(R.id.radioGrp);
        rbyear = findViewById(R.id.radioGrpYear);
        rbmale = findViewById(R.id.radioButtonMale);
        rbfemale = findViewById(R.id.radioButtonFemale);
        rbI = findViewById(R.id.radioButtonI);
        rbII = findViewById(R.id.radioButtonII);
        rbIII = findViewById(R.id.radioButtonIII);
        rbIV = findViewById(R.id.radioButtonIV);
        tvDate = findViewById(R.id.textViewDate);
        cbdance = findViewById(R.id.checkBoxDance);
        cbswim = findViewById(R.id.checkBoxSwim);
        cbdraw = findViewById(R.id.checkBoxDraw);
        etExpectation = findViewById(R.id.editTextExpectations);



        spinnerLocation = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Location, android.R.layout.simple_spinner_item); // a string.xml-ben letrehozott Location array adatainak kinyerese
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // passing a layout for dropdown item
        spinnerLocation.setAdapter(adapter); // connecting the spinner to the adapter

        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        ArrayAdapter<CharSequence> adapterDepartment = ArrayAdapter.createFromResource(this, R.array.Department, android.R.layout.simple_spinner_item);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapterDepartment);


        profilePic = findViewById(R.id.imageView);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });

        pickDate = findViewById(R.id.button);
        tvDate = findViewById(R.id.textViewDate);

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                tvDate.setText(year + "." + (month + 1) + "." + day);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();

            }

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        });

        send = findViewById(R.id.buttonSend);
        send.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                // resizing the choosen picture:
                Bitmap output = Bitmap.createBitmap(130, 130, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(output);
                Matrix m = new Matrix();
                m.setScale((float) 130 / bitmap.getWidth(), (float) 130 / bitmap.getHeight());
                canvas.drawBitmap(bitmap, m, new Paint());

                profilePic.setImageBitmap(output);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == send) {

            name = etName.getText().toString().trim();
            location = spinnerLocation.getSelectedItem().toString();

            if(rbgender.getCheckedRadioButtonId() == R.id.radioButtonMale)
            {
                gender = rbmale.getText().toString();
            }
            else{
                gender = rbfemale.getText().toString();
            }

            date = tvDate.getText().toString().trim();

            // if a certain hobby is checked by the user, put it in the hobbies ArrayList
            CheckBox[] chkBoxs = {cbdance,cbswim,cbdraw};
            for(int i = 0; i< chkBoxs.length; ++i){

                if(chkBoxs[i].isChecked())
                {
                    hobby = chkBoxs[i].getText().toString().trim();
                    hobbies.add(hobby);
                }
            }

            department = spinnerDepartment.getSelectedItem().toString();

            if(rbyear.getCheckedRadioButtonId() == rbI.getId())
            {
                studyYear = rbI.getText().toString().trim();
            }
            else if(rbyear.getCheckedRadioButtonId() == rbII.getId()){

                studyYear = rbII.getText().toString().trim();
            }
            else if(rbyear.getCheckedRadioButtonId() == rbIII.getId()){

                studyYear = rbIII.getText().toString().trim();
            }
            else{
                studyYear = rbIV.getText().toString().trim();
            }

            expectation = etExpectation.getText().toString().trim();

            user.setDate(date);
            user.setName(name);
            user.setLocation(location);
            user.setGender(gender);
            user.setHobby(hobbies);
            user.setDepartment(department);
            user.setYearOfStudy(studyYear);
            user.setExpectations(expectation);

            uploadFile();

            Intent showData = new Intent(MainActivity.this, DataActivity.class);
            startActivity(showData);
            finish();

        }
    }

    // getting the extension from our file
    private String getFileExtension(Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile()
    {
        if(imageUri != null)
        {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                url = taskSnapshot.getUploadSessionUri().toString();

                                user.setImageUrl(url);
                                userId = ref.push().getKey();
                                ref.child(userId).setValue(user);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                        }
                    });
        }
        else{
            Toast.makeText(this,"No image selected", Toast.LENGTH_LONG).show();
        }
    }
}
