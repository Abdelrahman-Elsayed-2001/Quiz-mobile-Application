package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChoiceOfQuestions extends AppCompatActivity {
    //Declaring variables
    TextView   ch1,ch2,ch3;
    FirebaseAuth mAuth;
    Button LogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choice_of_questions);
        //assign variables to their id in the layout
        ch1 = findViewById(R.id.First_Category);
        ch2 = findViewById(R.id.Second_Category);
        ch3 = findViewById(R.id.Third_Category);
        LogOut= findViewById(R.id.LogOut);
        mAuth = FirebaseAuth.getInstance();

        //Declaring array of objects from AnswerClass class for storage of Math questions from string.xml file
         AnswerClass[] questionBankM = {
                new AnswerClass(R.string.Question_1m, R.string.Question1_Am, R.string.Question1_Bm, R.string.Question1_Cm, R.string.Question1_Dm, R.string.Answer_1m),
                new AnswerClass(R.string.Question_2m, R.string.Question2_Am, R.string.Question2_Bm, R.string.Question2_Cm, R.string.Question2_Dm, R.string.Answer_2m),
                new AnswerClass(R.string.Question_3m, R.string.Question3_Am, R.string.Question3_Bm, R.string.Question3_Cm, R.string.Question3_Dm, R.string.Answer_3m),
                new AnswerClass(R.string.Question_4m, R.string.Question4_Am, R.string.Question4_Bm, R.string.Question4_Cm, R.string.Question4_Dm, R.string.Answer_4m),
                new AnswerClass(R.string.Question_5m, R.string.Question5_Am, R.string.Question5_Bm, R.string.Question5_Cm, R.string.Question5_Dm, R.string.Answer_5m),
                new AnswerClass(R.string.Question_6m, R.string.Question6_Am, R.string.Question6_Bm, R.string.Question6_Cm, R.string.Question6_Dm, R.string.Answer_6m),
                new AnswerClass(R.string.Question_7m, R.string.Question7_Am, R.string.Question7_Bm, R.string.Question7_Cm, R.string.Question7_Dm, R.string.Answer_7m),
                new AnswerClass(R.string.Question_8m, R.string.Question8_Am, R.string.Question8_Bm, R.string.Question8_Cm, R.string.Question8_Dm, R.string.Answer_8m),
        };

        //Declaring array of objects from AnswerClass class for storage of GENERAL questions from string.xml file
         AnswerClass[] questionBankG = {
                new AnswerClass(R.string.Question_1, R.string.Question1_A, R.string.Question1_B, R.string.Question1_C, R.string.Question1_D, R.string.Answer_1),
                new AnswerClass(R.string.Question_2, R.string.Question2_A, R.string.Question2_B, R.string.Question2_C, R.string.Question2_D, R.string.Answer_2),
                new AnswerClass(R.string.Question_3, R.string.Question3_A, R.string.Question3_B, R.string.Question3_C, R.string.Question3_D, R.string.Answer_3),
                new AnswerClass(R.string.Question_4, R.string.Question4_A, R.string.Question4_B, R.string.Question4_C, R.string.Question4_D, R.string.Answer_4),
                new AnswerClass(R.string.Question_5, R.string.Question5_A, R.string.Question5_B, R.string.Question5_C, R.string.Question5_D, R.string.Answer_5),
                new AnswerClass(R.string.Question_6, R.string.Question6_A, R.string.Question6_B, R.string.Question6_C, R.string.Question6_D, R.string.Answer_6),
                new AnswerClass(R.string.Question_7, R.string.Question7_A, R.string.Question7_B, R.string.Question7_C, R.string.Question7_D, R.string.Answer_7),
                new AnswerClass(R.string.Question_8, R.string.Question8_A, R.string.Question8_B, R.string.Question8_C, R.string.Question8_D, R.string.Answer_8),
        };

        //Declaring array of objects from AnswerClass class for storage of READING questions from string.xml file
        AnswerClass[] questionBankR = {
                new AnswerClass(R.string.Question_1R, R.string.Question1_AR, R.string.Question1_BR, R.string.Question1_CR, R.string.Question1_DR, R.string.Answer_1R),
                new AnswerClass(R.string.Question_2R, R.string.Question2_AR, R.string.Question2_BR, R.string.Question2_CR, R.string.Question2_DR, R.string.Answer_2R),
                new AnswerClass(R.string.Question_3R, R.string.Question3_AR, R.string.Question3_BR, R.string.Question3_CR, R.string.Question3_DR, R.string.Answer_3R),
                new AnswerClass(R.string.Question_4R, R.string.Question4_AR, R.string.Question4_BR, R.string.Question4_CR, R.string.Question4_DR, R.string.Answer_4R),
                new AnswerClass(R.string.Question_5R, R.string.Question5_AR, R.string.Question5_BR, R.string.Question5_CR, R.string.Question5_DR, R.string.Answer_5R),
                new AnswerClass(R.string.Question_6R, R.string.Question6_AR, R.string.Question6_BR, R.string.Question6_CR, R.string.Question6_DR, R.string.Answer_6R),
                new AnswerClass(R.string.Question_7R, R.string.Question7_AR, R.string.Question7_BR, R.string.Question7_CR, R.string.Question7_DR, R.string.Answer_7R),
                new AnswerClass(R.string.Question_8R, R.string.Question8_AR, R.string.Question8_BR, R.string.Question8_CR, R.string.Question8_DR, R.string.Answer_8R),
        };

        //when clicking on category 1 TEXTVIEW (GENERAL)
        ch1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                QuizCategorySetting(questionBankG, 30000, false);
            }
        });
        //when clicking on category 2 TEXTVIEW (GENERAL)
        ch2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                QuizCategorySetting(questionBankM, 120000, false);
            }
        });

        //when clicking on category 3 TEXTVIEW (GENERAL)
        ch3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //if no internet connection you can't start Reading category because you to download a pdf file before answering the questions
               boolean x = hasInternetConnection(getApplicationContext());
               if(x) {
                   startDownload();
                   //time in millisecond = 15 Mins
                   QuizCategorySetting(questionBankR, 900000, true);
               }else{
                   Toast.makeText(getApplicationContext(),"Please Connect to the Internet!",Toast.LENGTH_SHORT).show();
               }
            }
        });

        //When pressing on LogOut Button
        LogOut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //call logout method
                logout();
            }
        });
    }

    private void QuizCategorySetting(AnswerClass[] questionbank, int i, boolean b) {
        //assigning questionback static Array in QuizActivity to the corresponding array of the category
        QuizActivity.questionBank = questionbank;
        //time for answering questions
        QuizActivity.timeNeed = i;
        //if all the questions have Shared time like in Reading Category or it needs a specific time for each question
        QuizActivity.mergeTime = b;
//start QuizActivity
        Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
        startActivity(intent);
    }

//Log out from the quiz application and navigate me to login activity
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);

    }
//Download the Passage of the reading category
    private void startDownload() {
        try
        {
            Uri uri = Uri.parse("https://dl.dropboxusercontent.com/s/bwmsxsthug8dshy/Read%20the%20passage%20given%20below.pdf?dl=0");
            DownloadManager.Request request = new DownloadManager.Request(uri)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedOverMetered(true)
                    .setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, "File.pdf")
                    .setTitle("PDF");
            DownloadManager manager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            long reference = manager.enqueue(request);
            request.setTitle(String.valueOf(reference));
            Toast.makeText(this,"Download Complete",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"Download Failed",Toast.LENGTH_SHORT).show();
        }
    }

//Check internet Connection
    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager check = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = check.getActiveNetworkInfo();
        return info != null && info.isConnected();

    }
}