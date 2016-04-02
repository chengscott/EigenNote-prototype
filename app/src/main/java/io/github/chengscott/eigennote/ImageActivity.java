package io.github.chengscott.eigennote;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
                .rawQuery("select note.id as _id, note.*, image from note inner join image where subject_fk=? and chapter_fk=?;",
                        new String[]{subject, chapter});
        if (cursor != null) {
            while (cursor.moveToNext()) {

            }
            cursor.close();
        }
        // recyclerview adapter
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ImageCardAdapter(cursor));
    }
}
