package com.longtrinh.memorygame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lucky on 9/17/2016.
 */
public class ItemAdapter extends BaseAdapter {
    private Context context;
    private final int[] imageSource;

    public ItemAdapter(Context context, int[] imageSource) {
        this.context = context;
        this.imageSource = imageSource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.item_play, null);

            if(imageSource[position] != 0){
                ImageView showImage = (ImageView) gridView.findViewById(R.id.showSquare);
                showImage.setImageResource(imageSource[position]);

                showImage.setTag(imageSource[position]);

                // set image based on selected text

            }else{
                gridView.setVisibility(View.INVISIBLE);
            }






        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return imageSource.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
