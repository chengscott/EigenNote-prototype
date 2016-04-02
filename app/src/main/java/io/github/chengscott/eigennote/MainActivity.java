package io.github.chengscott.eigennote;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView subjectList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        subjectList = (ListView) findViewById(R.id.subjectList);
        // setup db
        DataHelper dataHelper = new DataHelper(this);
        dataHelper.getWritableDatabase().execSQL("PRAGMA foreign_keys = \"1\";");
        // query subjects
        Cursor cursor = dataHelper.getReadableDatabase()
                .rawQuery("select id as _id, title from subject;", null);
        MainCursorAdapter mainCursorAdapter =
                new MainCursorAdapter(MainActivity.this, cursor, 0, MainCursorAdapter.Type.subject);
        subjectList.setAdapter(mainCursorAdapter);
    }


}
