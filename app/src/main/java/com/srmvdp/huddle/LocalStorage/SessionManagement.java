package com.srmvdp.huddle.LocalStorage;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.firebase.iid.FirebaseInstanceId;
import com.srmvdp.huddle.AdminPanel.AdminPanel;
import com.srmvdp.huddle.Authentication.ChangePassword;
import com.srmvdp.huddle.Authentication.ForgotOTP;
import com.srmvdp.huddle.Authentication.ForgotPassword;
import com.srmvdp.huddle.Dashboard;
import com.srmvdp.huddle.Extras.AdminPanelIntro;
import com.srmvdp.huddle.Authentication.LoginRegisterTabbed;
import com.srmvdp.huddle.Authentication.OTP;
import com.srmvdp.huddle.Authentication.PhoneNumber;

public class SessionManagement {

    SharedPreferences pref;

    Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    public static final String PREF_NAME = "CodeVarsPref";

    public static final String PHONE = "New";

    public static final String FORGOTPASSWORD = "Forgot";

    public static final String CHANGEPASSWORD = "Change";

    public static final String ONETIMEPASSWORD = "No";

    public static final String DASHBOARD = "Nah";

    public static final String PANEL = "Nuh";

    public static final String FULLNAME = "fullname";

    public static final String DEPARTMENT = "department";

    public static final String SECTION = "section";

    public static final String REG_NUM = "regnum";

    public static final String MOB_NUM = "mobnum";

    public static final String OTP = "OTP";

    public static final String OTP_HEADER = "Otp Header";

    public static final String FORGOT_MOBILE = "ForgotOTP";

    public static final String TOKEN = "TOKEN";

    public static final String PRIVILEGE = "PRIVILEGE";

    public static final String USERPROFILE = "USERPROFILE";

    public static final String PROFILEPIC = "PROFILEPIC";


    public SessionManagement(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String regnum, String privilege, String department, String section) {

        editor.putBoolean(PHONE, true);

        editor.putString(REG_NUM, regnum);

        editor.putString(PRIVILEGE, privilege);

        editor.putString(DEPARTMENT, department);

        editor.putString(SECTION, section);

        editor.commit();

    }


    public void createLoginCancelSession() {

        editor.putBoolean(PHONE, false);

        editor.putBoolean(ONETIMEPASSWORD, false);

        editor.commit();

    }


    public void createForgotSession(String regnum, String mobile, String otp, String header) {

        editor.putString(REG_NUM, regnum);

        editor.putString(OTP, otp);

        editor.putString(OTP_HEADER, header);

        editor.putString(FORGOT_MOBILE, mobile);

        editor.putBoolean(FORGOTPASSWORD, true);

        editor.commit();

    }


    public void createForgotCancelSession() {

        editor.putBoolean(FORGOTPASSWORD, false);

        editor.putBoolean(CHANGEPASSWORD, false);

        editor.commit();

    }


    public void createChangePasswordSession() {

        editor.putBoolean(FORGOT_MOBILE, false);

        editor.putBoolean(CHANGEPASSWORD, true);

        editor.commit();

    }


    public void createNumberSession(String mobile, String otp) {

        editor.putString(MOB_NUM, mobile);

        editor.putString(OTP, otp);

        editor.putBoolean(PHONE, false);

        editor.putBoolean(ONETIMEPASSWORD, true);

        editor.commit();

    }


    public void createDashboardSession() {

        editor.putBoolean(ONETIMEPASSWORD, false);

        editor.putBoolean(DASHBOARD, true);

        editor.commit();

    }


    public void createVerifiedDashboardSession(String regnum, String privilege, String department, String section) {

        editor.putBoolean(PHONE, false);

        editor.putBoolean(ONETIMEPASSWORD, false);

        editor.putString(REG_NUM, regnum);

        editor.putString(PRIVILEGE, privilege);

        editor.putString(SECTION, section);

        editor.putString(DEPARTMENT, department);

        editor.putBoolean(DASHBOARD, true);

        editor.commit();

    }


    public void createAdminPanelSession() {

        editor.putBoolean(PANEL, true);

        editor.commit();

    }


    public void createUserProfileSession() {

        editor.putBoolean(USERPROFILE, true);

        editor.commit();

    }


    public void createUserProfile(String fullname) {

        editor.putString(FULLNAME, fullname);

        editor.commit();

    }


