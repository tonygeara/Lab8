package com.example.tony.androidlabs;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class MessageFragment extends Fragment
{
    protected TextView viewMessage;
    protected TextView viewID;
    protected Button deleteButton;
    protected ListView list;
    private boolean tablet;
    protected static final String NAME = "MESSAGEFRAGMENT";

    protected Chatwindow chatWindow;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.activity_message_fragment, container, false);
        final Bundle args = getArguments();

        viewMessage = (TextView) view.findViewById(R.id.message_detail_message);
        viewID = (TextView) view.findViewById(R.id.message_detail_id);
        deleteButton = (Button) view.findViewById(R.id.message_detail_btn_delete);

        list = getActivity().findViewById(R.id.ChatView);
        final Bundle bundle = getArguments();
        final int currentid = bundle.getInt("id");
        tablet = bundle.getBoolean("FLAG");
        final String SHOWMESSAGE = bundle.getString("MESSAGE:");

//        String messageText =  getString(R.string.message_detail_msg) + " " + Long.toString((Long) args.get("MESSAGE")) ;
//        String messageText = ChatDatabaseHelper.KEY_MESSAGE;
//        final String idText = getString(R.string.message_detail_id) + " " + Long.toString(args.getLong("id"));

        if (bundle!= null){
        viewMessage.setText(SHOWMESSAGE);
        viewID.setText(String.valueOf(currentid));}

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!tablet )
                {
                    Intent msgDetailsIntent = new Intent(getActivity(), MessageDetail.class);
                    msgDetailsIntent.putExtra("msgID", viewMessage.getText().toString());


                    getActivity().setResult(RESULT_OK, msgDetailsIntent);
                    getActivity().finish();
                }
                else
                {
//                    chatWindow.deleteItem(getLong( ChatDatabaseHelper.KEY_ID));
//                    chatWindow.deleteItem(ChatDatabaseHelper.KEY_MESSAGE);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    MessageFragment messagefrag = (MessageFragment) getFragmentManager().findFragmentByTag("TABLETFRAGMENT");

                    EditText edit = getActivity().findViewById(R.id.editText);
                    Chatwindow.saveChat.remove(SHOWMESSAGE);

                    ft.remove(messagefrag);
                    ft.commit();
                    Log.i(NAME,"DELETEDDDDD");
                }
            }
        });

        return view;
    }
}