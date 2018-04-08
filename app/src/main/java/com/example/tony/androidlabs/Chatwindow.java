package com.example.tony.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Chatwindow extends Activity {

protected static final String NAME = "ChatWindow";
protected static final int REQUEST_MSG_DETAILS = 1;
protected static final int REQUEST_MSG_DELETE= 2;
    private  Button send;
    private EditText edittt;
    public static  ArrayList<String> saveChat;
    private ArrayList<Long> chatIDs;
    private ListView chatView;
    private ChatDatabaseHelper dhHelper;
    private SQLiteDatabase db;
    private ContentValues content;
    private ChatAdapter messageAdapter;
    private boolean exist ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatwindow);
        String[] allCol = {ChatDatabaseHelper.KEY_ID,ChatDatabaseHelper.KEY_MESSAGE};
        dhHelper = new ChatDatabaseHelper(this);

        db = dhHelper.getWritableDatabase();

        send =(Button) findViewById(R.id.send);
        edittt = (EditText) findViewById(R.id.editText);
        chatView = (ListView) findViewById(R.id.ChatView);

        saveChat = new ArrayList<> ();
        chatIDs = new ArrayList<>();



        messageAdapter =new ChatAdapter( this );
        chatView.setAdapter (messageAdapter);

        exist = (findViewById(R.id.fragment_message_layout) != null);


         final Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME, allCol, null, null, null, null, null);

        if (cursor.moveToFirst()) {


         while(!cursor.isAfterLast()){
                             Log.i(NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                saveChat.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
               chatIDs.add(cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID)));
                cursor.moveToNext();}
        }
                cursor.close();

        content = new ContentValues();
        final FragmentManager manager = getFragmentManager();

        chatView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                String query = "SELECT * FROM " + ChatDatabaseHelper.TABLE_NAME;


//                SQLiteDatabase cursor = dhHelper.getReadableDatabase() ;
                Cursor cursor  = dhHelper.getRead(query);
                cursor.moveToPosition(position);
                int idd = (int) id;
                String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
                Bundle newBundle = new Bundle();
                newBundle.putInt("id",idd);
                newBundle.putString("MESSAGE:" , message );
                newBundle.putBoolean("FLAG" , exist);

//                newBundle.putString("MESSAGE",messageAdapter.getItem(position));
//                newBundle.putLong("id",messageAdapter.getItemId(position));
                if(exist){
                    MessageFragment messagefragment = new MessageFragment();
                   FragmentTransaction ft = manager.beginTransaction();
//                    FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.fragment_message_layout, messagefragment);
                  ft.add(R.id.fragment_message_layout, messagefragment,"TABLETFRAGMENT");
                    messagefragment.setArguments(newBundle);
                    ft.commit();
                }
                else {
                    Intent intent = new Intent(Chatwindow.this,MessageDetail.class);
                    intent.putExtras(newBundle);
                    startActivityForResult(intent,REQUEST_MSG_DETAILS);
                }
            }
        });
        send .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveChat.add(edittt.getText().toString());
                content.put(ChatDatabaseHelper.KEY_MESSAGE, edittt.getText().toString());
                db.insert(ChatDatabaseHelper.TABLE_NAME, null, content);
                messageAdapter.notifyDataSetChanged();
                edittt.setText("");
            }
        });


    }



    private class ChatAdapter extends ArrayAdapter<String> {
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

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == REQUEST_MSG_DELETE){
            long msgID = data.getLongExtra("msgID",-1);
            Log.i(NAME,"REQUEST DELETING MESSAGE ID:" + Long.toString(msgID));
            deleteItem(msgID);
        }
        }
   public void  deleteItem (long id){

            db.delete(ChatDatabaseHelper.TABLE_NAME,ChatDatabaseHelper.KEY_MESSAGE + "=" + id,null);
            int position = chatIDs.indexOf(id);
            saveChat.remove(edittt.getText().toString());
            chatIDs.remove(position);

            final ChatAdapter adap = (ChatAdapter) chatView.getAdapter();
            adap.notifyDataSetChanged();

   }
    }