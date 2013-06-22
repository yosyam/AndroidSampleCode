package net.metax.android.androidsamplecode.tcpserver;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.metax.android.androidsamplecode.R;

import org.apache.http.conn.util.InetAddressUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 
 * Created by yoshi on 13/06/10.
 */
public class TcpServerActivity extends Activity {

    final String TAG = "TcpServerActivity";

    private ServerSocket mServer = null;
    private Socket mSocket = null;
    volatile Thread runner = null;
    private Handler mHandler = null;
    int port = 8080;
    volatile TextView textMessage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_server);
        mHandler = new Handler();
        InitializeActivity();

    }

    private void InitializeActivity() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

        String ipAddressStr = getLocalIPv4Address();
        if (ipAddressStr != null) {
            TextView tv = (TextView) findViewById(R.id.textIpAddress);
            tv.setText(ipAddressStr + ":" + Integer.toString(port));
        }

        textMessage = (TextView) findViewById(R.id.textMessage);
        if (runner == null) {
            runner = new Thread(new ServerThread());
            runner.start();
        }
    }

    private String getLocalIPv4Address() {
        try {
            for (Enumeration<NetworkInterface> networkInterfaceEnum = NetworkInterface.getNetworkInterfaces();
                    networkInterfaceEnum.hasMoreElements();) {
                NetworkInterface networkInterface = networkInterfaceEnum.nextElement();
                for (Enumeration<InetAddress> ipAddressEnum = networkInterface.getInetAddresses();
                     ipAddressEnum.hasMoreElements();) {
                    InetAddress inetAddress = ipAddressEnum.nextElement();
                    if (!inetAddress.isLoopbackAddress() &&
                            InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Log.e(TAG, e.toString());
        }
        return null;
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
