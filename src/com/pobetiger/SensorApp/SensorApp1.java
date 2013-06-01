package com.pobetiger.SensorApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.hardware.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.*;


import java.util.List;


public class SensorApp1 extends Activity
{

    private SensorManager mSensorManager;
    //private Sensor mAccelerometer;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void getDeviceSensorList(View view)
    {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        LinearLayout layout_box = (LinearLayout) findViewById(R.id.layout_box);

        if (layout_box.getChildCount() > 0)
        {
            layout_box.removeAllViews();
            // probably also need to unregister listeners
        }

        List<Sensor> mySensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : mySensors)
        {
            //final String sSensorName = s.getName();
            final int nSensorType = s.getType();
            Button newBtn = new Button(this);
            newBtn.setText(s.getName());

            newBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(SensorApp1.this, SensorDetail.class);
                    i.putExtra("sensor", nSensorType);
                    SensorApp1.this.startActivity(i);
                }
            });

            layout_box.addView(newBtn);
//            txtList.append(s.getName() + "\n");
        }
    }


}
