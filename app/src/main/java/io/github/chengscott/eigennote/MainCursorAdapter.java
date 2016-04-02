package io.github.chengscott.eigennote;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MainCursorAdapter extends CursorAdapter {

    public MainCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvTitle = (TextView) view.findViewById(android.R.id.text1);
        // Extract properties from cursor
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        // Populate fields with extracted properties
        tvTitle.setText(title);
    }
}
