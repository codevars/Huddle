package com.srmvdp.huddle.Profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.srmvdp.huddle.Dashboard;
import com.srmvdp.huddle.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.bitmap;
import static com.srmvdp.huddle.R.id.preview;
import static com.srmvdp.huddle.R.id.previewcontainer;


public class SelfProfile extends AppCompatActivity implements View.OnClickListener {

    //***Post URL ***//

    String PROFILE_URL = "";

    //***Constants**//

    private int PICK_IMAGE_REQUEST = 1;

    //***Edit-Text***//

    private EditText nameEdit;

    private EditText bioEdit;

    private EditText rollnoEdit;

    private EditText emailEdit;

    private EditText phoneEdit;

    //*** Edit - text end ***//

    String name;

    String bio;

    String phone;

    String rollno;

    String email;

    private ActionBar bar;

    private Button button;

    private CircleImageView profilephoto;

    private Bitmap bitmap;

    private TextView changephoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_profile);

        profilephoto = (CircleImageView) findViewById(R.id.profilephoto);

        changephoto = (TextView) findViewById(R.id.changeprofilepictesxt);

        button = (Button) findViewById(R.id.submitSelfProfile);

        //*** Edit - text***//

        nameEdit = (EditText) findViewById(R.id.nameEdit);

        bioEdit = (EditText) findViewById(R.id.bioEdit);

        rollnoEdit = (EditText) findViewById(R.id.rollnoEdit);

        emailEdit = (EditText) findViewById(R.id.emailEdit);

        phoneEdit = (EditText) findViewById(R.id.phoneEdit);

        //*** Edit - Text end ***//

        profilephoto.setOnClickListener(this);

        button.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                Intent intent = new Intent(this, BioPage.class);

                finish();

                startActivity(intent);

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }

    }

    private void showFileChooser() {

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri filePath = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profilephoto.setImageBitmap(bitmap);


            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }


    @Override
    public void onClick(View view) {

        if (view == profilephoto) {

            showFileChooser();

        }

        if (view == button) {

            name = nameEdit.getText().toString().trim();
            bio = nameEdit.getText().toString().trim();
            rollno = nameEdit.getText().toString().trim();
            email = nameEdit.getText().toString().trim();
            phone = nameEdit.getText().toString().trim();

            arrangeData(name, bio, rollno, email, phone);


        }

    }


    public void arrangeData(String Name, String Bio, String Rollno, String Email, String Phone) {

        Name = name;
        Bio = bio;
        Rollno = rollno;
        Email = email;
        Phone = phone;


        final String finalName1 = Name;
        final String finalBio = Bio;
        final String finalRollno = Rollno;
        final String finalEmail = Email;
        final String finalPhone = Phone;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, PROFILE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("success")) {
                            Toast.makeText(SelfProfile.this, "Profile has been successfully updated", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(SelfProfile.this, Dashboard.class);
                            startActivity(i);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SelfProfile.this, "An error has occured, Please try again later", Toast.LENGTH_SHORT).show();

                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", finalName1);
                params.put("bio", finalBio);
                params.put("roll no", finalRollno);
                params.put("email", finalEmail);
                params.put("phone", finalPhone);
                return params;

            }


        };

        RequestQueue reuqestQue = Volley.newRequestQueue(this);
        reuqestQue.add(stringRequest);


    }
}






