package io.github.chengscott.eigennote;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private DataHelper dataHelper;
    private ListView listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listview);
        dataHelper = new DataHelper(this);
        dataHelper.getWritableDatabase().execSQL("PRAGMA foreign_keys = \"1\";");
        Cursor cursor = dataHelper.getReadableDatabase()
                .rawQuery("select id as _id, title from subject;", null);
        MainCursorAdapter mainCursorAdapter = new MainCursorAdapter(MainActivity.this, cursor, 0);
        listview.setAdapter(mainCursorAdapter);
    }
}
