package io.github.chengscott.eigennote;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
        String title = intent.getStringExtra("title");
        setTitle(title);
        // query chapter
        DataHelper dataHelper = new DataHelper(this);
        Cursor cursor = dataHelper.getReadableDatabase()
                .rawQuery("select id as _id, title from chapter where subject_fk=?;", new String[]{title});
        MainCursorAdapter mainCursorAdapter = new MainCursorAdapter(this, cursor, 0);
        chapterList.setAdapter(mainCursorAdapter);
        cursor.close();
        chapterAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
