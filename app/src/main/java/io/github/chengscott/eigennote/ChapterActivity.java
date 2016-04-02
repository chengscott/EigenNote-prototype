package io.github.chengscott.eigennote;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class ChapterActivity extends AppCompatActivity {
    private ListView chapterList;
    private FloatingActionButton chapterAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        chapterList = (ListView) findViewById(R.id.chapterList);
        chapterAdd = (FloatingActionButton) findViewById(R.id.chapterAdd);
        // get title from intent
        Intent intent = getIntent();
        final String title = intent.getStringExtra("title");
        setTitle(title);
        // query chapter
        chapterList.setAdapter(new CursorAdapterCreator(this).createChapterCursor(title));
        chapterAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = new EditText(view.getContext());
                editText.setSingleLine();
                editText.setId(R.id.chapterEditText);
                final String subject = getTitle().toString();
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.insert_chapter_title)
                        .setView(editText)
                        .setPositiveButton(R.string.insert_confirm,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                                        Dialog dialog = (Dialog) dialogInterface;
                                        // insert new chapter
                                        String chapter = ((EditText) dialog
                                                .findViewById(R.id.chapterEditText))
                                                .getText()
                                                .toString();
                                        DataHelper dataHelper = new DataHelper(getApplicationContext());
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("title", chapter);
                                        contentValues.put("subject_fk", subject);
                                        dataHelper.getWritableDatabase()
                                                .insert("chapter", null, contentValues);
                                        // snackbar notification
                                        Snackbar.make(findViewById(R.id.chapterCoordinatorLayout),
                                                String.format(getResources().getString(R.string.insert_snackbar), chapter),
                                                Snackbar.LENGTH_SHORT)
                                                .show();
                                        // reset adapter
                                        chapterList.setAdapter(new CursorAdapterCreator(dialog.getContext())
                                                .createChapterCursor(title));
                                    }
                                })
                        .show();
            }
        });
    }
}
