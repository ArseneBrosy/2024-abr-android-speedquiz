package com.brosars.speedquiz.controllers;

import android.database.Cursor;

public class Question {
    private String label;
    private int answer;

    /**
     * Constructor
     * @param label the question's label
     * @param answer the question's answer
     */
    public Question(String label, int answer) {
        this.label = label;
        this.answer = answer;
    }

    public Question(Cursor cursor){
        this.label = cursor.getString(cursor.getColumnIndexOrThrow("label"));
        this.answer = cursor.getInt(cursor.getColumnIndexOrThrow("answer"));
    }

    /**
     * @return the question's label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the question's answer
     */
    public int getAnswer() {
        return answer;
    }
}
