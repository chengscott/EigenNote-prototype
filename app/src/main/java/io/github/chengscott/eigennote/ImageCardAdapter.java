package io.github.chengscott.eigennote;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageCardAdapter extends RecyclerView.Adapter<ImageCardAdapter.ViewHolder> {
    private ArrayList<Note> mNotes;
    private HashMap<Integer, Bitmap> mImages;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView typeText;
        public ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            typeText = (TextView) view.findViewById(R.id.typeText);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    public ImageCardAdapter(ArrayList<Note> notes, HashMap<Integer, Bitmap> images) {
        mNotes = notes;
        mImages = images;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ImageCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_image, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // holder.mTextView.setText(mDataset[position]);
        Note note = mNotes.get(position);
        viewHolder.typeText.setText(note.getType());
        Bitmap bmp = Bitmap.createBitmap(mImages.get(note.getImage_fk()), note.getX(), note.getY(), note.getWidth(), note.getHeight());
        viewHolder.imageView.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }
}