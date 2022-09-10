package com.cokerj.collegecompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.User;
import com.cokerj.collegecompanion.UI.CreateNewUser;
import com.cokerj.collegecompanion.UI.TermsScreen;

public class MainActivity extends AppCompatActivity {
    Repository repo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new Repository(getApplication());
        repo.logOutUser();
        setContentView(R.layout.activity_main);
    }

    public void enterApp(View view) {
        TextView incorrectLoginTextView = findViewById(R.id.incorrectLoginTextView);
        incorrectLoginTextView.setVisibility(View.INVISIBLE);
        TextView userName = findViewById(R.id.loginUserNameField);
        TextView password = findViewById(R.id.loginUserPasswordField);
        if (userName.getText().toString().equals("") || password.getText().toString().equals("")){
            return;
        } else {
            User signedInUser = repo.testLogIn(userName.getText().toString(), password.getText().toString());
            if (signedInUser == null){

                incorrectLoginTextView.setVisibility(View.VISIBLE);
            }else{
                Intent intent = new Intent(MainActivity.this, TermsScreen.class);
                repo.logInUser(signedInUser.getUserId());
                startActivity(intent);
            }
        }
    }



    public void toCreateUserScreen(View view){
        Intent intent = new Intent(MainActivity.this, CreateNewUser.class);
        startActivity(intent);
    }
}