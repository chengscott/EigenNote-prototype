package io.github.chengscott.eigennote;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        // get title and subject
        Intent intent = getIntent();
        String subject = intent.getStringExtra("subject");
        String chapter = intent.getStringExtra("title");
        setTitle(chapter);
        // query images
        DataHelper dataHelper = new DataHelper(this);
        Cursor cursor = dataHelper.getReadableDatabase()
                .rawQuery("select note.id as _id, note.* from note inner join image where subject_fk=? and chapter_fk=? and image_fk=image.id;",
                        new String[]{subject, chapter});
        ArrayList<Note> notes = new ArrayList<>();
        HashMap<Integer, Bitmap> images = new HashMap<>();
        ArrayList<String> images_fk = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                notes.add(new Note(
                                cursor.getInt(cursor.getColumnIndex("x")),
                                cursor.getInt(cursor.getColumnIndex("y")),
                                cursor.getInt(cursor.getColumnIndex("width")),
                                cursor.getInt(cursor.getColumnIndex("height")),
                                cursor.getString(cursor.getColumnIndex("type")),
                                cursor.getInt(cursor.getColumnIndex(("image_fk")))
                        )
                );
                images_fk.add(String.valueOf(cursor.getInt(cursor.getColumnIndex(("image_fk")))));
            }
            cursor.close();
        }
        // read image
        String[] arr_images_fk = images_fk.toArray(new String[0]);
        cursor = dataHelper.getReadableDatabase()
                .rawQuery("select id as _id, image from image where id in (" + makePlaceholders(images_fk.size()) + ");", arr_images_fk);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                byte[] data = cursor.getBlob(cursor.getColumnIndex("image"));
                images.put(cursor.getInt(cursor.getColumnIndex("_id")),
                        BitmapFactory.decodeByteArray(data, 0, data.length));
            }
            cursor.close();
        }
        // recyclerview adapter
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ImageCardAdapter(notes, images));
    }

    private String makePlaceholders(int length) {
        StringBuilder sb = new StringBuilder(length * 2 - 1).append("?");
        for (int i = 1; i < length; ++i) {
            sb.append(", ?");
        }
        return sb.toString();
    }
}
