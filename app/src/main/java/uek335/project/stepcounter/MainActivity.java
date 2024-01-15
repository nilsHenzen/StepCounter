package uek335.project.stepcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView stepCountView;
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private int stepCount = 0;
    private boolean isMoving = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button leaderboardButton = findViewById(R.id.leaderboardButton);
        stepCountView = findViewById(R.id.dailySteps);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StepLengthActivity.class);
                startActivity(intent);
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (stepSensor != null) {
                sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isMoving) {
                    stepCount++;
                    updateStepCountView();
                }
                handler.postDelayed(this, 500);
            }
        }, 500);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float acceleration = Math.abs(event.values[0] + event.values[1] + event.values[2]);

            if (acceleration > 11 || acceleration < -11) {
                isMoving = true;
            } else {
                isMoving = false;
            }
        }
    }

    private void updateStepCountView() {
        if (stepCountView != null) {
            stepCountView.setText("" + stepCount);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}