package uek335.project.stepcounter.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import uek335.project.stepcounter.R;
import uek335.project.stepcounter.StepEntry;
import uek335.project.stepcounter.service.DBService;
import uek335.project.stepcounter.service.DistanceCalculationService;

public class LeaderBoardActivity extends AppCompatActivity {

    private View buttonHome;
    private TextView statsFirst;
    private TextView statsSecond;
    private TextView statsThird;
    private DBService dbService;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        buttonHome = findViewById(R.id.homeButton);
        statsFirst = findViewById(R.id.leaderValues1);
        statsSecond = findViewById(R.id.leaderValues2);
        statsThird = findViewById(R.id.leaderValues3);
        dbService = new DBService(this);
        sharedPreferences = getSharedPreferences("MyPrefsStepLength", MODE_PRIVATE);

        List<StepEntry> top3Entries = dbService.getTop3StepCounts();

        //display the top3 entries if existing
        if (top3Entries.size() >= 1) {
            StepEntry entry1 = top3Entries.get(0);
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsStepLength", Context.MODE_PRIVATE);
            String lengthValue1 = sharedPreferences.getString("length", "0");
            String distance1 = DistanceCalculationService.calculateDistance(lengthValue1, entry1.getCount());
            statsFirst.setText(entry1.getCount() + " Schritte " + entry1.getDate() + "  " + distance1 + "km");
        }

        if (top3Entries.size() >= 2) {
            StepEntry entry2 = top3Entries.get(1);
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsStepLength", Context.MODE_PRIVATE);
            String lengthValue2 = sharedPreferences.getString("length", "0");
            String distance2 = DistanceCalculationService.calculateDistance(lengthValue2, entry2.getCount());
            statsSecond.setText(entry2.getCount() + " Schritte " + entry2.getDate() + "  " + distance2 + "km");
        }

        if (top3Entries.size() >= 3) {
            StepEntry entry3 = top3Entries.get(2);
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsStepLength", Context.MODE_PRIVATE);
            String lengthValue3 = sharedPreferences.getString("length", "0");
            String distance3 = DistanceCalculationService.calculateDistance(lengthValue3, entry3.getCount());
            statsThird.setText(entry3.getCount() + " Schritte " + entry3.getDate() + "  " + distance3 + "km");
        }
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeaderBoardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}