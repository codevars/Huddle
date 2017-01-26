package com.srmvdp.huddle.Authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.srmvdp.huddle.Dashboard;
import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.R;
import com.srmvdp.huddle.Server.RegisterUserClass;

import java.util.HashMap;

public class OTP extends AppCompatActivity implements View.OnClickListener {

    private static final String REQUEST_OTP = "http://codevars.esy.es/requestotp.php";

    private static final String REQUEST_VERIFIED = "http://codevars.esy.es/verified.php";

    private String countrycode;

    private String registrationnumber;

    private String mobile;

    private String firebase;

    private String onetimepassword;

    private String first;

    private String second;

    private String third;

    private String four;

    private String otp;

    private TextView number;

    private TextView timer;

    private TextView resend;

    private Animation buttonup;

    private Button submit;

    private ImageView imagetimer;

    private SessionManagement session;

    private ActionBar bar;

    static EditText otpfieldone;

    static EditText otpfieldtwo;

    static EditText otpfieldthree;

    static EditText otpfieldfour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        session = new SessionManagement(getApplicationContext());

        HashMap<String, String> mob = session.getMobileDetails();

        HashMap<String, String> reg = session.getRegistrationDetails();

        HashMap<String, String> token = session.getFirebaseTokenDetails();

        HashMap<String, String> rotp = session.getOTPDetails();

        bar = getSupportActionBar();

        bar.setHomeButtonEnabled(true);

        bar.setDisplayHomeAsUpEnabled(true);

        mobile = mob.get(SessionManagement.MOB_NUM);

        registrationnumber = reg.get(SessionManagement.REG_NUM);

        firebase = token.get(SessionManagement.TOKEN);

        onetimepassword = rotp.get(SessionManagement.OTP);

        timer = (TextView) findViewById(R.id.timer);

        imagetimer = (ImageView) findViewById(R.id.imagetimer);

        resend = (TextView) findViewById(R.id.resend);

        resend.setVisibility(View.GONE);

        resend.setOnClickListener(this);

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(this);

        submit.setEnabled(false);

        submit.setBackgroundColor(Color.parseColor("#992b2b2b"));

        otpfieldone = (EditText) findViewById(R.id.o1);

        otpfieldtwo = (EditText) findViewById(R.id.o2);

        otpfieldthree = (EditText) findViewById(R.id.o3);

        otpfieldfour = (EditText) findViewById(R.id.o4);

        otpfieldtwo.setEnabled(false);

        otpfieldthree.setEnabled(false);

        otpfieldfour.setEnabled(false);

        number = (TextView) findViewById(R.id.number);

        number.setText(mobile);

        countdown();

        slideup();

