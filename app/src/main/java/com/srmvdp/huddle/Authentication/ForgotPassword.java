package com.srmvdp.huddle.Authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.R;
import com.srmvdp.huddle.Server.RegisterUserClass;

import java.util.HashMap;
import java.util.StringTokenizer;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private static final String REQUEST_OTP = "http://codevars.esy.es/resetpassword.php";

    private static final String REQUEST_CALL = "http://codevars.esy.es/call/call.php";

    private EditText reg;

    private Button submit;

    private int selected;

    private String method;

    private String registrationnumber;

    private String otp;

    private String mobile;

    private RadioGroup methods;

    private RadioButton email;

    private RadioButton sms;

    private RadioButton call;

    private Animation buttonup;

    private LinearLayout back;

    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        session = new SessionManagement(getApplicationContext());

        reg = (EditText) findViewById(R.id.registrationnumber);

        submit = (Button) findViewById(R.id.submit);

        methods = (RadioGroup) findViewById(R.id.methods);

        email = (RadioButton) findViewById(R.id.email);

        sms = (RadioButton) findViewById(R.id.sms);

        call = (RadioButton) findViewById(R.id.call);

        back = (LinearLayout) findViewById(R.id.gobackcontainer);

        submit.setOnClickListener(this);

        back.setOnClickListener(this);

        hideStatusBar();

        slideup();

    }


    private boolean isOnline() {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


    private void internetCheck() {

        if (isOnline()) {

            emptyCheck();

        } else {

            Toast.makeText(ForgotPassword.this, "No Internet Connection!", Toast.LENGTH_LONG).show();

        }

    }


    private void hideStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        }

    }


    private void slideup() {

        buttonup = new TranslateAnimation(0, 0, 500, 0);

        buttonup.setDuration(1000);

        submit.setAnimation(buttonup);

    }


    private void emptyCheck() {

        registrationnumber = reg.getText().toString().trim();

        selected = methods.getCheckedRadioButtonId();

        if (registrationnumber.isEmpty() || registrationnumber.length() != 15) {

            reg.requestFocus();

            Toast.makeText(ForgotPassword.this, "Incorrect Registration Number!", Toast.LENGTH_LONG).show();

        } else {

            switch (selected) {

                case R.id.email:

                    method = "email";

                    sendEmail(method, registrationnumber);

                    break;

                case R.id.sms:

                    method = "sms";

                    sendSMS(method, registrationnumber);

                    break;

                case R.id.call:

                    method = "call";

                    sendCall(registrationnumber);

                    break;

                default:

                    Toast.makeText(ForgotPassword.this, "Please Select Verification Method!", Toast.LENGTH_SHORT).show();

                    break;

            }

        }

    }


    private void sendEmail(final String method, final String registrationnumber) {

        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = new ProgressDialog(ForgotPassword.this, R.style.MyTheme);
                loading.setCancelable(false);
                loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                loading.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equalsIgnoreCase("")) {

                    Toast.makeText(ForgotPassword.this, "Please Try Again!", Toast.LENGTH_LONG).show();

                } else if (s.contains("@")) {

                    otp = s.substring(0, 4);

                    mobile = s.substring(4);

                    session.createForgotSession(registrationnumber, mobile, otp, "An Email Has Been Sent To");

                    Toast.makeText(ForgotPassword.this, "Email Sent Successfully!", Toast.LENGTH_LONG).show();

                    Intent back = new Intent(ForgotPassword.this, ForgotOTP.class);

                    startActivity(back);

                    finish();

                } else {

                    Toast.makeText(ForgotPassword.this, s, Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("method", params[0]);
                data.put("registrationnumber", params[1]);

                String result = ruc.sendPostRequest(REQUEST_OTP, data);

                return result;

            }

        }

        RegisterUser ru = new RegisterUser();

        ru.execute(method, registrationnumber);

    }


    private void sendSMS(final String method, final String registrationnumber) {

        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = new ProgressDialog(ForgotPassword.this, R.style.MyTheme);
                loading.setCancelable(false);
                loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                loading.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equalsIgnoreCase("")) {

                    Toast.makeText(ForgotPassword.this, "Please Try Again!", Toast.LENGTH_LONG).show();

                }

                else if (s.length() == 14) {

                    otp = s.substring(0, 4);

                    mobile = s.substring(4, 14);

                    session.createForgotSession(registrationnumber, mobile, otp, "An SMS Has Been Sent To");

                    Toast.makeText(ForgotPassword.this, "SMS Sent Successfully!", Toast.LENGTH_LONG).show();

                    Intent back = new Intent(ForgotPassword.this, ForgotOTP.class);

                    startActivity(back);

                    finish();

                } else {

                    Toast.makeText(ForgotPassword.this, s, Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("method", params[0]);
                data.put("registrationnumber", params[1]);

                String result = ruc.sendPostRequest(REQUEST_OTP, data);

                return result;

            }

        }

        RegisterUser ru = new RegisterUser();

        ru.execute(method, registrationnumber);

    }


    private void sendCall(final String to) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = new ProgressDialog(ForgotPassword.this, R.style.MyTheme);
                loading.setCancelable(false);
                loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                loading.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equalsIgnoreCase("")) {

                    Toast.makeText(ForgotPassword.this, "Please Try Again!", Toast.LENGTH_LONG).show();

                } else if (s.length() == 20) {

                    otp = s.substring(0, 4);

                    mobile = s.substring(4, 14);

                    session.createForgotSession(registrationnumber, mobile, otp, "We Just Tried To Call You At");

                    Toast.makeText(ForgotPassword.this, "OTP Call Requested!", Toast.LENGTH_LONG).show();

                    finish();

                    Intent back = new Intent(ForgotPassword.this, ForgotOTP.class);

                    startActivity(back);



                } else {

                    Toast.makeText(ForgotPassword.this, s, Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("registrationnumber", params[0]);

                String result = ruc.sendPostRequest(REQUEST_CALL, data);

                return result;
            }

        }

        RegisterUser ru = new RegisterUser();

        ru.execute(to);

    }


    @Override
    public void onClick(View view) {

        if (view == submit) {

            internetCheck();

        }

        if (view == back) {

            Intent go = new Intent(ForgotPassword.this, LoginRegisterTabbed.class);

            startActivity(go);

            finish();

        }

    }


}
