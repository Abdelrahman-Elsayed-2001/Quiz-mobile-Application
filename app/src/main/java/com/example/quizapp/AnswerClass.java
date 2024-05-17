package com.example.quizapp;
//This Class Has one constructor with 6 private integers and their setters and getters
public class AnswerClass {

    private int OptionA,OptionB,OptionC,OptionD,QuestionID,AnswerID;

    public AnswerClass(int questionID,int optionA,int optionB,int optionC,int optionD,int answerID){

        QuestionID=questionID;
        OptionA=optionA;
        OptionB=optionB;
        OptionC=optionC;
        OptionD=optionD;
        AnswerID=answerID;
    }

    public int getOptionA() {
        return OptionA;
    }

    public int getOptionB() {
        return OptionB;
    }

    public int getOptionC() {
        return OptionC;
    }

    public int getOptionD() {
        return OptionD;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public int getAnswerID() {
        return AnswerID;
    }
}