        textWatcher();

    }


    public boolean isOnline(final Context context) {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


    public void check(String prompt) {

        if (isOnline(this)) {

            if (prompt.equalsIgnoreCase("submit")) {

                verification();

            } else if (prompt.equalsIgnoreCase("resend")) {

                requestparamater();

            }

        } else {

            Toast.makeText(this, "No Connection Found!", Toast.LENGTH_SHORT).show();

        }


    }


    public void recivedSms(String message) {
        try {

            String removeend = message.substring(45);

            removeend = removeend.substring(0, removeend.length() - 12);

            otpfieldone.setText(removeend.substring(0));

            otpfieldtwo.setText(removeend.substring(1));

            otpfieldthree.setText(removeend.substring(2));

            otpfieldfour.setText(removeend.substring(3));

        } catch (Exception e) {

        }

    }


    public void countdown() {

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

                timer.setText("Seconds Remaining: " + millisUntilFinished / 1000);

            }

            public void onFinish() {

                imagetimer.setVisibility(View.GONE);

                timer.setVisibility(View.GONE);

                resend.setVisibility(View.VISIBLE);

                resend.setText("Didn't Get OTP? Try Again.");

            }

        }.start();

    }


    private void slideup() {

        buttonup = new TranslateAnimation(0, 0, 500, 0);

        buttonup.setDuration(1000);

        submit.setAnimation(buttonup);

    }


    private void verification() {

        HashMap<String, String> rotp = session.getOTPDetails();

        onetimepassword = rotp.get(SessionManagement.OTP);

        first = otpfieldone.getText().toString();

        second = otpfieldtwo.getText().toString();

        third = otpfieldthree.getText().toString();

        otp = first + second + third + four;

        if (otp.equals(onetimepassword)) {

            verified(mobile, registrationnumber, firebase);

        } else {

            Toast.makeText(this, onetimepassword, Toast.LENGTH_SHORT).show();

            Toast.makeText(this, "Incorrect OTP!", Toast.LENGTH_SHORT).show();

        }

    }


    public void requestparamater() {

        String countrycode = "91";

        String cmob = countrycode + mobile;

        String reg = registrationnumber;

        String phone = mobile;

        requestotp(cmob, reg, phone);

    }


    private void textWatcher() {

        otpfieldone.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {

                if (otpfieldone.getText().toString().length() == 2) {

                    String o = otpfieldone.getText().toString();

                    otpfieldtwo.setText(o.substring(1));

                    otpfieldtwo.setEnabled(true);

                    otpfieldtwo.setSelection(otpfieldtwo.getText().length());

                    otpfieldtwo.requestFocus();

                    String text = otpfieldone.getText().toString();

                    otpfieldone.setText(text.substring(0, text.length() - 1));

                }

            }

        });

        otpfieldtwo.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {

                if (otpfieldtwo.getText().toString().length() == 0) {

                    otpfieldone.setSelection(otpfieldone.getText().length());

                    otpfieldone.requestFocus();

                    otpfieldtwo.setEnabled(false);

                }

                if (otpfieldtwo.getText().toString().length() == 2) {

                    String t = otpfieldtwo.getText().toString();

                    otpfieldthree.setText(t.substring(1));

                    otpfieldthree.setEnabled(true);

                    otpfieldthree.setSelection(otpfieldthree.getText().length());

                    otpfieldthree.requestFocus();

                    String lol = otpfieldtwo.getText().toString();

                    otpfieldtwo.setText(lol.substring(0, lol.length() - 1));

                }

            }

        });

        otpfieldthree.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {

                if (otpfieldthree.getText().toString().length() == 0) {

                    otpfieldtwo.setSelection(otpfieldtwo.getText().length());

                    otpfieldtwo.requestFocus();

                    otpfieldthree.setEnabled(false);

                }

                if (otpfieldthree.getText().toString().length() == 2) {

                    String t = otpfieldthree.getText().toString();

                    otpfieldfour.setText(t.substring(1));

                    otpfieldfour.setEnabled(true);

                    otpfieldfour.setSelection(otpfieldfour.getText().length());

                    otpfieldfour.requestFocus();

                    String text = otpfieldthree.getText().toString();

                    otpfieldthree.setText(text.substring(0, text.length() - 1));

                }

            }

        });

        otpfieldfour.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                four = otpfieldfour.getText().toString();

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {

                if (otpfieldfour.getText().toString().length() == 0) {

                    otpfieldthree.setSelection(otpfieldthree.getText().length());

                    otpfieldthree.requestFocus();

                    otpfieldfour.setEnabled(false);

                    submit.setEnabled(false);

                    submit.setBackgroundColor(Color.parseColor("#992b2b2b"));

                }

                if (otpfieldfour.getText().toString().length() == 1) {

                    submit.setEnabled(true);

                    submit.setBackgroundColor(Color.parseColor("#2b2b2b"));

                }

            }

        });

    }


    private void requestotp(final String mobile, String reg, String phone) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = new ProgressDialog(OTP.this, R.style.MyTheme);
                loading.setCancelable(false);
                loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                loading.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equalsIgnoreCase("")) {

                    Toast.makeText(OTP.this, "Please Try Again Later!", Toast.LENGTH_LONG).show();

                } else {

                    if (s.length() == 4) {

                        Toast.makeText(OTP.this, "OTP Sent!", Toast.LENGTH_LONG).show();

                        HashMap<String, String> rotp = session.getOTPDetails();

                        session.resentotpsave(s);

                        onetimepassword = rotp.get(SessionManagement.OTP);

                        countdown();

                        imagetimer.setVisibility(View.VISIBLE);

                        timer.setVisibility(View.VISIBLE);

                        resend.setVisibility(View.GONE);

                    } else {

                        Toast.makeText(OTP.this, "OTP Sent!", Toast.LENGTH_LONG).show();

                    }


                }

            }


            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("mobile", params[0]);
                data.put("reg", params[1]);
                data.put("phone", params[2]);


                String result = ruc.sendPostRequest(REQUEST_OTP, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(mobile, reg, phone);
    }


    private void verified(final String mobile, final String registrationnumber, final String firebase) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = new ProgressDialog(OTP.this, R.style.MyTheme);
                loading.setCancelable(false);
                loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                loading.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equalsIgnoreCase("")) {

                    Toast.makeText(OTP.this, "Network Overload. Try Again Later!", Toast.LENGTH_LONG).show();

                    return;

                }

                if (s.contains("Mobile Verified Successfully!")) {

                    Toast.makeText(OTP.this, "Mobile Verified Successfully!", Toast.LENGTH_LONG).show();

                    session.createDashboardSession();

                    Intent back = new Intent(OTP.this, Dashboard.class);

                    finish();

                    startActivity(back);

                    return;

                } else {

                    Toast.makeText(OTP.this, s, Toast.LENGTH_LONG).show();

                    return;

                }

            }


            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("mobile", params[0]);
                data.put("registrationnumber", params[1]);
                data.put("firebase", params[2]);

                String result = ruc.sendPostRequest(REQUEST_VERIFIED, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();

        ru.execute(mobile, registrationnumber, firebase);

    }


    @Override

    public void onClick(View go) {

        if (go == submit) {

            check("submit");

        }

        if (go == resend) {

            check("resend");

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                Intent back = new Intent(OTP.this, PhoneNumber.class);

                finish();

                startActivity(back);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }


}


