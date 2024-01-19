package com.brosars.speedquiz.controllers;


import java.util.ArrayList;

public class QuestionData {
    private ArrayList<Question> questions = new ArrayList<>();

    /**
     * fill the list of questions
     */
    public void fillQuestions() {
        questions.add(new Question("The Amazon rainforest is often referred to as the \"lungs of the Earth.\"", true));
        questions.add(new Question("Marie Curie was the first woman to win a Nobel Prize.", true));
        questions.add(new Question("The currency of South Korea is the yuan.", false));
        questions.add(new Question("The Great Wall of China was built to keep out invading Mongol forces.", false));
        questions.add(new Question("The Sahara Desert is the largest hot desert in the world.", true));
        questions.add(new Question("The speed of light is faster than the speed of sound.", true));
        questions.add(new Question("The Great Pyramid of Giza is the only remaining wonder of the ancient world.", true));
        questions.add(new Question("The currency of Mexico is the peso.", true));
        questions.add(new Question("The moon is larger than the planet Mercury.", false));
        questions.add(new Question("The longest river in Europe is the Danube.", false));
    }

    /**
     * @return the questions list
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }
}
