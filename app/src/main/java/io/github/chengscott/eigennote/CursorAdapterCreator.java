package io.github.chengscott.eigennote;

import android.content.Context;
import android.database.Cursor;

public class CursorAdapterCreator {
    Context context;
    DataHelper dataHelper;

    public CursorAdapterCreator(Context context) {
        this.context = context;
        dataHelper = new DataHelper(context);
    }

    public MainCursorAdapter createSubjectCursor() {
        Cursor cursor = dataHelper.getReadableDatabase()
                .rawQuery("select id as _id, title from subject;", null);
        return new MainCursorAdapter(context, cursor, 0, MainCursorAdapter.Type.subject);
    }

    public MainCursorAdapter createChapterCursor(String title) {
        Cursor cursor = dataHelper.getReadableDatabase()
                .rawQuery("select id as _id, title from chapter where subject_fk=?;", new String[]{title});
        return new MainCursorAdapter(context, cursor, 0, MainCursorAdapter.Type.chapter);
    }
}
