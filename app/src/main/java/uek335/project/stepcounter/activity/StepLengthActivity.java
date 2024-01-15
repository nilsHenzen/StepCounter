package uek335.project.stepcounter.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uek335.project.stepcounter.R;

public class StepLengthActivity extends AppCompatActivity {

    private View buttonSubmit;
    private TextView valueLength;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_length);

        valueLength = findViewById(R.id.valueLenght);
        buttonSubmit = findViewById(R.id.submitButton);
        sharedPreferences = getSharedPreferences("MyPrefsStepLength", MODE_PRIVATE);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stepLength = valueLength.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("length", stepLength);
                editor.apply();

                Intent intent = new Intent(StepLengthActivity.this, LeaderBoardActivity.class);
                startActivity(intent);
            }
        });

    }
}