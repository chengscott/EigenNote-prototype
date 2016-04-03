package io.github.chengscott.eigennote;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView subjectList;
    private FloatingActionButton subjectAdd;
    private final int PICK_FROM_FILE = 0x1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolBar));
        subjectList = (ListView) findViewById(R.id.subjectList);
        subjectAdd = (FloatingActionButton) findViewById(R.id.subjectAdd);
        // query subjects
        subjectList.setAdapter(new CursorAdapterCreator(this).createSubjectCursor());
        subjectAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = new EditText(view.getContext());
                editText.setSingleLine();
                editText.setId(R.id.subjectEditText);
                new AlertDialog.Builder(view.getContext())
                        .setTitle(R.string.insert_subject_title)
                        .setView(editText)
                        .setPositiveButton(R.string.insert_confirm,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int whichButton) {
                                        Dialog dialog = (Dialog) dialogInterface;
                                        // insert new subject
                                        String subject = ((EditText) dialog
                                                .findViewById(R.id.subjectEditText))
                                                .getText()
                                                .toString();
                                        DataHelper dataHelper = new DataHelper(getApplicationContext());
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("title", subject);
                                        dataHelper.getWritableDatabase()
                                                .insert("subject", null, contentValues);
                                        // snackbar notification
                                        Snackbar.make(findViewById(R.id.subjectCoodinatorLayout),
                                                String.format(getResources().getString(R.string.insert_snackbar), subject),
                                                Snackbar.LENGTH_SHORT)
                                                .show();
                                        // reset adapter
                                        subjectList.setAdapter(new CursorAdapterCreator(dialog.getContext())
                                                .createSubjectCursor());
                                    }
                                })
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.readImage:
                startActivityForResult(
                        Intent.createChooser(
                                new Intent(Intent.ACTION_GET_CONTENT)
                                        .setType("image/*"), getString(R.string.import_image)),
                        PICK_FROM_FILE);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_FILE) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                // String filePath = new File(selectedImage.getPath()).getAbsolutePath();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Findpoint points = new Findpoint();
                points.FindPoint(bitmap);
            }
        }
    }
}