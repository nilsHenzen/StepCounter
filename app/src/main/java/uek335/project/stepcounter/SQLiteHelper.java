package uek335.project.stepcounter;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "stepCounterDB";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "dailySteps";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STEP_COUNT = "stepCount";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_DATE + " TEXT PRIMARY KEY," +
                    COLUMN_STEP_COUNT + " INTEGER);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
