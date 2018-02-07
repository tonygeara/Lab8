package com.example.tony.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;


public class ListItemsActivity extends Activity {
    protected static final String NAME = "ListItemsActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton imageButton;
    private void dispatchTakePictureIntent() {
        Intent PicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (PicIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(PicIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(NAME, "In onCreate()");
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        Switch switch2 = (Switch) findViewById(R.id.switch2);

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    giveToast(getString(R.string.onSwchMsg), 1);
                    Log.i("SWITCH BUTTON", "Enabled");
                } else {
                    giveToast(getString(R.string.offSwchMsg), 0);
                    Log.i("SWITCH BUTTON", "Disabled");
                }

            }
        });

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                builder.setMessage(R.string.dialog_msessage)
                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent resultIntent = new Intent();
                                resultIntent.putExtra("Response", "Here is my response");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
        private void giveToast( String s , int code) {

            if (code == 1) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }


    protected void onStart() {
        super.onStart();
        Log.i(NAME, "In onStart()");
    }

    protected void onResume() {
        super.onResume();
        Log.i(NAME, "In onResume()");

    }


    protected void onStop() {
        super.onStop();
        Log.i(NAME, "In onStop()");
    }
    protected void onRestart() {
        super.onRestart();
        Log.i(NAME, "In onRestart()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(NAME, "In onPause()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(NAME, "In onDestroy()");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imBitmap);
        }
    }

}

        // String input = getintent().getStringextra(name :"Userinput")
// intent second =  .... to open the next activity
        // second.set...
        //

        //3rd activity
        // button = input
        //edittext= textpersonanam
        //button = go back
//        .java = setcontentview(R.idactivityth3
        // button back = back...
        // onactivityresult() if result code ==66  then result code = 66 ...
        // sharedpreferences prefs = getsharedpreferences(name"Usrinput",context.mode_private
        // sharedpreferecesp.editor = prefs.edit
        // int num = prefs.getint(key:"num",delValue:0
//        edit.putint("intum",numlaunches +1)
//        edit.commit


        // lab4
        // new activity - listviewactivity
        // change constraintlayout to linearlayout
        // Listview create it inside the xml listviewactivity
        // listview the list = (listview)findview ...(..thelist)

        //MynewAdapter adap = new My...
        // thelist.setAdapter()
        // private class MyNewAdapter extends BaseAdapter{
        // public int getcount() // inside the class mynew...
        // return 1; || or return 2;...
        // public view getview (int position, viewgroup vg){
        //
        // return null }
        // public long getitemid (int position){ return position }
        // public string getitem (int position) { return integer.tostring(position) }
        // create new layout table_cell_layout
        // inside the new llayout table_... xml
        // create new textview
        // create new edittext (android hint: "write here")
        // inside ListViewActivity.java
        // Layoutinflater inf... = getlayoutinflater
        // view newview = inflater.inflat(R.layout.table_cell_layout,vg, attach .. false)
        // TextView positiontext = (textview)newview.findviewbyid(R.id.number_numberplace
        // positiontext.settext(getitem(position));
        //edittext edit = (edititext)findviewid(R.id.text_edit
        //edit.setonkeylistener (new view.onkeylisterner ({ @overide public boolean on key
        // (view v , int keycode, keyevent ebent)
        // positiontext.settext(getitem(position)+" " + edit.gettext());
        //
        //
        //
        //
        // return newview;



