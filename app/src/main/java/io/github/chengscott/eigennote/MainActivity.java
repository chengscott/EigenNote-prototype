package io.github.chengscott.eigennote;

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
        // query subjects
        subjectList.setAdapter(new CursorAdapterCreator(this).createSubjectCursor());
    }
}
