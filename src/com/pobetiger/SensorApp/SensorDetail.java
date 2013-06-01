package com.pobetiger.SensorApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.hardware.*;


/**
 * Created with IntelliJ IDEA.
 * User: pobetiger
 * Date: 5/18/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class SensorDetail extends Activity implements SensorEventListener
{

    private int _nSensorDelay_usec;
    private SensorManager _SensorManager;
    private Sensor _Sensor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_detail);

        _SensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Intent i = getIntent();
        //String sMsg = i.getStringExtra("sensor");
        int nType = i.getIntExtra("sensor", -1);
        int nDelaySeconds = i.getIntExtra("sensor_delay_sec", 1);

        _nSensorDelay_usec = nDelaySeconds * 1000 * 1000; // convert to useconds
        _Sensor = _SensorManager.getDefaultSensor(nType);

        String sSensorStatus = "Sensor Details:\n";

        sSensorStatus += "Name: " + _Sensor.getName() + "\n";
        sSensorStatus += "Vendor: " + _Sensor.getVendor() + "\n";
        sSensorStatus += "Max Range: " + _Sensor.getMaximumRange() + "\n";
        sSensorStatus += "Resolution: " + _Sensor.getResolution() + "\n";

        ((TextView) findViewById(R.id.txtMessage)).setText(sSensorStatus);

        _SensorManager.registerListener(this, _Sensor, _nSensorDelay_usec);

    }

    public void onClick_close(View v)
    {
        finish();
    }
    public void onClick_SetDelay(View v)
    {
        String sDelay = ((EditText) findViewById(R.id.txtDelay)).getText().toString();
        Float fDelay = (Float.parseFloat(sDelay));


        if (fDelay <= 0)
        {
            fDelay = 1.0f;
        }

        float f = fDelay.floatValue() * 1000000f;
        _nSensorDelay_usec = (int)Math.round(f);

        // register with new delay settings
        _SensorManager.unregisterListener(this);
        _SensorManager.registerListener(this, _Sensor, _nSensorDelay_usec);
    }

    protected void onResume() {
        super.onResume();
        _SensorManager.registerListener(this, _Sensor, _nSensorDelay_usec);
    }

    protected void onPause() {
        super.onPause();
        _SensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    public void onSensorChanged(SensorEvent event)
    {
        String sMsg = event.timestamp + " : ";

        for (float val : event.values)
        {
            sMsg += val + ",";
        }
        sMsg += "\n";

        ((TextView) findViewById(R.id.txtMessage)).append(sMsg);

    }


}