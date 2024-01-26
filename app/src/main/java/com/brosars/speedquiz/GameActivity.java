package com.brosars.speedquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.brosars.speedquiz.controllers.QuestionData;
import com.brosars.speedquiz.models.GameManager;
import com.google.android.material.button.MaterialButton;

public class GameActivity extends AppCompatActivity {

    private GameManager gameManager = new GameManager();

    private TextView playerOneNameText;
    private TextView playerTwoNameText;
    private TextView playerOneScoreText;
    private TextView playerTwoScoreText;
    private TextView questionOneText;
    private TextView questionTwoText;
    private MaterialButton playerOneButton;
    private MaterialButton playerTwoButton;
    private RelativeLayout endButtons;
    private MaterialButton restartButton;
    private MaterialButton menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // get the fields
        playerOneNameText = findViewById(R.id.name_player_1);
        playerTwoNameText = findViewById(R.id.name_player_2);
        playerOneScoreText = findViewById(R.id.score_player_1);
        playerTwoScoreText = findViewById(R.id.score_player_2);
        questionOneText = findViewById(R.id.question_player_1);
        questionTwoText = findViewById(R.id.question_player_2);
        playerOneButton = findViewById(R.id.button_player_1);
        playerTwoButton = findViewById(R.id.button_player_2);
        endButtons = findViewById(R.id.end_buttons);
        restartButton = findViewById(R.id.button_restart);
        menuButton = findViewById(R.id.button_menu);

        // set the players names
        Intent gameActivity = getIntent();

        String playerOneName = String.valueOf(gameActivity.getStringExtra("playerOneName"));
        String playerTwoName = String.valueOf(gameActivity.getStringExtra("playerTwoName"));

        playerOneNameText.setText(playerOneName);
        playerTwoNameText.setText(playerTwoName);
    }

    @Override
    protected void onStart() {
        super.onStart();

        gameManager.setGameActivity(this);
        gameManager.questionData = new QuestionData(this);
        gameManager.startTimer();

        playerOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.answerQuestion(1);
            }
        });

        playerTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.answerQuestion(2);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Update the showed question
     * @param question new question
     */
    public void setQuestion(String question) {
        questionOneText.setText(question);
        questionTwoText.setText(question);
    }

    /**
     * Update the scores showed on the screen
     * @param scorePlayerOne score of the player one
     * @param scorePlayerTwo score of the player two
     */
    public void setScores(int scorePlayerOne, int scorePlayerTwo) {
        playerOneScoreText.setText(String.valueOf(scorePlayerOne));
        playerTwoScoreText.setText(String.valueOf(scorePlayerTwo));
    }

    /**
     * Getter to the endButtons
     * @return the endButtons
     */
    public RelativeLayout getEndButtons() {
        return endButtons;
    }
}
