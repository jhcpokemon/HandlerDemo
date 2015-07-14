package io.github.jhcpokemon.handlerdemo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static TextView textView1,textView2;
    private Button button1,button2;
    private Handler handler1,handler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = (TextView)findViewById(R.id.text_view1);
        textView2 = (TextView)findViewById(R.id.text_view2);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        handler1 = new MyHandler();
        button1.setOnClickListener(new ButtonListener());
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = handler2.obtainMessage();
                msg.obj = "From MainThread!";
                handler2.sendMessage(msg);
            }
        });
        MyThread mt = new MyThread();
        mt.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Message msg = handler1.obtainMessage();
            msg.what = 0;
            handler1.sendMessage(msg);
        }
    }

    static class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            int result = msg.what;
            textView1.setText("result" + result);
        }
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            Looper.prepare();
            handler2 = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Log.i("fromworkthread",msg.obj.toString());
                }
            };
            Looper.loop();
        }
    }
}
