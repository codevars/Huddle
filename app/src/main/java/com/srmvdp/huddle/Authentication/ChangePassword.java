package com.srmvdp.huddle.Authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.R;
import com.srmvdp.huddle.Server.RegisterUserClass;

import java.math.BigInteger;
import java.util.HashMap;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    private static final String REQUEST_CHANGE = "http://codevars.esy.es/changepassword.php";

    private Animation buttonup;

    private Button submit;

    private EditText password;

    private EditText passwordconfirm;

    private String passone;

    private String passtwo;

    private String ip;

    private String reg;

    private LinearLayout back;

    private SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        session = new SessionManagement(getApplicationContext());

        HashMap<String, String> register = session.getRegistrationDetails();

        submit = (Button) findViewById(R.id.submit);

        password = (EditText) findViewById(R.id.password);

        passwordconfirm = (EditText) findViewById(R.id.passwordconfirm);

        reg = register.get(SessionManagement.REG_NUM);

        back = (LinearLayout) findViewById(R.id.gobackcontainer);

        submit.setOnClickListener(this);

        back.setOnClickListener(this);

        slideup();

    }


    public boolean isOnline() {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


    private void slideup() {

        buttonup = new TranslateAnimation(0, 0, 500, 0);

        buttonup.setDuration(1000);

        submit.setAnimation(buttonup);

    }


    private void check() {

        if (isOnline()) {

            verify();

        } else {

            Toast.makeText(this, "No Connection Found!", Toast.LENGTH_SHORT).show();

        }

    }


    private void verify() {

        passone = password.getText().toString().trim();

        passtwo = passwordconfirm.getText().toString().trim();

        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);

        ip = BigInteger.valueOf(wm.getDhcpInfo().netmask).toString();

        if (passone.equals(passtwo)) {

            if (passone.length() >= 6) {

                changePassword(reg, ip, passone);

            }

            else {

                Toast.makeText(this, "Password Must Be More Than 6 Characters!", Toast.LENGTH_SHORT).show();

            }

        } else {

            Toast.makeText(this, "Passwords Do Not Match!", Toast.LENGTH_SHORT).show();

        }

    }


    private void changePassword(final String reg, String ip, String password) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = new ProgressDialog(ChangePassword.this, R.style.MyTheme);
                loading.setCancelable(false);
                loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                loading.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if (s.equalsIgnoreCase("")) {

                    Toast.makeText(ChangePassword.this, "Please Try Again Later!", Toast.LENGTH_LONG).show();

                } else if (s.equalsIgnoreCase("Password Changed Successfully!")) {

                    Toast.makeText(ChangePassword.this, s, Toast.LENGTH_LONG).show();

                    Intent go = new Intent(ChangePassword.this, LoginRegisterTabbed.class);

                    session.createForgotCancelSession();

                    startActivity(go);

                    finish();

                } else {

                    Toast.makeText(ChangePassword.this, s, Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();
                data.put("registrationnumber", params[0]);
                data.put("ip", params[1]);
                data.put("password", params[2]);

                String result = ruc.sendPostRequest(REQUEST_CHANGE, data);

                return result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(reg, ip, passone);

    }


    @Override
    public void onClick(View view) {

        if (view == submit) {

            check();

        }

        if (view == back) {

            Intent go = new Intent(ChangePassword.this, LoginRegisterTabbed.class);

            session.createForgotCancelSession();

            startActivity(go);

            finish();

        }

    }


}
