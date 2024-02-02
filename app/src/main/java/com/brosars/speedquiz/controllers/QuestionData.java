package com.brosars.speedquiz.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.brosars.speedquiz.models.SpeedQuizSQLite;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionData {
    private ArrayList<Question> questions = new ArrayList<>();

    public QuestionData(Context context) {
        questions = initQuestionList(context);
    }

    /**
     * Charge une liste de question depuis la DB.
     * @param context Le contexte de l'application pour passer la query
     * @return Une arraylist charger de Question
     */
    private ArrayList<Question> initQuestionList(Context context){
        ArrayList<Question> listQuestion = new ArrayList<>();
        SpeedQuizSQLite helper = new SpeedQuizSQLite(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(true,"quiz",new String[]{"idQuiz","label","answer"},null,null,null,null,"idquiz",null);

        while(cursor.moveToNext()){
            listQuestion.add(new Question(cursor));
        }
        cursor.close();
        db.close();

        Collections.shuffle(listQuestion);

        // get settings for questions speed
        SharedPreferences sharedPref = context.getSharedPreferences("IDValue", 0);
        int questionCount = sharedPref.getInt("settings_question_count", listQuestion.size());

        return new ArrayList<>(listQuestion.subList(0, questionCount));
    }

    /**
     * @return the questions list
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
