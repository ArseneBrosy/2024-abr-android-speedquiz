package com.brosars.speedquiz.controllers;

public class Question {
    private String label;
    private boolean answer;

    /**
     * Constructor
     * @param label the question's label
     * @param answer the question's answer
     */
    public Question(String label, boolean answer) {
        this.label = label;
        this.answer = answer;
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
    public boolean getAnswer() {
        return answer;
    }
}
