package com.example.quizapp;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {
    //Declaring 9 variables of textview
    private TextView OptionA, OptionB, OptionC, OptionD, QuestionNumber, Question, Score,Next , timerText;

    // i is the index of questionBack array
    static int i=0;

    //the score is the right questions the user answers
    int score = 0;

    //initiating ProgressBar
    ProgressBar progressBar;

    //if stop not equal 0 , Option Buttons will not work
    int stop=0;

    //timeNeed: is time Need to answer question/s
    public static long timeNeed;

    //timeLeft: the remaining time
    long timeLeft=timeNeed;

    //if all the questions have Shared time like in Reading Category or it needs a specific time for each question
    public static boolean mergeTime= false;

    //Initiating Static Array of Objects from AnswerClass Class because we assign its value to Arrays Coming from ChoiceOfQuestions Class
    public static AnswerClass[] questionBank ;
    //make timer
    CountDownTimer timer;

    // initializing variables that would hold the question, Choices A,B,C and D
    int CurrentQuestion, CurrentOptionA, CurrentOptionB, CurrentOptionC, CurrentOptionD;

    //This is the rate that the progress bar would be incremented by
    final int PROGRESS_BAR = (int) Math.ceil(100 / questionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Find the TextViews and progressbar in the layout by Id and assigning it to the variables initiated at the beginning of this class.
        OptionA = (TextView) findViewById(R.id.optionA);
        OptionB = (TextView) findViewById(R.id.optionB);
        OptionC = (TextView) findViewById(R.id.optionC);
        OptionD = (TextView) findViewById(R.id.optionD);


        QuestionNumber = (TextView) findViewById(R.id.QuestionNumber);
        Question = (TextView) findViewById(R.id.Question);
        Score = (TextView) findViewById(R.id.Score);


        Next = (TextView) findViewById(R.id.NextQ);

        timerText = (TextView) findViewById(R.id.timer);


        progressBar = (ProgressBar) findViewById(R.id.ProgressBar);

        //Assignning the questionid and choices to those variables
        ValuesAssigning();
        //call startTimer method
        startTimer();

        //when clicking on Choice A
        OptionA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopTimer();
                //if stop= 0 if the answer is right change the seleted answer's color to green
                //if stop isn't equal zero the right answer would be green and the wrong answer's color the user chose would become red
                if (stop == 0){
                    if( CheckAnswer(CurrentOptionA)){
                        OptionA.setBackgroundResource(R.drawable.green);
                    }
                    else {
                        OptionA.setBackgroundResource(R.drawable.red);
                    }
                }
            }
        });

        //same goes for option B as option A
        OptionB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopTimer();
                if (stop == 0){
                    if( CheckAnswer(CurrentOptionB)){
                        OptionB.setBackgroundResource(R.drawable.green);
                    }
                    else {
                        OptionB.setBackgroundResource(R.drawable.red);
                    }
                }
            }
        });

        //same goes for option C as option A
        OptionC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopTimer();
                if (stop == 0){
                    if( CheckAnswer(CurrentOptionC)){
                        OptionC.setBackgroundResource(R.drawable.green);
                    }
                    else {
                        OptionC.setBackgroundResource(R.drawable.red);
                    }
                }
            }
        });
        //same goes for option D as option A
        OptionD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                stopTimer();
                if (stop == 0){
                    if( CheckAnswer(CurrentOptionD)){
                        OptionD.setBackgroundResource(R.drawable.green);
                    }
                    else {
                        OptionD.setBackgroundResource(R.drawable.red);
                    }
                }
            }
        });

        Next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updateQuestion();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    //this method is responsible for updating the question whenever the user presses on Next Question button
    public void updateQuestion() {
        //call startTimer method
        startTimer();
        //mack Next textView INVISIBLE
        Next.setVisibility(INVISIBLE);
        //Allow the user to use buttons
        stop=0;
        //reset Backgrounds
        OptionA.setBackgroundResource(R.drawable.cust_button);
        OptionB.setBackgroundResource(R.drawable.cust_button);
        OptionC.setBackgroundResource(R.drawable.cust_button);
        OptionD.setBackgroundResource(R.drawable.cust_button);
        // i starts at index 0 whenever updateQuestion is called the i is incremented by 1 when it reaches the length of questionBank
        //it will go back to i = zero
        i = (i+1)%questionBank.length;
        //When index i=0 restore all the values to their initial values if the user pressed on Restart button in the AlertDialog
        //If the user presses on go to choose category it will get him to ChoiceOfQuestions Activity
        if(i == 0){

            progressBar.incrementProgressBy(PROGRESS_BAR);
            stopTimer();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Done!");
            alert.setCancelable(false);
            alert.setMessage("Your Score " +score);
            //if User click on choose category
            alert.setPositiveButton("Choose Category",new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Intent for navigator from this activity to ChoiceOfQuestions Activity
                    Intent in = new Intent(getApplicationContext(),ChoiceOfQuestions.class);
                    startActivity(in);
                }
            });
            //if User click on choose restart
            alert.setNegativeButton("Restart", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //set the next question button text to "Next Question" because it has been changed to Time is Up somewhere in the next lines of code
                    Next.setText("Next Question");
                    //restore score to 0
                    score = 0;
                    timeLeft=timeNeed;
                    startTimer();
                    //restore progress bar to 0
                    progressBar.setProgress(0);
                    //Changing text back to its intitial value at the top of the layout
                    Score.setText("Score " + score + "/" + questionBank.length);
                }
            });
            //show AlertDiaglog
            AlertDialog Dialog = alert.create();
            Dialog.show();
        }
       // calling ValuesAssigning
        ValuesAssigning();

        // Update text of QuestionNumber
        QuestionNumber.setText(i + 1 +"/"+ questionBank.length +" Question");
        // Update Score
        Score.setText("Score " + score + "/" + questionBank.length);
        // Increment Progress Bar
        progressBar.incrementProgressBy(PROGRESS_BAR);

    }
    //Assigning questions id and their choices to the variables
    private void ValuesAssigning() {
        CurrentQuestion = questionBank[i].getQuestionID();
        Question.setText(CurrentQuestion);

        CurrentOptionA = questionBank[i].getOptionA();
        OptionA.setText(CurrentOptionA);

        CurrentOptionB = questionBank[i].getOptionB();
        OptionB.setText(CurrentOptionB);

        CurrentOptionC = questionBank[i].getOptionC();
        OptionC.setText(CurrentOptionC);

        CurrentOptionD = questionBank[i].getOptionD();
        OptionD.setText(CurrentOptionD);
    }

    public boolean CheckAnswer(int SelectedAnswer) {
        int Correctans = questionBank[i].getAnswerID();
        //mack Next textView VISIBLE
        Next.setVisibility(VISIBLE);
        //Prevent the user from using the buttons
        stop=1;
        //ch1 = SelectedAnswer , ch2 = correct Answer
        String ch1 = getString(SelectedAnswer);
        String ch2 = getString(Correctans);
        //if SelectedAnswer = correct Answer : make score + 1
        //else make correct Answer color green
        if (ch1.equals(ch2)) {
            score++;
            return true;
        }else{
            CorrectAnswer(Correctans,R.drawable.green);
            return false;
        }
    }

    //check which textView have the correct Answer and change its color
    public void CorrectAnswer(int CorrectID, int color){
        String ch2 = getString(CorrectID);

        if ( ch2.equals( OptionA.getText().toString() ) ){
            OptionA.setBackgroundResource(color);
        }
        if ( ch2.equals( OptionB.getText().toString() ) ){
            OptionB.setBackgroundResource(color);
        }
        if ( ch2.equals( OptionC.getText().toString() ) ){
            OptionC.setBackgroundResource(color);
        }
        if ( ch2.equals(OptionD.getText().toString() ) ){
            OptionD.setBackgroundResource(color);
        }
    }

    public void startTimer(){
        if (!mergeTime){
            timeLeft=timeNeed;
        }

        timer= new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long l) {
                timeLeft=l;
                //call updateTimer method
                updateTimer();
            }
            @Override
            public void onFinish() {
                //make Next textView VISIBLE
                Next.setVisibility(VISIBLE);
                //Prevent the user from using the buttons
                stop=1;
                if (!mergeTime){
                    //if Time's Up and mergeTime is false
                    //set red color in CorrectAnswer
                    CorrectAnswer(questionBank[i].getAnswerID(),R.drawable.red);
                }
                if (mergeTime){
                    //change Text to Time's Up when time is Finished
                    Next.setText("Time's Up");
                    //to end quiz
                    QuizActivity.i=questionBank.length-1;
                }
            }
        }.start();
    }

    //display the time on Activity
    public void updateTimer(){
        //minute have 60000 millisecond
        int minutes = (int) timeLeft / 60000;
        //seconds = remainder of the division minutes on 60000 then division on number millisecond = 1000
        int seconds = (int) (timeLeft % 60000) / 1000;
        String timeString =minutes + ":" + seconds;
        if (seconds<10){
            //if seconds < 10 time will be (1:9)
            //so put "0" before seconds to be (1:09)
            timeString =minutes + ":" +"0"+seconds;}
        //set time on Activity
        timerText.setText(timeString);
    }

    //method to stop the timer
    public void stopTimer(){
        timer.cancel();
    }
}