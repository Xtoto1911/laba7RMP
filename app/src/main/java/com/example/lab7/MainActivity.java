package com.example.lab7;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class MainActivity extends  AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lView = (ListView) findViewById(R.id.listView);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 2);
                return;
            }
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        ArrayList<String> listveiw1 = new ArrayList<String>();
        
        for(BluetoothDevice bt: pairedDevices)
            listveiw1.add(String.valueOf(bt.getName()) + "\n" + String.valueOf(bt.getAddress()));

        lView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listveiw1));

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?>parent, View view,int position, long id){
                Intent i=new Intent(MainActivity.this,SecondActivity.class);
                String s = (String) ((TextView)view).getText();
                i.putExtra("adress",s);
                startActivity(i);
            }
        });

    }




   /* @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                    {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 2);
                        return;
                    }
                }
                mDeviceList.add(device.getName() + "\n" + device.getAddress());
                Log.i("BT", device.getName() + "\n" + device.getAddress());
                listView.setAdapter(new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, mDeviceList));
            }
        }
    };*/
}