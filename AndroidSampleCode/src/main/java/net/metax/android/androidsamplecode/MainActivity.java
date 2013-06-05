package net.metax.android.androidsamplecode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import net.metax.android.androidsamplecode.networkstatus.NetworkStatusActivity;

public class MainActivity extends Activity {
    private Button buttonNetworkStatus;
    private Button.OnClickListener buttonNetworkStatusListener;

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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
