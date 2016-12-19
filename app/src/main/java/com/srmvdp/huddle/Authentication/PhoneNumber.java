package com.srmvdp.huddle.Authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.R;
import com.srmvdp.huddle.Server.RegisterUserClass;

import java.util.HashMap;

public class PhoneNumber extends AppCompatActivity implements  View.OnClickListener{

    private static final String REQUEST_OTP = "http://codevars.esy.es/requestotp.php";

    private TextView warning;

    private EditText pone;

    private EditText ptwo;

    private EditText pthree;

    private EditText pfour;

    private EditText pfive;

    private EditText psix;

    private EditText pseven;

    private EditText peight;

    private EditText pnine;

    private EditText pten;

    private Button submit;

    private String first;

    private String second;

    private String third;

    private String fourth;

    private String fifth;

    private String sixth;

    private String seventh;

    private String eighth;

    private String ninth;

    private String tenth;

    private String countrycode;

    private String mobile;

    private String registrationnumber;

    private String finalmobile;

    private String otp;

    private SessionManagement session;

    private Animation buttonup;

    private ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Typeface one = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");

        setContentView(R.layout.activity_phone_number);

        session = new SessionManagement(getApplicationContext());

        HashMap<String, String> regnum = session.getRegistrationDetails();

        registrationnumber = regnum.get(SessionManagement.REG_NUM);

        bar = getSupportActionBar();

        bar.setHomeButtonEnabled(true);

        bar.setDisplayHomeAsUpEnabled(true);

        warning = (TextView) findViewById(R.id.warning);

        pone = (EditText) findViewById(R.id.p1);

        ptwo = (EditText) findViewById(R.id.p2);

        pthree = (EditText) findViewById(R.id.p3);

        pfour = (EditText) findViewById(R.id.p4);

        pfive = (EditText) findViewById(R.id.p5);

        psix = (EditText) findViewById(R.id.p6);

        pseven = (EditText) findViewById(R.id.p7);

        peight = (EditText) findViewById(R.id.p8);

        pnine = (EditText) findViewById(R.id.p9);

        pten = (EditText) findViewById(R.id.p10);

        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(this);

        ptwo.setEnabled(false);

        pthree.setEnabled(false);

        pfour.setEnabled(false);

        pfive.setEnabled(false);

        psix.setEnabled(false);

        pseven.setEnabled(false);

        peight.setEnabled(false);

        pnine.setEnabled(false);

        pten.setEnabled(false);

        submit.setEnabled(false);

        submit.setBackgroundColor(Color.parseColor("#992b2b2b"));

        warning.setTypeface(one);

        textWatcher();

