package com.homeworkthree.photonotes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class PhotoListAdapter extends ArrayAdapter<String>{
    private final List<String> photos;

    public PhotoListAdapter(Context context, int resource, List<String> photos) {
        super(context, resource, photos);
        this.photos = photos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getImageView(position, convertView, parent);
    }
    public View getImageView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.custom_photo_layout, null);

        TextView textView = (TextView) row.findViewById(R.id.rowText);
        textView.setText(photos.get(position));

        return row;
    }
}
