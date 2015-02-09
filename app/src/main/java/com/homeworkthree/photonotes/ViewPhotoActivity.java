package com.homeworkthree.photonotes;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;


public class ViewPhotoActivity extends ActionBarActivity{

    private ImageView displayImage;
    private PhotoDatabase photoDB= new PhotoDatabase(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        TextView imageText = (TextView) findViewById(R.id.imageTextID);
        displayImage = (ImageView) findViewById(R.id.viewPhotoID);

        Intent intent = getIntent();
        int image_key=intent.getIntExtra("imageKey",-1);

        Photos photo=photoDB.getValues(image_key);
        imageText.setText(photo.getImageName());
        loadImageFromStorage(photo.getImagePath());

    }
    private void loadImageFromStorage(String path)
    {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myPath=new File(directory,path);
        String s=  myPath.getAbsolutePath();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap b = BitmapFactory.decodeFile(s, options);
        displayImage.setImageBitmap(b);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_view_photo, menu);
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


}
