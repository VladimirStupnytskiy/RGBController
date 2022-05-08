package com.example.v2vega109;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;
import static android.graphics.Color.rgb;


public class MainActivity extends AppCompatActivity {
    View gradientView;
    Button breathMode;
    Button sequenceMode;
    CircleButton arrb11, arrb12, arrb13, arrb14, arrb15, arrb16, arrb17;
    CircleButton arrb21, arrb22, arrb23, arrb24, arrb25, arrb26, arrb27;
    int breathState;
    int seqState;
    int modestate;
    private static final int REQUEST_ENABLE_BT = 3;
    private final String DEVICE_ADDRESS="98:D3:71:F9:77:36";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private ColorPicker colorPickerView;

    public boolean BTinit(){
        boolean found = false;
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(),"Device doesnt Support Bluetooth",Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled()){
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if(bondedDevices.isEmpty() && bluetoothAdapter.isEnabled()){
            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();
        }
        else {
            for (BluetoothDevice iterator : bondedDevices) {
                if(iterator.getAddress().equals(DEVICE_ADDRESS)) {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    public boolean BTconnect(){
        boolean connected = true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
        if(connected){
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connected;
    }

    public void BTdisconnect(){
        bluetoothAdapter.disable();
        try {
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public BluetoothAdapter getBtAdapter(){
        return bluetoothAdapter;
    }

    public BluetoothSocket getBtSocket(){

        return socket;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!getBtAdapter().isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BTdisconnect();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        breathState=0;
        seqState=0;
        modestate=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gradientView = findViewById(R.id.viewGradient);
        breathMode = findViewById(R.id.breathMode);
        sequenceMode = findViewById(R.id.sequence);
        gradientView = findViewById(R.id.viewGradient);
        colorPickerView = findViewById(R.id.colorPicker);

        arrb11 = findViewById(R.id.arrb11);
        arrb12 = findViewById(R.id.arrb12);
        arrb13 = findViewById(R.id.arrb13);
        arrb14 = findViewById(R.id.arrb14);
        arrb15 = findViewById(R.id.arrb15);
        arrb16 = findViewById(R.id.arrb16);
        arrb17 = findViewById(R.id.arrb17);
        arrb21 = findViewById(R.id.arrb21);
        arrb22 = findViewById(R.id.arrb22);
        arrb23 = findViewById(R.id.arrb23);
        arrb24 = findViewById(R.id.arrb24);
        arrb25 = findViewById(R.id.arrb25);
        arrb26 = findViewById(R.id.arrb26);
        arrb27 = findViewById(R.id.arrb27);

        arrb11.setCircleColor(rgb(255,255,255));
        arrb12.setCircleColor(rgb(0,0,255));
        arrb13.setCircleColor(rgb(0,125,0));
        arrb14.setCircleColor(rgb(209,7,106));
        arrb15.setCircleColor(rgb(162,7,186));
        arrb16.setCircleColor(rgb(255,255,0));
        arrb17.setCircleColor(rgb(188,162,161));
        arrb21.setCircleColor(rgb(0,0,0));
        arrb22.setCircleColor(rgb(55,151,255));
        arrb23.setCircleColor(rgb(0,255,0));
        arrb24.setCircleColor(rgb(238,73,87));
        arrb25.setCircleColor(rgb(255,0,0));
        arrb26.setCircleColor(rgb(255,128,0));
        arrb27.setCircleColor(rgb(139,69,19));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int color =0;
               CircleButton btn = (CircleButton) findViewById(view.getId());
                color =btn.getCircleColor();
                int red = red(color);
                int green = green(color);
                int blue = blue(color);
                sendData(red, green, blue);
                setGradientToView (red,green,blue);
            }
        };

        arrb11.setOnClickListener(listener);
        arrb12.setOnClickListener(listener);
        arrb13.setOnClickListener(listener);
        arrb14.setOnClickListener(listener);
        arrb15.setOnClickListener(listener);
        arrb16.setOnClickListener(listener);
        arrb17.setOnClickListener(listener);
        arrb21.setOnClickListener(listener);
        arrb22.setOnClickListener(listener);
        arrb23.setOnClickListener(listener);
        arrb24.setOnClickListener(listener);
        arrb25.setOnClickListener(listener);
        arrb26.setOnClickListener(listener);
        arrb27.setOnClickListener(listener);

        //try to connect BT during app launching
        if (bluetoothAdapter.isEnabled()) {

            BTinit();
            BTconnect();

            if(socket.isConnected()){

                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
            }
        }
        else if(!bluetoothAdapter.isEnabled()){

            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        //end of connect BT during app launching


        colorPickerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == (MotionEvent.ACTION_UP)) {
                    int Color = colorPickerView.getColor();
                    int red = red(Color);
                    int green = green(Color);
                    int blue = blue(Color);
                    sendData(red, green, blue);
                    setGradientToView (red,green,blue);
                }

                return false;
            }
        });


        breathMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sendData(1);
            }
        });

        sequenceMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(2);
            }
        });

    }

    public void sendData (int xRed, int xGreen, int xBlue){
        if (xRed == 255 && xGreen == 255 && xBlue == 255 ) xBlue = xBlue-130;
        if (xRed == 188 && xGreen == 162 && xBlue == 161 ) xBlue = xBlue-130;

        BluetoothSocket btSocket = getBtSocket();
        if(btSocket != null) {
            try {
                OutputStream outputStream = btSocket.getOutputStream();
                if(outputStream != null){
                    outputStream.write(("R" + xRed + "," + xGreen + "," + xBlue + ";").getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_SHORT).show();
        }
    }

    public void sendData (int mode){
        BluetoothSocket btSocket = getBtSocket();
        if (mode == 1 && breathState == 0){
            breathState = 1;
            seqState = 0;
            modestate = breathState;
            Toast.makeText(getApplicationContext(),"breath mode launched",Toast.LENGTH_SHORT).show();
        }
        else if(mode == 1 && breathState == 1){
            breathState = 0;
            modestate = breathState;
            Toast.makeText(getApplicationContext(),"breath mode stopped",Toast.LENGTH_SHORT).show();
        }

        else if (mode == 2 && seqState == 0){
            seqState = 1;
            breathState = 0;
            modestate = seqState;
            Toast.makeText(getApplicationContext(),"sequence mode launched",Toast.LENGTH_SHORT).show();
        }
        else if(mode == 2 && seqState == 1){
            seqState = 0;
            modestate = seqState;
            Toast.makeText(getApplicationContext(),"sequence mode stopped",Toast.LENGTH_SHORT).show();
        }

        if(btSocket != null) {
            try {
                OutputStream outputStream = btSocket.getOutputStream();
                if(outputStream != null){
                    outputStream.write(("M" + mode + "" + modestate + ";").getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Not Connected",Toast.LENGTH_SHORT).show();
        }
    }

    public void setGradientToView(int R, int G, int B) {
        int[] colors = new int[2];
        colors[0] = rgb(R,G,B);
        colors[1] = Color.BLACK;

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, colors);
        gd.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gd.setGradientRadius(780f);
        gd.setGradientCenter(0.5f,0.99f);
        gradientView.setBackground(gd);
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

        if (id == R.id.BTconnect && bluetoothAdapter.isEnabled()) {

            BTinit();
            BTconnect();

            if(socket.isConnected()){
                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
                sendData(0,255,0);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {}

                sendData(0,0,0);

            }
        }
        else if(id == R.id.BTconnect && !bluetoothAdapter.isEnabled()){

            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }


        if(id == R.id.BTdiconnect && bluetoothAdapter.isEnabled()){

            BTdisconnect();

            if(!socket.isConnected()){

                Toast.makeText(getApplicationContext(), "Disconnected", Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
