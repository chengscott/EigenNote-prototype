package io.github.chengscott.eigennote;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ImageCardAdapter extends RecyclerView.Adapter<ImageCardAdapter.ViewHolder> {
    private Cursor mCursor;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }

    public ImageCardAdapter(Cursor cursor) {
        mCursor = cursor;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // holder.mTextView.setText(mDataset[position]);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
}