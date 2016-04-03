package io.github.chengscott.eigennote;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainCursorAdapter extends CursorAdapter {
    public enum Type {subject, chapter}

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
        final TextView tvTitle = (TextView) view.findViewById(android.R.id.text1);
        // Extract properties from cursor
        final String title = cursor.getString(cursor.getColumnIndex("title"));
        // Populate fields with extracted properties
        tvTitle.setText(title);
        ///
        // tvTitle.setTag(R.id.tagID, cursor.getString(cursor.getColumnIndex("_id")));
        /*try {
            tvTitle.setTag(R.id.tagTitle, ((AppCompatActivity) view.getContext()).getTitle());
        } catch (Exception e) {
        }*/
        ///
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
                        intent.putExtra("subject", ((ChapterActivity) v.getContext()).getTitle());// v.getTag(R.id.tagTitle).toString());
                        break;
                }
                intent.putExtra("title", ((TextView) v).getText().toString());
                context.startActivity(intent);
            }
        });
        // rename or delete
        /*tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final AppCompatActivity activity = (AppCompatActivity) v.getContext();
                ///
                final String ID = v.getTag(R.id.tagID).toString();
                ///
                final String originalTitle = ((TextView) v).getText().toString();
                new AlertDialog.Builder(v.getContext())
                        .setTitle(originalTitle)
                        .setItems(R.array.change_action, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Dialog dialog = (Dialog) dialogInterface;
                                switch (which) {
                                    case 0:
                                        // rename
                                        EditText editText = new EditText(dialog.getContext());
                                        editText.setText(originalTitle);
                                        editText.setSelection(editText.getText().length());
                                        editText.setSingleLine();
                                        editText.setId(R.id.renameEditText);
                                        new AlertDialog.Builder(dialog.getContext())
                                                .setTitle(R.string.rename_title)
                                                .setView(editText)
                                                .setPositiveButton(R.string.rename_confirm,
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialogInterface, int whichButton) {
                                                                Dialog dialog = (Dialog) dialogInterface;
                                                                // insert new subject
                                                                String title = ((EditText) dialog
                                                                        .findViewById(R.id.renameEditText))
                                                                        .getText()
                                                                        .toString();
                                                                DataHelper dataHelper = new DataHelper(dialog.getContext());
                                                                ContentValues contentValues = new ContentValues();
                                                                contentValues.put("title", title);
                                                                switch (mType) {
                                                                    case subject:
                                                                        dataHelper.getWritableDatabase()
                                                                                .update("subject", contentValues, "title=?", new String[]{originalTitle});
                                                                        // TODO: recusively update chapter, image
                                                                        // snackbar notification
                                                                        Snackbar.make(activity.findViewById(R.id.subjectCoodinatorLayout),
                                                                                String.format(dialog.getContext().getResources().getString(R.string.rename_snackbar), title),
                                                                                Snackbar.LENGTH_SHORT)
                                                                                .show();
                                                                        // reset adapter
                                                                        ((ListView) activity.findViewById(R.id.subjectList))
                                                                                .setAdapter(new CursorAdapterCreator(dialog.getContext())
                                                                                        .createSubjectCursor());
                                                                        break;
                                                                    case chapter:
                                                                        dataHelper.getWritableDatabase()
                                                                                .update("chapter", contentValues, "id=?", new String[]{ID});
                                                                        // TODO: recusively update image
                                                                        // snackbar notification
                                                                        Snackbar.make(activity.findViewById(R.id.chapterCoordinatorLayout),
                                                                                String.format(dialog.getContext().getResources().getString(R.string.rename_snackbar), title),
                                                                                Snackbar.LENGTH_SHORT)
                                                                                .show();
                                                                        // reset adapter
                                                                        ((ListView) activity.findViewById(R.id.chapterList))
                                                                                .setAdapter(new CursorAdapterCreator(dialog.getContext())
                                                                                        .createSubjectCursor());
                                                                        break;
                                                                }
                                                            }
                                                        })
                                                .show();
                                        break;
                                    case 1:
                                        // delete
                                        break;
                                }
                            }
                        })
                        .show();
                return false;
            }
        });*/
    }
}
