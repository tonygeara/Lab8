package com.example.tony.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Chatwindow extends Activity {

protected static final String NAME = "ChatWindow";
    private  Button send;
    private EditText edittt;
    private ArrayList<String> saveChat;
    private ListView chatView;
    private ChatDatabaseHelper dhHelper;
    private SQLiteDatabase db;
    private ContentValues content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatwindow);
        saveChat = new ArrayList<String> ();
        send =(Button) findViewById(R.id.send);
        edittt = (EditText) findViewById(R.id.editText);
        dhHelper = new ChatDatabaseHelper(this);
        chatView = (ListView) findViewById(R.id.ChatView);
        db = dhHelper.getWritableDatabase();
        content = new ContentValues();
        final ChatAdapter messageAdapter =new ChatAdapter( this );
        chatView.setAdapter (messageAdapter);

        final Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE}, null, null, null, null, null);
        while(cursor.isAfterLast()){
                String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                saveChat.add(message);
                Log.i(NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                cursor.moveToNext();}
        Log.i(NAME, "Cursor’s  column count =" + cursor.getColumnCount() );
        for(int i=0;i<cursor.getColumnCount();i++)
            Log.i(NAME, "Cursor’s  column name =" + cursor.getColumnName(i) );
        send .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageAdapter.notifyDataSetChanged();
                saveChat.add(edittt.getText().toString());
                content.put(ChatDatabaseHelper.KEY_MESSAGE, edittt.getText().toString());
                db.insert(ChatDatabaseHelper.TABLE_NAME, null, content);
                edittt.setText("");
            }
        });
    }
        private class ChatAdapter extends ArrayAdapter<String> {
//            HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
            public ChatAdapter(Context context) {
                super(context, 0);
            }
            public int getCount(){
            return saveChat.size();
            }
            public String getItem(int position){
                return saveChat.get(position);
            }
            public View getView (int position, View convertView, ViewGroup parent){
                LayoutInflater inflater = Chatwindow.this.getLayoutInflater();
                View result = null ;
                if(position%2 == 0)
                    result = inflater.inflate(R.layout.chat_row_incoming, null);
                else
                    result = inflater.inflate(R.layout.chat_row_outgoing, null);
                TextView message = (TextView)result.findViewById(R.id.message_text);
                message.setText( getItem(position)); // get the string at position
                return result;
            }
            public long getId (int position){
                return position;
            }
        }
        @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        Log.i(NAME,"IS ON DESTROY");
        }
    }