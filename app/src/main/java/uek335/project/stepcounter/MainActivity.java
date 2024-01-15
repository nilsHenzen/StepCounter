package uek335.project.stepcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView stepCountView;
    private TextView currentDateView;
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private int stepCount = 0;
    private boolean isMoving = false;
    private SQLiteHelper dbHelper;
    private StepCountDBService stepCountDBService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button leaderboardButton = findViewById(R.id.leaderboardButton);
        stepCountView = findViewById(R.id.dailySteps);
        currentDateView = findViewById(R.id.currrentDate);
        dbHelper = new SQLiteHelper(this);
        stepCountDBService = new StepCountDBService(this);
        stepCount = stepCountDBService.getStepCount();


        //onClick redirect to other View
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StepLengthActivity.class);
                startActivity(intent);
            }
        });

        //implement Sensor
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
                    saveStepCountToDatabase();
                }
                handler.postDelayed(this, 500);
            }
        }, 500);

        setCurrentDate();
    }

    //save count of steps in db
    private void saveStepCountToDatabase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_DATE, getCurrentDate());
        values.put(SQLiteHelper.COLUMN_STEP_COUNT, stepCount);

        long result = db.insertWithOnConflict(SQLiteHelper.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void setCurrentDate() {
        String currentDate = getCurrentDate();

        if (currentDateView != null) {
            currentDateView.setText("" + currentDate);
            updateDBStepCountView();
        }
    }

    //update the text for the current Date
    private void updateDBStepCountView() {
        if (stepCountView != null) {
            int dbStepCount = stepCountDBService.getStepCount();
            stepCountView.setText("" + dbStepCount);
        }
    }


    //update the display of steps
    private void updateStepCountView() {
        if (stepCountView != null) {
            stepCountView.setText("" + stepCount);
        }
    }

    //check if a step is made
    //uses accelerometer sensor and counts as step if it is moving fast
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
