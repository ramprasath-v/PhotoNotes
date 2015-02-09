package com.homeworkthree.photonotes;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements OnItemClickListener {

    private ListView listView;
    private List<String> savedImageNames;

    final List<String> photos = new ArrayList<>();
    private PhotoDatabase photoDB= new PhotoDatabase(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.photoList);
        listView.setOnItemClickListener(this);
        savedImageNames = photoDB.getAllImages();
        for (int i = 0; i < savedImageNames.size(); ++i) {
            photos.add(savedImageNames.get(i));
        }
        listView.setAdapter(new PhotoListAdapter(this, R.layout.custom_photo_layout, photos));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent savedImageIntent = new Intent(MainActivity.this,
                ViewPhotoActivity.class);
        savedImageIntent.putExtra("imageKey",position+1);
        startActivity(savedImageIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        int id = item.getItemId();
        if (id == R.id.action_new) {
            Intent addImage = new Intent(MainActivity.this, AddPhotoActivity.class);
            startActivity(addImage);
            return true;
        } else if (id == R.id.uninstall) {
            Uri packageURI = Uri.parse("package:com.homeworkthree.photonotes");
            Intent deleteIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(deleteIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

