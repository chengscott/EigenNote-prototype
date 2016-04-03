package io.github.chengscott.eigennote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CreateSubjectTable = "CREATE TABLE IF NOT EXISTS `subject` (" +
            " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
            " `title` TEXT NOT NULL UNIQUE," +
            " `created_at` TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP" +
            ");";
    private static final String CreateChapterTable = "CREATE TABLE IF NOT EXISTS `chapter` (" +
            " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
            " `title` TEXT NOT NULL," +
            " `subject_fk` TEXT NOT NULL," +
            " `created_at` TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            " FOREIGN KEY(`subject_fk`) REFERENCES `subject`(`title`)" +
            ");";
    private static final String CreateImageTable = "CREATE TABLE IF NOT EXISTS `image` (" +
            " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
            " `subject_fk` TEXT NOT NULL," +
            " `chapter_fk` TEXT NOT NULL," +
            " `image` BLOB NOT NULL," +
            " `created_at` TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            " FOREIGN KEY(`subject_fk`) REFERENCES `subject`(`title`)," +
            " FOREIGN KEY(`chapter_fk`) REFERENCES `chapter`(`title`)" +
            ");";
    private static final String CreateNoteTable = "CREATE TABLE IF NOT EXISTS `note` (" +
            " `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
            " `image_fk` INTEGER NOT NULL," +
            " `x` INTEGER," +
            " `y` INTEGER," +
            " `width` INTEGER," +
            " `height` INTEGER," +
            " `type` TEXT DEFAULT 'Theorem'," +
            " FOREIGN KEY(`image_fk`) REFERENCES `image`(`id`)" +
            ");";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateSubjectTable);
        db.execSQL(CreateChapterTable);
        db.execSQL(CreateImageTable);
        db.execSQL(CreateNoteTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}