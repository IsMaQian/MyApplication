package com.example.mq.nine_patch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button send;
    private EditText inputText;
    private RecyclerView msgRecycleView;
    private MsgAdapter adapter;
    private List<Msg> msgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMsgs();
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecycleView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecycleView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecycleView.setAdapter(adapter);
        send.setOnClickListener(new SendButtonListener());
    }

    private class SendButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String content = inputText.getText().toString();
            if (!"".equals(content)) {
                Msg msg = new Msg(content, Msg.TYPE_SENT);
                msgList.add(msg);
                adapter.notifyItemInserted(msgList.size() - 1);
                msgRecycleView.scrollToPosition(msgList.size() - 1);
                inputText.setText("");
            }
        }
    }
    private void initMsgs() {
            Msg msg1 = new Msg("Hello guy", Msg.TYPE_RECEIVE);
            msgList.add(msg1);
            Msg msg2 = new Msg("Hello,Who is that?", Msg.TYPE_SENT);
            msgList.add(msg2);
            Msg msg3 = new Msg("This is Tom.Nice talking to you", Msg.TYPE_RECEIVE);
            msgList.add(msg3);

    }


}
