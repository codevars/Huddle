package com.srmvdp.huddle.Authentication;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.R;

import java.util.HashMap;

public class ForgotOTP extends AppCompatActivity implements View.OnClickListener {

    private String countrycode;

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

    static EditText otpfieldone;

    static EditText otpfieldtwo;

    static EditText otpfieldthree;

    static EditText otpfieldfour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_otp);

        session = new SessionManagement(getApplicationContext());

        timer = (TextView) findViewById(R.id.timer);

        imagetimer = (ImageView) findViewById(R.id.imagetimer);

        number = (TextView) findViewById(R.id.number);

        resend = (TextView) findViewById(R.id.resend);

        resend.setVisibility(View.GONE);

        resend.setOnClickListener(this);

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(this);

        submit.setEnabled(false);

        hideStatusBar();

        textWatcher();

        countdown();

    }


    public boolean isOnline(final Context context) {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


    public void check(String prompt) {

        if (isOnline(this)) {

            if (prompt.equalsIgnoreCase("submit")) {

                verification();

            }

            else if (prompt.equalsIgnoreCase("resend")) {

                requestparamater();

            }

        } else {

            Toast.makeText(this, "No Connection Found!", Toast.LENGTH_SHORT).show();

        }


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


    private void hideStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


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

                resend.setText(getResources(getString(R.string)));

            }

        }.start();

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

    @Override
    public void onClick(View view) {

        if (view == submit) {

            check("submit");

        }

        if (view == resend) {

            check("resend");

        }

    }

}
