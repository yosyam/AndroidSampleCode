package net.metax.android.androidsamplecode.wifiserver;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.metax.android.androidsamplecode.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Created by yoshi on 13/06/10.
 */
public class WifiServerActivity extends Activity {

    final String TAG = "WifiServerActivity";

    private ServerSocket mServer = null;
    private Socket mSocket = null;
    volatile Thread runner = null;
    private Handler mHandler = null;
    int port = 8080;
    volatile TextView textMessage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_server);
        mHandler = new Handler();
        InitializeActivity();

    }

    private void InitializeActivity() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int address = wifiInfo.getIpAddress();
        String ipAddressStr = (address & 0xFF) + "."
                + ((address >> 8) & 0xFF) + "." + ((address >> 16) & 0xFF)
                + "." + ((address >> 24) & 0xFF);
        TextView tv = (TextView) findViewById(R.id.textIpAddress);
        tv.setText(ipAddressStr + ":" + Integer.toString(port));
        textMessage = (TextView) findViewById(R.id.textMessage);
        if (runner == null) {
            runner = new Thread(new ServerThread());
            runner.start();
        }
    }

    private class ServerThread implements Runnable {

        @Override
        public void run() {
            try {
                mServer = new ServerSocket(port);
                while (!Thread.interrupted()) {
                    mSocket = mServer.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    String message;

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            textMessage.setText(textMessage.getText() + "[" + mSocket.getInetAddress().toString() + "]\n");
                        }
                    });

                    while ((message = in.readLine()) != null) {
                        final StringBuilder messageBuilder = new StringBuilder();
                        messageBuilder.append(message + "\n");
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                textMessage.setText(textMessage.getText() + messageBuilder.toString());
                            }
                        });
                    }

                    mSocket.close();
                }
                Log.i(TAG, "Close Socket");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runner.interrupt();
        try {
            if (mSocket != null) {
                mSocket.close();
            }
            if (mServer != null) {
                mServer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        super.onMenuItemSelected(featureId, item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
