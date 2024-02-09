package com.brosars.speedquiz.models;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.brosars.speedquiz.GameActivity;
import com.brosars.speedquiz.controllers.QuestionData;

public class GameManager {
    private int questionsSpeed;
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
            mainHandler.postDelayed(this, questionsSpeed);
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
                // start timer ended
                startGame();
            }
        }
    };

    /**
     * Start the countdown to the first question
     */
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
        mainHandler.postDelayed(nextQuestionRunnable, questionsSpeed);

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
            // if it is was the last one, show the end buttons
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
        // ignore if the game is ended
        if (questionIndex >= questionData.getQuestions().size()) {
            return;
        }
        // update the players scores
        int scoreAdder = getCurrentAnswer() == 1 ? 1 : -1;
        playerOneScore += player == 1 ? scoreAdder : 0;
        playerTwoScore += player == 2 ? scoreAdder : 0;
        // don't let the scores go below 0
        playerOneScore = Math.max(playerOneScore, 0);
        playerTwoScore = Math.max(playerTwoScore, 0);
        gameActivity.setScores(playerOneScore, playerTwoScore);
        // reset the delay to the next question
        mainHandler.removeCallbacks(nextQuestionRunnable);
        mainHandler.postDelayed(nextQuestionRunnable, questionsSpeed);
        nextQuestion();
    }

    /**
     * Setter to the gameActivity
     * @param gameActivity the game activity
     */
    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    /**
     * Setter to the questions speed
     * @param questionsSpeed the questions speed
     */
    public void setQuestionsSpeed(int questionsSpeed) {
        this.questionsSpeed = questionsSpeed;
    }
}
