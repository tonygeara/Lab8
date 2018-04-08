package com.example.tony.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
public class LoginActivity extends Activity {
    protected static final String NAME = "LoginActivity";
//    public static final String LOGIN_ACTIVITY_PREFS = "login_Activity";
//    public static final String DEFAULT_EMAIL_HOLDER = "Default Email";
      EditText email;
//    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i(NAME, "In onCreate()");

        Button button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPreferences preferences = getSharedPreferences("App_File", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    email = (EditText) findViewById(R.id.editText2);
                    editor.putString("Default E-mail", email.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                    startActivity(intent);
                } catch (NullPointerException npe) {

                }
            }
        });
    }

    protected void onResume() {
        super.onResume();
        Log.i(NAME, "In onResume()");

    }
    protected void onStart() {
        super.onStart();
        Log.i(NAME, "In onStart()");
    }


    protected void onPause() {
        super.onPause();
        Log.i(NAME, "In onPause()");
    }
    protected void onStop() {
        super.onStop();
        Log.i(NAME, "In onStop()");
    }
    protected void onDestroy() {
        super.onDestroy();
        Log.i(NAME, "In onDestroy()");
    }
}
