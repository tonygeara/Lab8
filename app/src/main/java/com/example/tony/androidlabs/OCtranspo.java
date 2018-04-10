package com.example.tony.androidlabs;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class OCtranspo extends AppCompatActivity {

    protected ListView OCLIST;
    protected EditText OCEDITTEXT;
    protected Button destination;
    ArrayList<String> save;
    private Chatwindow chatwindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_octranspo);
        OCLIST = (ListView) findViewById(R.id.OCListView);
        OCEDITTEXT = (EditText) findViewById(R.id.OCeditText);
        destination = (Button) findViewById(R.id.OCsend);

        final String dest = String.valueOf(OCEDITTEXT.getText());

        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save.add(dest);
                ContentValues content = null;
                content.put(ChatDatabaseHelper.KEY_MESSAGE, OCEDITTEXT.getText().toString());
//                ChatDatabaseHelper db;
//                db.insert(ChatDatabaseHelper.TABLE_NAME, null, content);
//                db.notifyDataSetChanged();
//                edittt.setText("");
            }
        });
    }
}