    public void createProfilePicSession() {

        editor.putBoolean(PROFILEPIC, true);

        editor.commit();

    }


    public void createUpdatedSectionSession(String section) {

        editor.putString(SECTION, section);

        editor.commit();

    }


    public void phone() {

        if (this.phoneIn()) {

            Intent i = new Intent(_context, PhoneNumber.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        }


    }


    public void changepassword() {

        if (this.changeIn()) {

            Intent i = new Intent(_context, ChangePassword.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        }


    }


    public void forgotpassword() {

        if (this.forgotIn()) {

            Intent i = new Intent(_context, ForgotOTP.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        }


    }


    public void otp() {

        if (this.otpIn()) {

            Intent i = new Intent(_context, OTP.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        }


    }


    public void dashboard() {

        if (this.dashboardIn()) {

            Intent i = new Intent(_context, Dashboard.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        }


    }


    public void adminPanel() {

        if (this.adminpanelIn()) {

            Intent i = new Intent(_context, AdminPanel.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

        } else {

            Intent i = new Intent(_context, AdminPanelIntro.class);

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


    public void logoutUser() {

        editor.clear();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        firebaseToken(refreshedToken);

        editor.commit();

        Intent i = new Intent(_context, LoginRegisterTabbed.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);


    }


    public HashMap<String, String> getRegistrationDetails() {

        HashMap<String, String> reg = new HashMap<String, String>();

        reg.put(REG_NUM, pref.getString(REG_NUM, null));

        return reg;

    }


    public HashMap<String, String> getMobileDetails() {

        HashMap<String, String> mobile = new HashMap<String, String>();

        mobile.put(MOB_NUM, pref.getString(MOB_NUM, null));

        return mobile;

    }


    public HashMap<String, String> getDepartmentDetails() {

        HashMap<String, String> dept = new HashMap<String, String>();

        dept.put(DEPARTMENT, pref.getString(DEPARTMENT, null));

        return dept;

    }


    public HashMap<String, String> getSectionDetails() {

        HashMap<String, String> sec = new HashMap<String, String>();

        sec.put(SECTION, pref.getString(SECTION, null));

        return sec;

    }


    public HashMap<String, String> getOTPDetails() {

        HashMap<String, String> otpheader = new HashMap<String, String>();

        otpheader.put(OTP, pref.getString(OTP, null));

        return otpheader;

    }


    public HashMap<String, String> getOTPHeaderDetails() {

        HashMap<String, String> otp = new HashMap<String, String>();

        otp.put(OTP_HEADER, pref.getString(OTP_HEADER, null));

        return otp;

    }


    public HashMap<String, String> getForgotMobileDetails() {

        HashMap<String, String> mobile = new HashMap<String, String>();

        mobile.put(FORGOT_MOBILE, pref.getString(FORGOT_MOBILE, null));

        return mobile;

    }


    public HashMap<String, String> getFirebaseTokenDetails() {

        HashMap<String, String> token = new HashMap<>();

        token.put(TOKEN, pref.getString(TOKEN, null));

        return token;

    }


    public HashMap<String, String> getPrivilegeDetails() {

        HashMap<String, String> privilege = new HashMap<>();

        privilege.put(PRIVILEGE, pref.getString(PRIVILEGE, null));

        return privilege;

    }


    public HashMap<String, String> getUserProfileDetails() {

        HashMap<String, String> profile = new HashMap<>();

        profile.put(FULLNAME, pref.getString(FULLNAME, null));

        return profile;


    }

    public boolean forgotIn() {
        return pref.getBoolean(FORGOTPASSWORD, false);
    }

    public boolean changeIn() {
        return pref.getBoolean(CHANGEPASSWORD, false);
    }

    public boolean phoneIn() {
        return pref.getBoolean(PHONE, false);
    }

    public boolean otpIn() {
        return pref.getBoolean(ONETIMEPASSWORD, false);
    }

    public boolean dashboardIn() {
        return pref.getBoolean(DASHBOARD, false);
    }

    public boolean adminpanelIn() {
        return pref.getBoolean(PANEL, false);
    }

    public boolean userProfileIn() {
        return pref.getBoolean(USERPROFILE, false);
    }

    public boolean hasProfilePic() {
        return pref.getBoolean(PROFILEPIC, false);
    }

}