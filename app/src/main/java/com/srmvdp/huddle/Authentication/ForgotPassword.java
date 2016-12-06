package com.srmvdp.huddle.Authentication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.srmvdp.huddle.AdminPanel.AdminPanel;
import com.srmvdp.huddle.Dashboard;
import com.srmvdp.huddle.LoginRegisterTabbed;
import com.srmvdp.huddle.R;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private ActionBar bar;

    private EditText reg;

    private Button submit;

    private int selected;

    private String registrationnumber;

    private RadioGroup methods;

    private RadioButton email;

    private RadioButton sms;

    private RadioButton call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        bar = getSupportActionBar();

        bar.setHomeButtonEnabled(true);

        bar.setDisplayHomeAsUpEnabled(true);

        reg = (EditText) findViewById(R.id.registrationnumber);

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(this);

        methods = (RadioGroup) findViewById(R.id.methods);

        email = (RadioButton)findViewById(R.id.email);

        sms = (RadioButton)findViewById(R.id.sms);

        call = (RadioButton)findViewById(R.id.call);

    }


    private boolean isOnline() {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


    private void internetCheck() {

        if (isOnline()) {

            emptyCheck();

        }

        else {

            Toast.makeText(ForgotPassword.this, "No Internet Connection!", Toast.LENGTH_LONG).show();

        }

    }


    private void emptyCheck() {

        registrationnumber = reg.getText().toString().trim();

        selected = methods.getCheckedRadioButtonId();

        if (registrationnumber.isEmpty() || registrationnumber.length() != 15) {

            reg.requestFocus();

            Toast.makeText(ForgotPassword.this, "Incorrect Registration Number!", Toast.LENGTH_LONG).show();

        }

        else {

            switch(selected) {

                case R.id.email:

                    sendEmail();

                    break;

                case R.id.sms:

                    sendSMS();

                    break;

                case R.id.call:

                    sendCall();

                    break;

                default:

                    Toast.makeText(ForgotPassword.this, "Please Select Verification Method!", Toast.LENGTH_SHORT).show();

                    break;

            }

        }

    }


    private void sendEmail() {

        Toast.makeText(ForgotPassword.this, "Email Sent!", Toast.LENGTH_LONG).show();

    }


    private void sendSMS() {

        Toast.makeText(ForgotPassword.this, "SMS Sent!", Toast.LENGTH_LONG).show();

    }


    private void sendCall() {

        Toast.makeText(ForgotPassword.this, "Call Sent!", Toast.LENGTH_LONG).show();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                Intent back = new Intent(ForgotPassword.this, LoginRegisterTabbed.class);

                finish();

                startActivity(back);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }


    @Override
    public void onClick(View view) {

        if (view == submit) {

            internetCheck();

        }

    }


}
