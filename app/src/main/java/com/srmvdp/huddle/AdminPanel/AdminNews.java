package com.srmvdp.huddle.AdminPanel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.srmvdp.huddle.Extras.ConnectivityReceiver;
import com.srmvdp.huddle.LocalStorage.SessionManagement;
import com.srmvdp.huddle.News.AppController;
import com.srmvdp.huddle.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class AdminNews extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private final String UPLOAD_URL = "http://codevars.esy.es/upload.php";

    private ActionBar bar;

    private AlertDialog.Builder warning;

    private Animation slide;

    private LinearLayout previewcontainer;

    private TextInputLayout statusparent;

    private EditText titletext;

    private EditText statustext;

    private EditText urltext;

    private Button notification;

    private Button select;

    private Button post;

    private ImageView preview;

    private String title;

    private String name;

    private String image;

    private String status;

    private String profilepic;

    private String timestamp;

    private String url;

    private SessionManagement session;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String KEY_NAME = "name";

    private String KEY_IMAGE = "image";

    private String KEY_STATUS = "status";

    private String KEY_PROFILEPIC = "profilepic";

    private String KEY_TIMESTAMP = "timestamp";

    private String KEY_URL = "url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel_news);


        session = new SessionManagement(getApplicationContext());

        bar = getSupportActionBar();

        bar.setDisplayHomeAsUpEnabled(true);

        statustext = (EditText) findViewById(R.id.status);

        statusparent = (TextInputLayout) findViewById(R.id.statusparent);

        urltext = (EditText) findViewById(R.id.url);

        titletext = (EditText) findViewById(R.id.title2);

        select = (Button) findViewById(R.id.selectImages);

        post = (Button) findViewById(R.id.buttonpost);

        preview = (ImageView) findViewById(R.id.preview);

        previewcontainer = (LinearLayout) findViewById(R.id.previewcontainer);

        notification = (Button) findViewById(R.id.notification);

        select.setOnClickListener(this);

        post.setOnClickListener(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        previewcontainer.setVisibility(View.GONE);

        notification.setVisibility(View.GONE);

        initialInternetCheck();


    }


    public boolean isOnline() {

        final ConnectivityManager connectivityManager = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE));

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();

    }


    public void initialInternetCheck() {

        if (!isOnline()) {

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

            notification.setText(getResources().getString(R.string.notconnected));

        }

    }


    private void slide() {

        slide = new TranslateAnimation(0, 0, 500, 0);

        slide.setDuration(1000);

        notification.setAnimation(slide);

    }


    private void currentTimeStamp() {

        String timestampprefix = "930";

        Long time = System.currentTimeMillis() / 1000;

        String current = time.toString();

        timestamp = current + timestampprefix;

    }


    private void showFileChooser() {

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }


    private void validation() {

        if (statustext.getText().toString().trim().isEmpty()) {

            statusparent.setError("Please Enter Summary!");

            return;

        } else {

            statusparent.setErrorEnabled(false);

        }

        if (urltext.getText().toString().trim().isEmpty()) {

            alert("warning");

            return;

        }

        if (preview.getDrawable() == null) {

            alert("noimage");

            return;

        }

        uploadImage();


    }


    private void alert(String trigger) {

        DialogInterface.OnClickListener url = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case DialogInterface.BUTTON_POSITIVE:

                        if (preview.getDrawable() == null) {

                            alert("noimage");

                        } else {

                            uploadImage();

                        }

                        break;


                    case DialogInterface.BUTTON_NEGATIVE:

                        break;

                }

            }

        };


        DialogInterface.OnClickListener image = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case DialogInterface.BUTTON_POSITIVE:

                        uploadData();

                        break;


                    case DialogInterface.BUTTON_NEGATIVE:

                        break;

                }

            }

        };


        if (trigger.equalsIgnoreCase("warning")) {

            warning = new AlertDialog.Builder(this);

            warning.setMessage("No URL Is Entered. Do You Want To Continue?").setPositiveButton("Yes", url).setNegativeButton("No", url).show();

        }



        if (trigger.equalsIgnoreCase("noimage")) {

            warning = new AlertDialog.Builder(this);

            warning.setMessage("No Image Is Provided. Do You Want To Continue?").setPositiveButton("Yes", image).setNegativeButton("No", image).show();

        }


    }


    public String getStringImage(Bitmap bmp) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);

        byte[] imageBytes = baos.toByteArray();

        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;

    }


    public void retrieveData() {

        if (!session.hasProfilePic()) {

            profilepic = "http://i.imgur.com/rpPnGkP.png";

        }

        title = titletext.getText().toString();

        status = statustext.getText().toString();

        url = urltext.getText().toString();

    }


    private void uploadImage() {

        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {

                        loading.dismiss();

                        Toast.makeText(AdminNews.this, s, Toast.LENGTH_LONG).show();

                    }

                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        loading.dismiss();

                        String error = volleyError.toString();

                        Toast.makeText(AdminNews.this, "Unstable Internet Connection!", Toast.LENGTH_LONG).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                image = getStringImage(bitmap);

                retrieveData();

                currentTimeStamp();

                HashMap<String, String> profile = session.getUserProfileDetails();

                name = profile.get(SessionManagement.FULLNAME);

                Map<String, String> params = new Hashtable<String, String>();

                params.put(KEY_NAME, name);

                params.put(KEY_IMAGE, image);

                params.put(KEY_STATUS, status);

                params.put(KEY_PROFILEPIC, profilepic);

                params.put(KEY_TIMESTAMP, timestamp);

                params.put(KEY_URL, url);

                return params;

            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    private void uploadData() {

        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String s) {

                        loading.dismiss();

                        Toast.makeText(AdminNews.this, s, Toast.LENGTH_LONG).show();

                    }

                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        loading.dismiss();

                        String error = volleyError.toString();

                        Toast.makeText(AdminNews.this, "Unstable Internet Connection!", Toast.LENGTH_LONG).show();
                    }

                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                retrieveData();

                currentTimeStamp();

                HashMap<String, String> profile = session.getUserProfileDetails();

                name = profile.get(SessionManagement.FULLNAME);

                Map<String, String> params = new Hashtable<String, String>();

                params.put(KEY_NAME, name);

                params.put(KEY_STATUS, status);

                params.put(KEY_PROFILEPIC, profilepic);

                params.put(KEY_TIMESTAMP, timestamp);

                params.put(KEY_URL, url);

                return params;

            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                previewcontainer.setVisibility(View.VISIBLE);

                preview.setImageBitmap(bitmap);


            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }


    public void onClick(View view) {

        if (view == select) {

            showFileChooser();

        }

        if (view == post) {

            validation();

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                Intent intent = new Intent(this, AdminPanel.class);

                finish();

                startActivity(intent);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }


    private void showNotification(boolean isConnected) {

        if (isConnected) {

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationgreen));

            notification.setText(getResources().getString(R.string.connected));

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    notification.setVisibility(View.GONE);

                }

            }, 3000);

        } else {

            slide();

            notification.setVisibility(View.VISIBLE);

            notification.setBackground(getResources().getDrawable(R.drawable.notificationred));

            notification.setText(getResources().getString(R.string.notconnected));

        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        AppController.getInstance().setConnectivityListener(this);

    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        showNotification(isConnected);

    }


}
