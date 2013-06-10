package net.metax.android.androidsamplecode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import net.metax.android.androidsamplecode.nifstatus.NifStatusActivity;
import net.metax.android.androidsamplecode.networkstatus.NetworkStatusActivity;
import net.metax.android.androidsamplecode.wifiserver.WifiServerActivity;

public class MainActivity extends Activity {
    private Button buttonNetworkStatus;
    private Button.OnClickListener buttonNetworkStatusListener;
    private Button buttonIpStatus;
    private Button.OnClickListener buttonIpStatusListener;
    private Button buttonWifiServer;
    private Button.OnClickListener buttonWifiServerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeApp();
    }

    private void InitializeApp() {

        buttonNetworkStatus = (Button) findViewById(R.id.buttonNetworkStatus);
        buttonNetworkStatusListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NetworkStatusActivity.class);
                startActivity(intent);
            }
        };
        buttonNetworkStatus.setOnClickListener(buttonNetworkStatusListener);

        buttonIpStatus = (Button) findViewById(R.id.buttonIpStatus);
        buttonIpStatusListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NifStatusActivity.class);
                startActivity(intent);
            }
        };
        buttonIpStatus.setOnClickListener(buttonIpStatusListener);

        buttonWifiServer = (Button) findViewById(R.id.buttonWifiServer);
        buttonWifiServerListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WifiServerActivity.class);
                startActivity(intent);
            }
        };
        buttonWifiServer.setOnClickListener(buttonWifiServerListener);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
