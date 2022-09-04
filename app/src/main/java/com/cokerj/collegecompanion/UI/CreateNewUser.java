package com.cokerj.collegecompanion.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.User;
import com.cokerj.collegecompanion.R;


public class CreateNewUser extends AppCompatActivity {
    Repository repo;
    TextView createUserNameField;
    TextView createPasswordField;
    TextView confirmPasswordField;
    TextView invalidUsernameTextView;
    TextView passwordNotMatchingTextView;
    TextView confirmUserCreateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        repo = new Repository(getApplication());
        createUserNameField = findViewById(R.id.createUserNameField);
        createPasswordField = findViewById(R.id.createPasswordField);
        confirmPasswordField = findViewById(R.id.confirmPasswordField);
        invalidUsernameTextView = findViewById(R.id.invalidUsernameTextView);
        passwordNotMatchingTextView = findViewById(R.id.passwordNotMatchingTextView);
        confirmUserCreateTextView = findViewById(R.id.confirmUserCreateTextView);

        createUserNameField.setText("");
        createPasswordField.setText("");
        confirmPasswordField.setText("");
        invalidUsernameTextView.setVisibility(View.INVISIBLE);
        passwordNotMatchingTextView.setVisibility(View.INVISIBLE);
        confirmUserCreateTextView.setVisibility(View.INVISIBLE);

        createUserNameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        createPasswordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        confirmPasswordField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
    }

    public void toSignInScreen(View view) {
        Intent intent = new Intent(CreateNewUser.this, MainActivity.class);
        startActivity(intent);
    }
    public void createUser(View view){
        invalidUsernameTextView.setVisibility(View.INVISIBLE);
        passwordNotMatchingTextView.setVisibility(View.INVISIBLE);
        confirmUserCreateTextView.setVisibility(View.INVISIBLE);

        if (createUserNameField.getText().toString().equals("") ||
                createPasswordField.getText().toString().equals("") ||
                confirmPasswordField.getText().toString().equals("")
        ){
            return;
        }else {
            String userName = createUserNameField.getText().toString();
            String password = createPasswordField.getText().toString();
            String confirmPassword = confirmPasswordField.getText().toString();

            boolean nameAvailable = repo.checkUserNameAvailability(userName) == null;
            if (!nameAvailable){
                invalidUsernameTextView.setVisibility(View.VISIBLE);
            }else {
                boolean passwordMatches = password.equals(confirmPassword);
                if (!passwordMatches){
                    passwordNotMatchingTextView.setVisibility(View.VISIBLE);
                }else{
                    User newUser = new User(userName, password);
                    repo.insert(newUser);
                    createUserNameField.setText("");
                    createPasswordField.setText("");
                    confirmPasswordField.setText("");
                    confirmUserCreateTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
