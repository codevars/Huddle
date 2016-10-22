package com.srmvdp.huddle.Firebase;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.srmvdp.huddle.LocalStorage.SessionManagement;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    private SessionManagement session;

    @Override
    public void onTokenRefresh() {

        session = new SessionManagement(getApplicationContext());

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        session.firebaseToken(refreshedToken);

        Log.d(TAG, "Refreshed Token: " + refreshedToken);

    }

}