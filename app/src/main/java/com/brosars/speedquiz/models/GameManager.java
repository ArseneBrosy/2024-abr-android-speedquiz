package com.brosars.speedquiz.models;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.brosars.speedquiz.GameActivity;
import com.brosars.speedquiz.controllers.QuestionData;

import java.util.Timer;
import java.util.TimerTask;

public class GameManager {
    private final static int QUESTION_TIME = 5000;
    public QuestionData questionData;
    private int questionIndex = 0;
    private GameActivity gameActivity;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private Runnable nextQuestionRunnable = new Runnable() {
        @Override
        public void run() {
            nextQuestion();
            // Show the next question after the time is up
            mainHandler.postDelayed(this, QUESTION_TIME);
        }
    };
    private int startTimer = 5;
    private Runnable startTimerRunnable = new Runnable() {
        @Override
        public void run() {
            startTimer--;
            if (startTimer > 0) {
                gameActivity.setQuestion(String.valueOf(startTimer));
                mainHandler.postDelayed(this, 1000);
            } else {
                startGame();
            }
        }
    };

    public void startTimer() {
        startTimer = 5;
        mainHandler.removeCallbacks(nextQuestionRunnable);
        gameActivity.setQuestion(String.valueOf(startTimer));
        mainHandler.postDelayed(startTimerRunnable, 1000);
    }

    /**
     * Start the game by showing the first question
     */
    public void startGame() {
        gameActivity.getEndButtons().setVisibility(View.INVISIBLE);
        gameActivity.setQuestion(questionData.getQuestions().get(0).getLabel());
        // Show the next question after the time is up
        mainHandler.postDelayed(nextQuestionRunnable, QUESTION_TIME);

        playerOneScore = 0;
        playerTwoScore = 0;
        gameActivity.setScores(playerOneScore, playerTwoScore);
    }

    /**
     * Show the next question
     */
    public void nextQuestion() {
        questionIndex++;
        if (questionIndex >= questionData.getQuestions().size()) {
            gameActivity.getEndButtons().setVisibility(View.VISIBLE);
            return;
        }
        gameActivity.setQuestion(questionData.getQuestions().get(questionIndex).getLabel());
    }

    /**
     * @return true if the currently showed question is true, else false
     */
    public int getCurrentAnswer() {
        return questionData.getQuestions().get(questionIndex).getAnswer();
    }

    /**
     * answer the current question and update the scores
     * @param player the player who answered the question
     */
    public void answerQuestion(int player) {
        if (questionIndex >= questionData.getQuestions().size()) {
            return;
        }
        int scoreAdder = getCurrentAnswer() == 1 ? 1 : -1;
        playerOneScore += player == 1 ? scoreAdder : 0;
        playerTwoScore += player == 2 ? scoreAdder : 0;
        // don't let the scores go below 0
        playerOneScore = Math.max(playerOneScore, 0);
        playerTwoScore = Math.max(playerTwoScore, 0);
        gameActivity.setScores(playerOneScore, playerTwoScore);
        // reset the delay to the next question
        mainHandler.removeCallbacks(nextQuestionRunnable);
        mainHandler.postDelayed(nextQuestionRunnable, QUESTION_TIME);
        nextQuestion();
    }

    /**
     * Setter to the gameActivity
     * @param gameActivity the game activity
     */
    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }
}
