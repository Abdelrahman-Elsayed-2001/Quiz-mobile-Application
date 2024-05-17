package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginActivity extends AppCompatActivity {
    //Initialization of variables
    EditText Email, Password;
    TextView NoAccount;
    Button Login;
    FirebaseAuth mAuth;
    CheckBox RememberMe;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    //Sharedpref name and keys
    private static final String SHARED_PRF_NAME = "mypref";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_PASSWORD = "Password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //assigning variables to views in layout
        Email = findViewById(R.id.EnterEmail);
        mAuth = FirebaseAuth.getInstance();
        Password = findViewById(R.id.EnterPass);
        NoAccount = findViewById(R.id.NoAccountText);
        Login = findViewById(R.id.LoginButton);
        RememberMe = findViewById(R.id.RememberMe);
        //The default of the CheckBox is checked unless the user change it
        RememberMe.setChecked(true);


        editor =getSharedPreferences(SHARED_PRF_NAME,MODE_PRIVATE).edit();
        pref = getSharedPreferences(SHARED_PRF_NAME, MODE_PRIVATE);
        //Storing email and password the user enters in their keys initialized above
        String SavedEmail =  pref.getString(KEY_EMAIL,"");
        String SavedPass =  pref.getString(KEY_PASSWORD,"");

        //setting the email and password textviews to values saved in the sharepref
        Email.setText(SavedEmail);
        Password.setText(SavedPass);
        //if user has no account navigate him to sign up activity to create new account
        NoAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        //when user presses on Login button login in quiz app
        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //if no internet connection display error message else login normally
                boolean x = ChoiceOfQuestions.hasInternetConnection(getApplicationContext());
                if(x) {
                    login();
                }else{
                    Toast.makeText(getApplicationContext(),"Please Connect to the Internet!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        //login function checks if the user name and pw is stored in the firebase
    private void login() {
        //setting 2 strings to the values the user enters in the email and password textviews
        String UserEmail = Email.getText().toString().trim();
        String UserPass = Password.getText().toString();

        // if the username is empty print this error
        if (UserEmail.isEmpty()) {
            Email.setError("Email can not be empty");
        }
        // if the password is empty print this error
        if (UserPass.isEmpty()) {
            Password.setError("Password can not be empty");
        } else {
            //if email and pw are correct sign the user in the quiz app
            mAuth.signInWithEmailAndPassword(UserEmail, UserPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //if task is successfull check if the user checked remember me checkbox to save the email and password they entered in the their textviews
                    if (task.isSuccessful()) {
                        if (RememberMe.isChecked()) {
                            StoreDataUsingSharedPref(UserEmail, UserPass);

                        }else{
                            //if rememberme checkbox is not checked clear the email and password textviews
                            editor.clear();
                            editor.commit();
                        }
                        //navigate to the choiceofquestions activity to choose the category and start the quiz
                        Intent intent = new Intent(getApplicationContext(), ChoiceOfQuestions.class);
                        startActivity(intent);
                    } else {
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


                            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                Toast.makeText(getApplicationContext(), "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_DISABLED":
                                Toast.makeText(getApplicationContext(), "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_TOKEN_EXPIRED":
                                Toast.makeText(getApplicationContext(), "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_NOT_FOUND":
                                Toast.makeText(getApplicationContext(), "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_INVALID_USER_TOKEN":
                                Toast.makeText(getApplicationContext(), "The user\\'s credential is no longer valid. The user must sign in again", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_OPERATION_NOT_ALLOWED":
                                Toast.makeText(getApplicationContext(), "This operation is not allowed. You must enable this service in the console", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_WEAK_PASSWORD":
                                Toast.makeText(getApplicationContext(), "The given password is invalid", Toast.LENGTH_LONG).show();
                                break;
                                default:
                                    Toast.makeText(getApplicationContext(), "Something went wrong please try again", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

        }
    }

    //Method for assigning the entered email and password from the user to it's  in the sharedpreference
    private void StoreDataUsingSharedPref(String userEmail, String userPass) {
        editor.putString(KEY_EMAIL,userEmail);
        editor.putString(KEY_PASSWORD,userPass);
        editor.commit();
    }
}

