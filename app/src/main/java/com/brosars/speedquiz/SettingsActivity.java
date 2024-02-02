package com.brosars.speedquiz;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    EditText questionCountET;
    TextView errorText;
    private int maxQuestionCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        okButton = findViewById(R.id.ok_button);
        questionSpeedSlider = findViewById(R.id.settings_question_speed);
        questionCountET = findViewById(R.id.settings_question_count);
        errorText = findViewById(R.id.settings_error_question_count);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // get settings for questions speed
        SharedPreferences sharedPref = getSharedPreferences("IDValue", 0);
        int questionSpeed = sharedPref.getInt("settings_questions_speed", 5000);
        int questionCount = sharedPref.getInt("settings_question_count", 0);
        questionSpeedSlider.setValue(questionSpeed / 1000F);
        questionCountET.setText(String.valueOf(questionCount));

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
                editor.putInt("settings_question_count", Integer.parseInt(questionCountET.getText().toString()));
                editor.apply();

                finish();
            }
        });

        questionCountET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = questionCountET.getText().toString();
                if (text.isEmpty()) {
                    return;
                }
                boolean isTooMany = Integer.parseInt(text) > maxQuestionCount;
                boolean isNotEnough = Integer.parseInt(text) <= 0;
                boolean isCorrect = !isTooMany && !isNotEnough;
                if (isTooMany) {
                    errorText.setText(R.string.error_too_many_questions);
                }
                if (isNotEnough) {
                    errorText.setText(R.string.error_not_enough_question);
                }
                errorText.setVisibility(isCorrect ? View.INVISIBLE : View.VISIBLE);
                okButton.setEnabled(isCorrect);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
