package com.srmvdp.huddle.LocalStorage;

import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.srmvdp.huddle.Dashboard;
import com.srmvdp.huddle.LoginRegisterTabbed;
import com.srmvdp.huddle.MainActivity;
import com.srmvdp.huddle.OTP;
import com.srmvdp.huddle.PhoneNumber;
import com.srmvdp.huddle.app.AppController;

public class SessionManagement {

    SharedPreferences pref;

    Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "CodeVarsPref";

    public static final String PHONE = "New";

    public static final String ONETIMEPASSWORD = "No";

    public static final String DASHBOARD = "Nah";

    public static final String REG_NUM = "regnum";

    public static final String MOB_NUM = "mobnum";

    public static final String OTP = "OTP";

    public static final String TOKEN = "TOKEN";



    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public void createLoginSession(String regnum){

        editor.putBoolean(PHONE, true);

        editor.putString(REG_NUM, regnum);

        editor.commit();

    }



    public void createNumberSession(String mobile, String otp){

        editor.putString(MOB_NUM, mobile);

        editor.putString(OTP, otp);

        editor.putBoolean(PHONE, false);

        editor.putBoolean(ONETIMEPASSWORD, true);

        editor.commit();

    }



    public void createDashboardSession(){

        editor.putBoolean(ONETIMEPASSWORD, false);

        editor.putBoolean(DASHBOARD, true);

        editor.commit();

    }



    public void phone(){

        if(this.phoneIn()){

            Intent i = new Intent(_context, PhoneNumber.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

        }


    }



    public void otp(){

        if(this.otpIn()){

            Intent i = new Intent(_context, OTP.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

        }


    }



    public void dashboard(){

        if(this.dashboardIn()){

            Intent i = new Intent(_context, Dashboard.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

        }


    }



    public void resentotpsave(String otp) {

        editor.putString(OTP, otp);

        editor.commit();

    }



    public void firebaseToken(String token) {

        editor.putString(TOKEN, token);

        editor.commit();

    }



    public void logoutUser(){

        editor.clear();

        editor.commit();

        Intent i = new Intent(_context, LoginRegisterTabbed.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);

    }



    public HashMap<String, String> getRegistrationDetails(){

        HashMap<String, String> reg = new HashMap<String, String>();

        reg.put(REG_NUM, pref.getString(REG_NUM, null));

        return reg;

    }



    public HashMap<String, String> getMobileDetails(){

        HashMap<String, String> mobile = new HashMap<String, String>();

        mobile.put(MOB_NUM, pref.getString(MOB_NUM, null));

        return mobile;

    }



    public HashMap<String, String> getOTPDetails(){

        HashMap<String, String> otp = new HashMap<String, String>();

        otp.put(OTP, pref.getString(OTP, null));

        return otp;

    }



    public HashMap<String, String> getFirebaseTokenDetails() {

        HashMap<String, String> token = new HashMap<>();

        token.put(TOKEN, pref.getString(TOKEN, null));

        return token;

    }



    public boolean phoneIn() {return pref.getBoolean(PHONE, false);}

    public boolean otpIn(){
        return pref.getBoolean(ONETIMEPASSWORD, false);
    }

    public boolean dashboardIn(){
        return pref.getBoolean(DASHBOARD, false);
    }



}