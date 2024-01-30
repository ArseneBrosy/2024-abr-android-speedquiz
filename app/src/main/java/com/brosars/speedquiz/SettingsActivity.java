package com.brosars.speedquiz;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.brosars.speedquiz.controllers.Question;
import com.brosars.speedquiz.models.SpeedQuizSQLite;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    private MaterialButton okButton;
    private Slider questionSpeedSlider;
    private int maxQuestionCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        okButton = findViewById(R.id.ok_button);
        questionSpeedSlider = findViewById(R.id.settings_question_speed);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // get settings for questions speed
        SharedPreferences sharedPref = getSharedPreferences("IDValue", 0);
        int questionSpeed = sharedPref.getInt("settings_questions_speed", 5000);
        questionSpeedSlider.setValue(questionSpeed / 1000F);
        System.out.println("preferences");
        System.out.println(questionSpeed);

        // get max question count
        SpeedQuizSQLite helper = new SpeedQuizSQLite(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(idQuiz) FROM quiz", null);
        cursor.moveToFirst();
        maxQuestionCount = cursor.getInt(0);
        cursor.close();
        db.close();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("IDValue", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("settings_questions_speed", (int)(questionSpeedSlider.getValue() * 1000));
                editor.apply();

                finish();
            }
        });
    }
}
