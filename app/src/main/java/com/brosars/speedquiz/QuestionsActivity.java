package com.brosars.speedquiz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.brosars.speedquiz.models.SpeedQuizSQLite;
import com.google.android.material.button.MaterialButton;

public class QuestionsActivity extends AppCompatActivity {

    private MaterialButton cancelButton;
    private MaterialButton addButton;
    private EditText labelEditText;
    private CheckBox isTrueCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        // get the components
        cancelButton = findViewById(R.id.cancel_button);
        addButton = findViewById(R.id.add_button);
        labelEditText = findViewById(R.id.question_label);
        isTrueCheckbox = findViewById(R.id.checkbox_is_true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // buttons actions
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add the questions into the database and close the activity
                addQuestion(labelEditText.getText().toString(), isTrueCheckbox.isChecked());
                finish();
            }
        });

        labelEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // enable the add button if the text isn't empty
                String text = labelEditText.getText().toString();
                addButton.setEnabled(text.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Add a question in the database
     * @param label the label of the question
     * @param isTrue is the question true
     */
    private void addQuestion(String label, boolean isTrue) {
        String query = "INSERT INTO quiz VALUES (null, \"" + label + "\", " + (isTrue ? "1" : "0") + ");";
        SpeedQuizSQLite helper = new SpeedQuizSQLite(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL(query);
        db.close();
    }
}
