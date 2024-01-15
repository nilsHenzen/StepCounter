package uek335.project.stepcounter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StepCountDBService {

    private final SQLiteHelper dbHelper;

    public StepCountDBService(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    @SuppressLint("Range")
    public int getStepCount() {
        String currentDate = getCurrentDate();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int count = 0;

        Cursor cursor = db.query(
                SQLiteHelper.TABLE_NAME,
                new String[]{SQLiteHelper.COLUMN_STEP_COUNT},
                SQLiteHelper.COLUMN_DATE + " = ?",
                new String[]{currentDate},
                null,
                null,
                SQLiteHelper.COLUMN_DATE + " DESC",
                "1"
        );

        if (cursor.moveToFirst()) {
            count = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_STEP_COUNT));
        }

        cursor.close();
        db.close();

        return count;
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
}

