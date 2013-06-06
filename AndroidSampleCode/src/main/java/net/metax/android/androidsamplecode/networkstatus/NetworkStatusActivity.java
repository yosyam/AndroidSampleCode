package net.metax.android.androidsamplecode.networkstatus;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import net.metax.android.androidsamplecode.R;

/**
 *
 * Created by yoshi on 13/06/05.
 */
public class NetworkStatusActivity extends Activity {
    ConnectReceiver mConnectReceiver = null;
    TextView mTextView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_status);

        InitializeActivity();

    }

    private void InitializeActivity() {
        mTextView = (TextView) findViewById(R.id.textView);
        mConnectReceiver = new ConnectReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getApplicationContext().registerReceiver(mConnectReceiver, filter);
        // ActionBarに「戻る」機能を追加
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    private class ConnectReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            boolean connected = (ni != null) && ni.isConnected();
            if (connected) {

                mTextView.setText(String.format(ni.toString()));
            } else {
                mTextView.setText(String.format("disconnected"));
            }
        }
    }

}
