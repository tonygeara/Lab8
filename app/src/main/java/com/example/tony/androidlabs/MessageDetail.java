package com.example.tony.androidlabs;

/**
 * Created by Tony on 2018-04-05.
 */

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MessageDetail extends AppCompatActivity
{
//    protected FrameLayout fl;
    protected MessageFragment messageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        fl = new FrameLayout(this);
//        fl.setId(R.id.fragment_message_layout);
//        setContentView(fl);
        setContentView(R.layout.activity_message_detail);

        FragmentManager fmanager = getFragmentManager();

        messageFragment= new MessageFragment();

        messageFragment.setArguments(getIntent().getExtras());

        FragmentTransaction ft = fmanager.beginTransaction();
        ft.add(R.id.framecontainer, messageFragment);
        ft.commit();
    }
}