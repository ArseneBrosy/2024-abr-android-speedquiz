package com.brosars.speedquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private MaterialButton newPlayerButton;
    private MaterialButton startGameButton;
    private MaterialButton aboutOkButton;
    private TextInputLayout namePlayerOne;
    private TextInputLayout namePlayerTwo;
    private EditText editPlayerOneName;
    private EditText editPlayerTwoName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create the toolbar
        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        // get the components
        newPlayerButton = findViewById(R.id.button_new_player);
        startGameButton = findViewById(R.id.button_start_game);
        aboutOkButton = findViewById(R.id.about_ok_button);
        namePlayerOne = findViewById(R.id.player_one_name);
        namePlayerTwo = findViewById(R.id.player_two_name);
        editPlayerOneName = findViewById(R.id.edit_name_player_one);
        editPlayerTwoName = findViewById(R.id.edit_name_player_two);
    }

    // create the toolbar's menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // menu's buttons actions
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            findViewById(R.id.about_layout).setVisibility(View.VISIBLE);
        }
        if (item.getItemId() == R.id.action_settings) {
            Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
        }
        if (item.getItemId() == R.id.action_questions) {
            Intent questionsActivity = new Intent(MainActivity.this, QuestionsActivity.class);
            startActivity(questionsActivity);
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // buttons actions
        newPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPlayer();
            }
        });

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch the game activity with the players names as extras
                Intent gameActivity = new Intent(MainActivity.this, GameActivity.class);
                gameActivity.putExtra("playerOneName", editPlayerOneName.getText().toString());
                gameActivity.putExtra("playerTwoName", editPlayerTwoName.getText().toString());
                startActivity(gameActivity);
            }
        });

        aboutOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // close the about menu
                findViewById(R.id.about_layout).setVisibility(View.INVISIBLE);
            }
        });

        // edit texts actions
        editPlayerOneName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enableStartGameButton();
                newPlayer();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        editPlayerTwoName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                enableStartGameButton();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    /**
     * Enable or disable the start game button
     * to only enable it when both inputs are filled.
     */
    private void enableStartGameButton() {
        startGameButton.setEnabled(!editPlayerOneName.getText().toString().isEmpty() && !editPlayerTwoName.getText().toString().isEmpty());
    }

    /**
     * Show the edit text 2 and the start game button if edit text 1 is already visible
     * else show edit text 1
     */
    private void newPlayer() {
        if (namePlayerOne.getVisibility() == View.VISIBLE) {
            namePlayerTwo.setVisibility(View.VISIBLE);
            startGameButton.setVisibility(View.VISIBLE);
        } else {
            namePlayerOne.setVisibility(View.VISIBLE);
        }
    }
}