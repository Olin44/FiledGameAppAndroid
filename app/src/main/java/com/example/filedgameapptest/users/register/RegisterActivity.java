package com.example.filedgameapptest.users.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.filedgameapptest.MainActivity;
import com.example.filedgameapptest.R;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;
    private Button btnSignUp;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText passwordConfEditText;
    private TextWatcher afterTextChangedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerViewModel = new RegisterViewModel();
        initViews();

        registerViewModel.getRegisterFormState().observe(this, new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                btnSignUp.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerFormState.getEmailError()));
                }
                if (registerFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(registerFormState.getUsernameError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
                if (registerFormState.getPasswordConfError() != null) {
                    passwordConfEditText.setError(getString(registerFormState.getPasswordConfError()));
                }
                if (registerFormState.isDataValid()) {

                    btnSignUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = usernameEditText.getText().toString();
                            String email = emailEditText.getText().toString();
                            String password = passwordEditText.getText().toString();
                            Intent newUserIntent = new Intent(RegisterActivity.this, NewUserActivity.class);
                            newUserIntent.putExtra("email", email);
                            newUserIntent.putExtra("username", name);
                            newUserIntent.putExtra("password", password);
                            startActivity(newUserIntent);
                        }
                    });
                }

            }
        });
        setTextWatcher();

    }


    private void setTextWatcher() {
        afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerDataChanged(emailEditText.getText().toString(),
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        passwordConfEditText.getText().toString()
                );
            }
        };

        emailEditText.addTextChangedListener(afterTextChangedListener);
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordConfEditText.addTextChangedListener(afterTextChangedListener);
    }


    private void initViews() {
        emailEditText = findViewById(R.id.email);
        usernameEditText = findViewById(R.id.username);
        ;
        passwordEditText = findViewById(R.id.password);
        ;
        passwordConfEditText = findViewById(R.id.passwordConf);
        ;

        btnSignUp = findViewById(R.id.btnSignUp);

    }


}