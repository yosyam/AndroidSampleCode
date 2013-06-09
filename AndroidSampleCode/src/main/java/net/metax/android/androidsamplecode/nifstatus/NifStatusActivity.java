package net.metax.android.androidsamplecode.nifstatus;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.metax.android.androidsamplecode.R;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * Created by yoshi on 13/06/08.
 */
public class NifStatusActivity extends Activity {

    final String TAG = "NifStatusActivity";
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nif_status);

        InitializeActivity();

    }

    private void reloadNetworkInterfaceList() {

        mListView = (ListView) findViewById(R.id.lvInterfaceList);
        Enumeration<NetworkInterface> netIFs;
        IpAddressListAdapter adapter;
        List<NetworkInterface> list = new ArrayList<NetworkInterface>();
        try {

            netIFs = NetworkInterface.getNetworkInterfaces();
            while( netIFs.hasMoreElements() ) {
                list.add(netIFs.nextElement());
            }

        } catch (SocketException se) {
            Log.e(TAG, se.toString());
        }

        adapter = new IpAddressListAdapter(this, list);
        mListView.setAdapter(adapter);

    }

    private class IpAddressListAdapter extends ArrayAdapter<NetworkInterface> {
        private LayoutInflater mInflater;

        public IpAddressListAdapter(Context context, List<NetworkInterface> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_row_2_lines, null);
            }
            final NetworkInterface item = this.getItem(position);
            if (item != null) {
                ((TextView) convertView.findViewById(R.id.text1)).setText(item.getDisplayName());
                ((TextView) convertView.findViewById(R.id.text2)).setText(item.toString());
            }
            return convertView;
        }

    }

    private void InitializeActivity() {
        mListView = (ListView) findViewById(R.id.lvInterfaceList);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        reloadNetworkInterfaceList();
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
