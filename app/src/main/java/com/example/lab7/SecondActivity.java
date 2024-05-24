package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class SecondActivity extends AppCompatActivity {

    private TextView tv;
    BluetoothSocket clientSocket;
    OutputStream outStream;
    private SeekBar mRedSeekBar, mGreenSeekBar, mBlueSeekBar, mXSeekBar, mYSeekBar;
    private TextView tvX;
    private TextView tvY;
    private TextView tvR;
    private TextView tvG;
    private TextView tvB;
    int CordX, CordY, ColR, ColG, ColB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        tv = (TextView) findViewById(R.id.ConnectName);
        tvX = (TextView) findViewById(R.id.textViewx);
        tvX.setText("0");
        tvY = (TextView) findViewById(R.id.textViewy);
        tvY.setText("0");
        tvR = (TextView) findViewById(R.id.textViewR);
        tvR.setText("0");
        tvG = (TextView) findViewById(R.id.textViewG);
        tvG.setText("0");
        tvB = (TextView) findViewById(R.id.textViewB);
        tvB.setText("0");
        String Data = getIntent().getStringExtra("adress").split("\n")[1];
        tv.setText(Data);

        mRedSeekBar = (SeekBar) findViewById(R.id.seeekbyR);
        mGreenSeekBar = (SeekBar) findViewById(R.id.seeekbyG);
        mBlueSeekBar = (SeekBar) findViewById(R.id.seeekbyB);
        mXSeekBar = (SeekBar) findViewById(R.id.seeekbyx);
        mYSeekBar = (SeekBar) findViewById(R.id.seeekbyy);

        mRedSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        mGreenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        mBlueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        mXSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        mYSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();

        try {
            BluetoothDevice Device = bluetooth.getRemoteDevice(String.valueOf(Data));
            Method m = Device.getClass().getMethod(
                    "createRfcommSocket", new Class[]{int.class});
            clientSocket = (BluetoothSocket) m.invoke(Device, 1);
            if (ContextCompat.checkSelfPermission(SecondActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                {
                    ActivityCompat.requestPermissions(SecondActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 2);
                    return;
                }
            }
            clientSocket.connect();
            Toast toast = Toast.makeText(this, "Connected", Toast.LENGTH_LONG);
            toast.show();

        }catch (Exception ex){
            Log.d("OnCreate",ex.getMessage());
            Toast toast = Toast.makeText(this, "Введна ошибка "+ ex.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
    }
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if(seekBar.getId()==R.id.seeekbyx) {
                tvX.setText(String.valueOf(seekBar.getProgress()));
                CordX = seekBar.getProgress();
            }
            if(seekBar.getId()==R.id.seeekbyy){
                tvY.setText(String.valueOf(seekBar.getProgress()));
                CordY = seekBar.getProgress();
            }
            if(seekBar.getId()==R.id.seeekbyR){
                tvR.setText(String.valueOf(seekBar.getProgress()));
                ColR=seekBar.getProgress();
            }
            if(seekBar.getId()==R.id.seeekbyG){
                tvG.setText(String.valueOf(seekBar.getProgress()));
                ColG=seekBar.getProgress();
            }
            if(seekBar.getId()==R.id.seeekbyB){
                tvB.setText(String.valueOf(seekBar.getProgress()));
                ColB=seekBar.getProgress();
            }
        }
    };

    public void Send(View view){
        try{
            outStream = clientSocket.getOutputStream();
            String message = "$" + CordX + " " + CordY +" "+ ColR +" "+ ColG +" "+ ColB + ";";
            outStream.write(message.getBytes());
            Toast toast = Toast.makeText(this, "Succses " + message, Toast.LENGTH_LONG);
            toast.show();
        }
        catch (Exception ex){
            Log.d("Sending",ex.getMessage());
            Toast toast = Toast.makeText(this, "Введна ошибка " + ex.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
    }
    public void Reconnect(View view) {
        Intent intent=new Intent(SecondActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
