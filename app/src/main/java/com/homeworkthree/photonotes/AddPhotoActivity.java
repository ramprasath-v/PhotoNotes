package com.homeworkthree.photonotes;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddPhotoActivity extends ActionBarActivity implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private SQLiteDatabase db=null;
    private String imageName;
    private EditText imageNameText;
    private String timeStamp;
    private Bitmap imageBitmap;
    private PhotoDatabase photoDB;
    private String imageNameTextValue;
    private String absoluteImagePath;
    private String directoryPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
         imageNameText = (EditText) findViewById(R.id.imageEditText);
        Button takePhotoButton = (Button) findViewById(R.id.takePhotoButton);
        Button saveButton = (Button) findViewById(R.id.savePhotoButton);

        photoDB=new PhotoDatabase(this);
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        db=photoDB.getWritableDatabase();


        takePhotoButton.setOnClickListener(this);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                imageNameTextValue = imageNameText.getText().toString();
                if(imageNameTextValue.matches("")){
                    displayErrorMessage("Please enter a name for the image");
                }
                else if (imageBitmap == null){
                    displayErrorMessage("Please take a picture ");
                }
                else {
                    imageName = imageNameTextValue + timeStamp + ".jpeg";
                    directoryPath = saveToInternalStorage(imageBitmap, imageName);
                    absoluteImagePath = directoryPath+"/"+imageName;
                    ContentValues cv = new ContentValues();
                    cv.put("image_name", imageNameTextValue);
                    cv.put("image_path", absoluteImagePath);
                    db.insert("savedimages", null, cv);
                    finish();
                }

            }
        });
    }

    public void displayErrorMessage(String message){
        Toast.makeText(this, message , Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_photo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.uninstall) {
            Uri packageURI = Uri.parse("package:com.homeworkthree.photonotes");
            Intent deleteIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(deleteIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.takePhotoButton:

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
        }

    }

    private String saveToInternalStorage(Bitmap bitmapImage,String name) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myPath = new File(directory, name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

}
