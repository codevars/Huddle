package com.srmvdp.huddle.Authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.R;
import com.srmvdp.huddle.Server.RegisterUserClass;
import java.util.HashMap;

public class RegisterFragment extends Fragment {

    private static final String REGISTER_URL = "http://codevars.esy.es/register.php";

    private EditText fullname;

    private EditText srmemail;

    private EditText pass;

    private EditText registrationnumber;

    private Animation slide1;

    private Animation slide2;

    private SeekBar swipetoregister;

    private TextView register;

    private String firebasetoken;

    private SessionManagement session;

    public RegisterFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final FirebaseAuth auth;

        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        Typeface one = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Quando-Regular.ttf");

        session = new SessionManagement(getContext());

        HashMap<String, String> token = session.getFirebaseTokenDetails();

        firebasetoken = token.get(SessionManagement.TOKEN);

        fullname = (EditText) view.findViewById(R.id.fullname);

        srmemail = (EditText) view.findViewById(R.id.email);

        pass = (EditText) view.findViewById(R.id.password);

        registrationnumber = (EditText) view.findViewById(R.id.registrationnumber);

        swipetoregister = (SeekBar) view.findViewById(R.id.swipetoregister);

        register = (TextView) view.findViewById(R.id.registertext);

        register.setTypeface(one);


        left();

        arrowslide();

        seekdetect();

        return view;
    }



    private void left() {

        slide1 = new TranslateAnimation(800,0,0,0);

        slide1.setDuration(800);

        fullname.setAnimation(slide1);

        srmemail.setAnimation(slide1);

        pass.setAnimation(slide1);

        registrationnumber.setAnimation(slide1);

    }



    private void arrowslide() {

        slide2 = new TranslateAnimation(0,100,0,0);

        slide2.setDuration(1000);

        slide2.setFillAfter(true);

        slide2.setRepeatCount(-1);

        slide2.setRepeatMode(Animation.REVERSE);

        swipetoregister.setAnimation(slide2);

    }


    public boolean isOnline(final Context context) {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }



    private void emptycheck() {

        if (fullname.getText().toString().matches("")) {

            Snackbar snackbar = Snackbar.make(getView(), "Please Enter Your Full Name!", Snackbar.LENGTH_SHORT);

            snackbar.show();

            swipetoregister.setProgress(0);

            slide2.start();

            return;

        }

        if (srmemail.getText().toString().matches("")) {

            Snackbar snackbar = Snackbar.make(getView(), "Please Enter Your SRM Email!", Snackbar.LENGTH_SHORT);

            snackbar.show();

            swipetoregister.setProgress(0);

            slide2.start();

            return;

        }

        if (pass.getText().toString().matches("")) {

            Snackbar snackbar = Snackbar.make(getView(), "Please enter your password!", Snackbar.LENGTH_SHORT);

            snackbar.show();

            swipetoregister.setProgress(0);

            slide2.start();

            return;

        }

        if (registrationnumber.getText().toString().matches("")) {

            Snackbar snackbar = Snackbar.make(getView(), "Please enter your Registration number!", Snackbar.LENGTH_SHORT);

            snackbar.show();

            swipetoregister.setProgress(0);

            slide2.start();

            return;

        }

        if (firebasetoken.matches("")) {
            Snackbar snackbar = Snackbar.make(getView(), "Internal error occured! Restart the app.", Snackbar.LENGTH_SHORT);

            snackbar.show();

            swipetoregister.setProgress(0);

            slide2.start();

            return;

        }

        else {

            check();

        }

    }



    public void check() {

        if (isOnline(getContext()))
        {

            registerUser();


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



    private void registerUser() {

        String name = fullname.getText().toString();

        String email = srmemail.getText().toString();

        String password = pass.getText().toString();

        String reg = registrationnumber.getText().toString();

        register(name,email,password,reg, firebasetoken);

    }



    private void register(final String name, final String email, final String password, String mobile, String firebasetoken) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


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


                if(s.equalsIgnoreCase("")){

                    Snackbar snackbar = Snackbar.make(getView(), "Loading... Try Again!", Snackbar.LENGTH_SHORT);

                    snackbar.show();

                }

                if(s.equalsIgnoreCase("Successfully Registered!")){

                    Snackbar snackbar = Snackbar.make(getView(), s, Snackbar.LENGTH_SHORT);

                    snackbar.show();

                    fullname.getText().clear();

                    srmemail.getText().clear();

                    pass.getText().clear();

                    registrationnumber.getText().clear();

                    TabLayout host = (TabLayout) getActivity().findViewById(R.id.tablayout);

                    TabLayout.Tab tab = host.getTabAt(0);

                    tab.select();


                }

                else {

                    Snackbar snackbar = Snackbar
                            .make(getView(), s, Snackbar.LENGTH_SHORT);

                    snackbar.show();

                }

            }


            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("fullname",params[0]);
                data.put("srmemail",params[1]);
                data.put("password",params[2]);
                data.put("registrationnumber",params[3]);
                data.put("firebasetoken",params[4]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,email,password,mobile,firebasetoken);

    }



    private void seekdetect() {

        swipetoregister.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (seekBar.getProgress() > 95) {

                    emptycheck();


                } else {

                    swipetoregister.setProgress(0);

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

                    swipetoregister.setAnimation(slide2);

                }

            }

        });



    }

}
