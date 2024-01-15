package uek335.project.stepcounter.service;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uek335.project.stepcounter.SQLiteHelper;
import uek335.project.stepcounter.StepEntry;

public class DBService {

    private final SQLiteHelper dbHelper;

    public DBService(Context context) {
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

    public List<StepEntry> getTop3StepCounts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<StepEntry> top3Entries = new ArrayList<>();

        Cursor cursor = db.query(
                SQLiteHelper.TABLE_NAME,
                new String[]{SQLiteHelper.COLUMN_STEP_COUNT, SQLiteHelper.COLUMN_DATE},
                null,
                null,
                null,
                null,
                SQLiteHelper.COLUMN_STEP_COUNT + " DESC",
                "3"
        );

        while (cursor.moveToNext()) {
            @SuppressLint("Range") int count = cursor.getInt(cursor.getColumnIndex(SQLiteHelper.COLUMN_STEP_COUNT));
            @SuppressLint("Range") String date = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_DATE));
            top3Entries.add(new StepEntry(count, date));
        }

        cursor.close();
        db.close();

        return top3Entries;
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
}

