package net.metax.android.androidsamplecode.wifiserver;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
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
public class WifiServerActivity extends Activity implements Runnable {

    private ServerSocket mServer;
    private Socket mSocket;
    volatile Thread runner = null;
    Handler mHandler = new Handler();
    int port = 8080;
    TextView textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_server);

        InitializeActivity();

    }

    private void InitializeActivity() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int address = wifiInfo.getIpAddress();
        String ipAddressStr = ((address >> 0) & 0xFF) + "."
                + ((address >> 8) & 0xFF) + "." + ((address >> 16) & 0xFF)
                + "." + ((address >> 24) & 0xFF);
        TextView tv = (TextView) findViewById(R.id.textIpAddress);
        tv.setText(ipAddressStr);
        textMessage = (TextView) findViewById(R.id.textMessage);

        if(runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }

    @Override
    public void run() {
        try {
            mServer = new ServerSocket(port);
            mSocket = mServer.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            String message;
            final StringBuilder messageBuilder = new StringBuilder();
            while ((message = in.readLine()) != null) {
                messageBuilder.append(message);
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    textMessage.setText(textMessage.getText() + messageBuilder.toString());

                }
            });
            runner.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
