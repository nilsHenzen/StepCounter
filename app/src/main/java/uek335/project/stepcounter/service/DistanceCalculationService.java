package uek335.project.stepcounter.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;

import java.text.DecimalFormat;

public class DistanceCalculationService extends Service {

    private final IBinder binder = new LocalBinder();
    private static SharedPreferences sharedPreferences;

    public class LocalBinder extends Binder {
        DistanceCalculationService getService() {
            return DistanceCalculationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        sharedPreferences = getSharedPreferences("MyPrefsStepLength", MODE_PRIVATE);
        return binder;
    }

    public static String calculateDistance(Context context, int countValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefsStepLength", Context.MODE_PRIVATE);
        String lengthValue = sharedPreferences.getString("length", "0");
        float length = Float.parseFloat(lengthValue);

        float count = countValue;
        float distance = (length / 100 * count) / 1000;

        DecimalFormat decimalFormat = new DecimalFormat("#.###");
        return decimalFormat.format(distance);    }
}
