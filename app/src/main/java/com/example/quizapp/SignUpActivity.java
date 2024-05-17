package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class SignUpActivity extends AppCompatActivity {
    //Initialization of variables
    EditText Name,Email,Password,Phone;
    TextView AlreadyHaveAcc;
    Button Register;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // assigning variables to views in layout
        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.EnterEmail);
        Password = findViewById(R.id.EnterPass);
        AlreadyHaveAcc = findViewById(R.id.AlreadyHaveAccText);
        Register = findViewById(R.id.RegisterButton);
        //click on already have account textview to navigate to login activity
        AlreadyHaveAcc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        //press on register button to regist
        Register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //if no internet connection display error message else sign up normally
                boolean x = ChoiceOfQuestions.hasInternetConnection(getApplicationContext());
                if(x) {
                    Register();
                }else{
                    Toast.makeText(getApplicationContext(),"Please Connect to the Internet!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //register function adds the email and password in the firebase
    private void Register() {
        //setting 2 strings to the values the user enters in the email and password textviews
        String UserEmail = Email.getText().toString().trim();
        String UserPass = Password.getText().toString();
        // if the username is empty print this error
        if(UserEmail.isEmpty()){
            Email.setError("Email can not be empty");
        }
        // if the password is empty print this error
        if(UserPass.isEmpty()){
            Password.setError("Password can not be empty");
        }else{
            //if email and pw are correct create new account with email and password
            mAuth.createUserWithEmailAndPassword(UserEmail,UserPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        //if task is successful show this toast to the user and navigate to the choiceofquestions activity
                        Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),ChoiceOfQuestions.class);
                        startActivity(intent);
                    }else{
                        //if task is unsuccessful check the errorcode and print the toast corresponding to the error
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                        switch (errorCode) {

                            case "ERROR_INVALID_CUSTOM_TOKEN":
                                Toast.makeText(getApplicationContext(), "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                Toast.makeText(getApplicationContext(), "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_INVALID_CREDENTIAL":
                                Toast.makeText(getApplicationContext(), "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_INVALID_EMAIL":
                                Toast.makeText(getApplicationContext(), "The email address is badly formatted.", Toast.LENGTH_LONG).show();

                                break;

                            case "ERROR_WRONG_PASSWORD":
                                Toast.makeText(getApplicationContext(), "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_MISMATCH":
                                Toast.makeText(getApplicationContext(), "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_REQUIRES_RECENT_LOGIN":
                                Toast.makeText(getApplicationContext(), "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                Toast.makeText(getApplicationContext(), "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_EMAIL_ALREADY_IN_USE":
                                Toast.makeText(getApplicationContext(), "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                Toast.makeText(getApplicationContext(), "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                break;


                            case "ERROR_USER_TOKEN_EXPIRED":
                                Toast.makeText(getApplicationContext(), "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_NOT_FOUND":
                                Toast.makeText(getApplicationContext(), "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_INVALID_USER_TOKEN":
                                Toast.makeText(getApplicationContext(), "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_OPERATION_NOT_ALLOWED":
                                Toast.makeText(getApplicationContext(), "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_WEAK_PASSWORD":
                                Toast.makeText(getApplicationContext(), "The given password is invalid.", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                Toast.makeText(getApplicationContext(), "Something went wrong please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

        }
    }
}
