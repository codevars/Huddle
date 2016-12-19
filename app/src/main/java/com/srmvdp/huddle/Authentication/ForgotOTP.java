package com.srmvdp.huddle.Authentication;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.R;

import java.util.HashMap;

public class ForgotOTP extends AppCompatActivity implements View.OnClickListener {

    private String otphead;

    private String onetimepassword;

    private String first;

    private String second;

    private String third;

    private String four;

    private String otp;

    private String mobile;

    private LinearLayout back;

    private TextView number;

    private TextView timer;

    private TextView resend;

    private TextView senthead;

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

        HashMap<String, String> otpheader = session.getOTPHeaderDetails();

        HashMap<String, String> mob = session.getForgotMobileDetails();

        otphead = otpheader.get(SessionManagement.OTP_HEADER);

        mobile = mob.get(SessionManagement.FORGOT_MOBILE);

        timer = (TextView) findViewById(R.id.timer);

        senthead = (TextView) findViewById(R.id.sent);

        imagetimer = (ImageView) findViewById(R.id.imagetimer);

        number = (TextView) findViewById(R.id.number);

        resend = (TextView) findViewById(R.id.resend);

        submit = (Button) findViewById(R.id.submit);

        back = (LinearLayout) findViewById(R.id.gobackcontainer);

        otpfieldone = (EditText) findViewById(R.id.o1);

        otpfieldtwo = (EditText) findViewById(R.id.o2);

        otpfieldthree = (EditText) findViewById(R.id.o3);

        otpfieldfour = (EditText) findViewById(R.id.o4);

        otpfieldtwo.setEnabled(false);

        otpfieldthree.setEnabled(false);

        otpfieldfour.setEnabled(false);

        resend.setVisibility(View.GONE);

        resend.setOnClickListener(this);

        submit.setOnClickListener(this);

        back.setOnClickListener(this);

        submit.setEnabled(false);

        hideStatusBar();

        setHeader();

        slideup();

        textWatcher();

        countdown();

    }


    public boolean isOnline(final Context context) {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


    public void setHeader() {

        senthead.setText(otphead);

        if (mobile.contains("@")) {

            number.setTextSize(20);

            number.setText(mobile);

        } else {

            number.setText(mobile);

        }

    }


    public void check(String prompt) {

        if (isOnline(this)) {

            if (prompt.equalsIgnoreCase("submit")) {

                verification();

            } else if (prompt.equalsIgnoreCase("resend")) {

                Intent go = new Intent(ForgotOTP.this, ForgotPassword.class);

                startActivity(go);

                finish();

            } else if (prompt.equalsIgnoreCase("back")) {

                Intent go = new Intent(ForgotOTP.this, LoginRegisterTabbed.class);

                session.createForgotCancelSession();

                startActivity(go);

                finish();

            }

        } else {

            Toast.makeText(this, "No Connection Found!", Toast.LENGTH_SHORT).show();

        }


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

            Toast.makeText(this, "OTP Verified!", Toast.LENGTH_SHORT).show();

            Intent go = new Intent(ForgotOTP.this, ChangePassword.class);

            session.createChangePasswordSession();

            startActivity(go);

            finish();


        } else {

            Toast.makeText(this, "Incorrect OTP!", Toast.LENGTH_SHORT).show();

        }

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

                resend.setText("Didn't Get OTP? Try Again.");

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

                }

                if (otpfieldfour.getText().toString().length() == 1) {

                    submit.setEnabled(true);

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

        if (view == back) {

            check("back");

        }

    }

}
