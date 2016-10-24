package com.srmvdp.huddle.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.srmvdp.huddle.Dashboard;
import com.srmvdp.huddle.Firebase.MyFirebaseInstanceIDService;
import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.PhoneNumber;
import com.srmvdp.huddle.R;
import com.srmvdp.huddle.Server.RegisterUserClass;
import java.util.HashMap;

public class LoginFragment extends Fragment {

    private static final String LOGIN_URL = "http://codevars.esy.es/login.php";

    private View view;

    private EditText registrationnumber;

    private EditText password;

    private Animation slide1;

    private Animation slide2;

    private SeekBar swipetologin;

    private TextView login;

    private SessionManagement session;

    public LoginFragment() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, container, false);

        Typeface one = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quando-Regular.ttf");

        session = new SessionManagement(getContext());

        registrationnumber = (EditText) view.findViewById(R.id.registrationnumber);

        password = (EditText) view.findViewById(R.id.password);

        swipetologin = (SeekBar) view.findViewById(R.id.swipetologin);

        login = (TextView) view.findViewById(R.id.logintext);

        login.setTypeface(one);

        left();

        arrowslide();

        seekdetect();

        return view;

    }



    private void left() {

        slide1 = new TranslateAnimation(800,0,0,0);

        slide1.setDuration(800);

        registrationnumber.setAnimation(slide1);

        password.setAnimation(slide1);

    }



    private void arrowslide() {

        slide2 = new TranslateAnimation(0,100,0,0);

        slide2.setDuration(1000);

        slide2.setFillAfter(true);

        slide2.setRepeatCount(-1);

        slide2.setRepeatMode(Animation.REVERSE);

        swipetologin.setAnimation(slide2);

    }



    public boolean isOnline(final Context context) {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }




    public void check() {

        if (isOnline(getContext()))
        {

            login();

        }

        else {

            Snackbar snackbar = Snackbar
                    .make(getView(), "No Connection Found!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            check();
                        }
                    });

            snackbar.show();

        }


    }



    private void emptycheck() {

        if (registrationnumber.getText().toString().matches("")) {

            Snackbar snackbar = Snackbar.make(getView(), "Please Enter Your Registration Number!", Snackbar.LENGTH_SHORT);

            snackbar.show();

            swipetologin.setProgress(0);

            slide2.start();

            return;

        }


        if (password.getText().toString().matches("")) {

            Snackbar snackbar = Snackbar.make(getView(), "Please Enter Your Password!", Snackbar.LENGTH_SHORT);

            snackbar.show();

            swipetologin.setProgress(0);

            slide2.start();

            return;

        }

        else {

            check();

        }

    }



    private void login(){

        String username = registrationnumber.getText().toString().trim();

        String pass  = password.getText().toString().trim();

        userLogin(username,pass);
    }


    private void userLogin(final String username, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = new ProgressDialog(getActivity(),R.style.MyTheme);
                loading.setCancelable(false);
                loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                loading.show();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();


                if(s.equals("Student")){

                    Snackbar snackbar = Snackbar.make(getView(), "Successfully Logged In!", Snackbar.LENGTH_SHORT);

                    snackbar.show();

                    session.createVerifiedDashboardSession(username, s);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i=new Intent(getContext(), Dashboard.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }, 1000);

                    return;

                }



                if(s.equalsIgnoreCase("Admin")){

                    Snackbar snackbar = Snackbar.make(getView(), "Successfully Logged In!", Snackbar.LENGTH_SHORT);

                    snackbar.show();

                    session.createVerifiedDashboardSession(username, s);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i=new Intent(getContext(), Dashboard.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }, 1000);

                    return;

                }



                if(s.equalsIgnoreCase("Teacher")){

                    Snackbar snackbar = Snackbar.make(getView(), "Successfully Logged In!", Snackbar.LENGTH_SHORT);

                    snackbar.show();

                    session.createVerifiedDashboardSession(username, s);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i=new Intent(getContext(), Dashboard.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }, 1000);

                    return;

                }



                if(s.equalsIgnoreCase("uAdmin")){

                    Snackbar snackbar = Snackbar.make(getView(), "Successfully Logged In!", Snackbar.LENGTH_SHORT);

                    snackbar.show();

                    session.createLoginSession(username, "Admin");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i=new Intent(getContext(), PhoneNumber.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }, 1000);

                    return;

                }



                if(s.equalsIgnoreCase("uStudent")){

                    Snackbar snackbar = Snackbar.make(getView(), "Successfully Logged In!", Snackbar.LENGTH_SHORT);

                    snackbar.show();

                    session.createLoginSession(username, "Student");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i=new Intent(getContext(), PhoneNumber.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }, 1000);

                    return;

                }



                if(s.equalsIgnoreCase("uTeacher")){

                    Snackbar snackbar = Snackbar.make(getView(), "Successfully Logged In!", Snackbar.LENGTH_SHORT);

                    snackbar.show();

                    session.createLoginSession(username, "Teacher");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent i=new Intent(getContext(), PhoneNumber.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }, 1000);

                    return;

                }



                if(s.equalsIgnoreCase("")){

                    Snackbar snackbar = Snackbar.make(getView(), "Loading... Try Again!", Snackbar.LENGTH_SHORT);

                    snackbar.show();

                    return;

                }



                else{

                    Snackbar snackbar = Snackbar.make(getView(), s, Snackbar.LENGTH_SHORT);

                    snackbar.show();

                    swipetologin.setProgress(0);

                    slide2.start();

                    return;



                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("registrationnumber",params[0]);
                data.put("password",params[1]);

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(LOGIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(username,password);

    }



    private void seekdetect() {

        swipetologin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (seekBar.getProgress() > 95) {

                    emptycheck();


                } else {

                    swipetologin.setProgress(0);

                    slide2.start();
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                slide2.cancel();

            }


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(progress>95){


                }

                else {

                    swipetologin.setAnimation(slide2);

                }

            }

        });


    }

}
