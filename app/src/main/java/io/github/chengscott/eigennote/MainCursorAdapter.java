package io.github.chengscott.eigennote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MainCursorAdapter extends CursorAdapter {
    public enum Type { subject, chapter }
    private Type mType;

    public MainCursorAdapter(Context context, Cursor cursor, int flags, Type type) {
        super(context, cursor, flags);
        mType = type;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvTitle = (TextView) view.findViewById(android.R.id.text1);
        // Extract properties from cursor
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        // Populate fields with extracted properties
        tvTitle.setText(title);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = null;
                switch (mType) {
                    case subject:
                        intent = new Intent(context, ChapterActivity.class);
                        break;
                    case chapter:
                        intent = new Intent(context, ImageActivity.class);
                        intent.putExtra("subject", ((ChapterActivity) v.getContext()).getTitle());
                        break;
                }
                intent.putExtra("title", ((TextView) v).getText().toString());
                context.startActivity(intent);
            }
        });
        tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle(((TextView) v).getText())
                        .setItems(R.array.change_action, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (mType) {
                                    case subject:
                                        break;
                                    case chapter:
                                        break;
                                }
                            }
                        })
                        .show();
                return false;
            }
        });
    }
}