        slideup();

    }


    public boolean isOnline(final Context context) {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


    public void check() {

        if (isOnline(this))
        {

            send();

        }

        else {

            Toast.makeText(this, "No Connection Found!", Toast.LENGTH_SHORT).show();

        }


    }


    private void textWatcher() {

        pone.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {

                if(pone.getText().toString().length()==1) {

                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {

                if(pone.getText().toString().length()==2) {

                    String o = pone.getText().toString();

                    ptwo.setText(o.substring(1));

                    ptwo.setEnabled(true);

                    ptwo.setSelection(ptwo.getText().length());

                    ptwo.requestFocus();

                    String text = pone.getText().toString();

                    pone.setText(text.substring(0, text.length() - 1));

                    first = pone.getText().toString();

                }

            }

        });

        ptwo.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {

                if(ptwo.getText().toString().length()==0) {

                    pone.setSelection(pone.getText().length());

                    pone.requestFocus();

                    ptwo.setEnabled(false);

                }

                if(ptwo.getText().toString().length()==2) {

                    String t = ptwo.getText().toString();

                    pthree.setText(t.substring(1));

                    pthree.setEnabled(true);

                    pthree.setSelection(pthree.getText().length());

                    pthree.requestFocus();

                    String lol = ptwo.getText().toString();

                    ptwo.setText(lol.substring(0, lol.length() - 1));

                    second = ptwo.getText().toString();

                }

            }

        });

        pthree.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {

                if(pthree.getText().toString().length()==0) {

                    ptwo.setSelection(ptwo.getText().length());

                    ptwo.requestFocus();

                    pthree.setEnabled(false);

                }

                if(pthree.getText().toString().length()==2) {

                    String t = pthree.getText().toString();

                    pfour.setText(t.substring(1));

                    pfour.setEnabled(true);

                    pfour.setSelection(pfour.getText().length());

                    pfour.requestFocus();

                    String text = pthree.getText().toString();

                    pthree.setText(text.substring(0, text.length() - 1));

                    third = pthree.getText().toString();

                }

            }

        });

        pfour.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {

                if(pfour.getText().toString().length()==0) {

                    pthree.setSelection(pthree.getText().length());

                    pthree.requestFocus();

                    pfour.setEnabled(false);

                }

                if(pfour.getText().toString().length()==2) {

                    String f = pfour.getText().toString();

                    pfive.setText(f.substring(1));

                    pfive.setEnabled(true);

                    pfive.setSelection(pfive.getText().length());

                    pfive.requestFocus();

                    String text = pfour.getText().toString();

                    pfour.setText(text.substring(0, text.length() - 1));

                    fourth = pfour.getText().toString();

                }

            }

        });

        pfive.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {


                if(pfive.getText().toString().length()==0) {

                    pfour.setSelection(pfour.getText().length());

                    pfour.requestFocus();

                    pfive.setEnabled(false);

                }

                if(pfive.getText().toString().length()==2) {

                    String f = pfive.getText().toString();

                    psix.setText(f.substring(1));

                    psix.setEnabled(true);

                    psix.setSelection(psix.getText().length());

                    psix.requestFocus();

                    String text = pfive.getText().toString();

                    pfive.setText(text.substring(0, text.length() - 1));

                    fifth = pfive.getText().toString();

                }

            }

        });

        psix.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {

                if(psix.getText().toString().length()==0) {

                    pfive.setSelection(pfive.getText().length());

                    pfive.requestFocus();

                    psix.setEnabled(false);

                }

                if(psix.getText().toString().length()==2) {

                    String si = psix.getText().toString();

                    pseven.setText(si.substring(1));

                    pseven.setEnabled(true);

                    pseven.setSelection(pseven.getText().length());

                    pseven.requestFocus();

                    String text = psix.getText().toString();

                    psix.setText(text.substring(0, text.length() - 1));

                    sixth = psix.getText().toString();

                }

            }

        });

        pseven.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {

                if(pseven.getText().toString().length()==0) {

                    psix.setSelection(psix.getText().length());

                    psix.requestFocus();

                    pseven.setEnabled(false);

                }

                if(pseven.getText().toString().length()==2) {

                    String se = pseven.getText().toString();

                    peight.setText(se.substring(1));

                    peight.setEnabled(true);

                    peight.setSelection(peight.getText().length());

                    peight.requestFocus();

                    String text = pseven.getText().toString();

                    pseven.setText(text.substring(0, text.length() - 1));

                    seventh = pseven.getText().toString();

                }


            }

        });

        peight.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {

                if(peight.getText().toString().length()==0) {

                    pseven.setSelection(pseven.getText().length());

                    pseven.requestFocus();

                    peight.setEnabled(false);

                }

                if(peight.getText().toString().length()==2) {

                    String e = peight.getText().toString();

                    pnine.setText(e.substring(1));

                    pnine.setEnabled(true);

                    pnine.setSelection(pnine.getText().length());

                    pnine.requestFocus();

                    String text = peight.getText().toString();

                    peight.setText(text.substring(0, text.length() - 1));

                    eighth = peight.getText().toString();

                }

            }

        });

        pnine.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {

                if(pnine.getText().toString().length()==0) {

                    peight.setSelection(peight.getText().length());

                    peight.requestFocus();

                    pnine.setEnabled(false);

                }

                if(pnine.getText().toString().length()==2) {

                    String n = pnine.getText().toString();

                    pten.setText(n.substring(1));

                    pten.setEnabled(true);

                    pten.setSelection(pten.getText().length());

                    pten.requestFocus();

                    String text = pnine.getText().toString();

                    pnine.setText(text.substring(0, text.length() - 1));

                    ninth = pnine.getText().toString();

                }

            }

        });

        pten.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {

                if(pten.getText().toString().length()==0) {

                    pnine.setSelection(pnine.getText().length());

                    pnine.requestFocus();

                    pten.setEnabled(false);

                    submit.setEnabled(false);

                    submit.setBackgroundColor(Color.parseColor("#992b2b2b"));

                }

                if(pten.getText().toString().length()==1) {

                    tenth = pten.getText().toString();

                    submit.setEnabled(true);

                    submit.setBackgroundColor(Color.parseColor("#2b2b2b"));

                }


            }

        });


    }


    private void slideup() {

        buttonup = new TranslateAnimation(0,0,500,0);

        buttonup.setDuration(1000);

        submit.setAnimation(buttonup);

    }


    private void send() {

        countrycode = "91";

        mobile = countrycode+first+second+third+fourth+fifth+sixth+seventh+eighth+ninth+tenth;

        requestparamater();

    }


    public void requestparamater() {

        String mob = mobile;

        String reg = registrationnumber;

        String phone = mobile.substring(2);

        requestotp(mob, reg, phone);

    }



    private void requestotp(final String mobile, String reg, String phone) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = new ProgressDialog(PhoneNumber.this,R.style.MyTheme);
                loading.setCancelable(false);
                loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                loading.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if(s.equalsIgnoreCase("")){

                    Toast.makeText(PhoneNumber.this, "Please Try Again!", Toast.LENGTH_LONG).show();

                }


                else {

                    if (s.length() == 4) {

                        finalmobile = mobile.substring(2);

                        otp = s;

                        session.createNumberSession(finalmobile, otp);

                        Intent back = new Intent(PhoneNumber.this, OTP.class);

                        finish();

                        startActivity(back);

                    }

                    else {

                        Toast.makeText(PhoneNumber.this, s, Toast.LENGTH_LONG).show();

                    }


                }

            }


            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("mobile",params[0]);
                data.put("reg",params[1]);
                data.put("phone",params[2]);


                String result = ruc.sendPostRequest(REQUEST_OTP,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(mobile, reg, phone);
    }


    @Override
    public void onClick(View click) {

        if (click == submit) {

            check();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                session.createLoginCancelSession();

                Intent back = new Intent(PhoneNumber.this, LoginRegisterTabbed.class);

                finish();

                startActivity(back);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }

}
