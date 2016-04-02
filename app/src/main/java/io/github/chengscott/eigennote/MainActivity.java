package io.github.chengscott.eigennote;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private final String DBNAME = "data.db";
    private SQLiteDatabase db;
    private ListView listview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listview);
        db = openOrCreateDatabase(DBNAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.execSQL("PRAGMA foreign_keys = \"1\";");
        CreateTableIfNotExists();
        Cursor cursor = db.rawQuery("select id as _id, title from subject;", null);
        /*ArrayList<String> data = new ArrayList();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                data.add(cursor.getString(0));
            }
            cursor.close();
        }*/
        // listview.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data));
        MainCursorAdapter mainCursorAdapter = new MainCursorAdapter(MainActivity.this, cursor, 0);
        listview.setAdapter(mainCursorAdapter);
    }

    private void CreateTableIfNotExists() {
        final String CreateSubjectTable = "CREATE TABLE IF NOT EXISTS `subject` (" +
                " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " `title` TEXT NOT NULL UNIQUE," +
                " `created_at` TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP" +
                ");";
        final String CreateChapterTable = "CREATE TABLE IF NOT EXISTS `chapter` (" +
                " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " `title` TEXT NOT NULL," +
                " `subject_fk` TEXT NOT NULL," +
                " `created_at` TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                " FOREIGN KEY(`subject_fk`) REFERENCES `subject`(`title`)" +
                ");";
        final String CreateImageTable = "CREATE TABLE IF NOT EXISTS `image` (" +
                " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " `subject_fk` TEXT NOT NULL," +
                " `chapter_fk` TEXT NOT NULL," +
                " `image` BLOB NOT NULL," +
                " `created_at` TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                " FOREIGN KEY(`subject_fk`) REFERENCES `subject`(`title`)," +
                " FOREIGN KEY(`chapter_fk`) REFERENCES `chapter`(`title`)" +
                ");";
        final String CreateNoteTable = "CREATE TABLE IF NOT EXISTS `note` (" +
                " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " `image_fk` INTEGER NOT NULL," +
                " `x` REAL," +
                " `y` REAL," +
                " `width` REAL," +
                " `height` REAL," +
                " `type` TEXT DEFAULT 'Point'," +
                " FOREIGN KEY(`image_fk`) REFERENCES `image`(`id`)" +
                ");";
        db.execSQL(CreateSubjectTable);
        db.execSQL(CreateChapterTable);
        db.execSQL(CreateImageTable);
        db.execSQL(CreateNoteTable);
    }
}
