package com.brosars.speedquiz.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SpeedQuizSQLite extends SQLiteOpenHelper {
    // Constants
    static String DB_NAME = "SpeedQuiz.db";
    static int DB_VERSION = 1;


    public SpeedQuizSQLite(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the table quiz
        db.execSQL("CREATE TABLE quiz(idQuiz INTEGER PRIMARY KEY, label TEXT, answer INTEGER);");
        // Insert all the questions into the db
        db.execSQL("INSERT INTO quiz VALUES (null, \"The Amazon rainforest is often referred to as the lungs of the Earth.\", 1);");
        db.execSQL("INSERT INTO quiz VALUES (null, \"Marie Curie was the first woman to win a Nobel Prize.\", 1);");
        db.execSQL("INSERT INTO quiz VALUES (null, \"The currency of South Korea is the yuan.\", 0);");
        db.execSQL("INSERT INTO quiz VALUES (null, \"The Great Wall of China was built to keep out invading Mongol forces.\", 0);");
        db.execSQL("INSERT INTO quiz VALUES (null, \"The Sahara Desert is the largest hot desert in the world.\", 1);");
        db.execSQL("INSERT INTO quiz VALUES (null, \"The speed of light is faster than the speed of sound.\", 1);");
        db.execSQL("INSERT INTO quiz VALUES (null, \"The Great Pyramid of Giza is the only remaining wonder of the ancient world.\", 1);");
        db.execSQL("INSERT INTO quiz VALUES (null, \"The currency of Mexico is the peso.\", 1);");
        db.execSQL("INSERT INTO quiz VALUES (null, \"The moon is larger than the planet Mercury.\", 0);");
        db.execSQL("INSERT INTO quiz VALUES (null, \"The longest river in Europe is the Danube.\", 0);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}