package fr.esilv.probleme_co_assist.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import fr.esilv.probleme_co_assist.R;

/**
 * Created by Charlotte on 29/02/2016.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private static String URL = "https://qbzewe93xe.execute-api.us-east-1.amazonaws.com/dev/log/register?userID=";
    private EditText mFirstname;
    private EditText mLastname;
    private EditText mPassword;
    private EditText mUserID;
    private EditText mAddress;
    private EditText mEmail;
    private EditText mImage;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mFirstname = (EditText) findViewById(R.id.e_firstname);
        mLastname = (EditText) findViewById(R.id.e_lastname);
        mPassword = (EditText) findViewById(R.id.e_pwd);
        mUserID = (EditText) findViewById(R.id.e_UserID);
        mAddress = (EditText) findViewById(R.id.e_address);
        mEmail = (EditText) findViewById(R.id.e_email);
        mImage = (EditText) findViewById(R.id.e_img);

        mButton = (Button) findViewById(R.id.registration_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (!isConnected()) {
            Toast toast = Toast.makeText(this, " You are not connected to Internet", Toast.LENGTH_LONG);
            toast.show();
        } else {
            if (TextUtils.isEmpty(mFirstname.getText()) ||
                    TextUtils.isEmpty(mAddress.getText()) ||
                    TextUtils.isEmpty(mEmail.getText()) ||
                    TextUtils.isEmpty(mLastname.getText()) ||
                    TextUtils.isEmpty(mImage.getText()) ||
                    TextUtils.isEmpty(mPassword.getText()) ||
                    TextUtils.isEmpty(mUserID.getText())) {

                Toast toast = Toast.makeText(this, " Please fill all the field", Toast.LENGTH_LONG);
                toast.show();
            } else {

                RequestGET(mFirstname.getText().toString(), mLastname.getText().toString(), mPassword.getText().toString(), mUserID.getText().toString(), mAddress.getText().toString(), mEmail.getText().toString(), mImage.getText().toString());

                CharSequence text = "You are registered !";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();

                Intent i = new Intent(this, ConnexionActivity.class);
                startActivity(i);
            }
        }
    }

    public void RequestGET(String firstName, String lastName, String pwd, String UserID, String address, String email, String img) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + UserID + "&password=" + pwd + "&first_name=" + firstName + "&last_name=" + lastName + "&email=" + email + "&address=" + address + "&imgUrl=" + img,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response", response.toString());
                        } catch (Exception e) {
                            Log.d("jackson", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error", error.getMessage());
                    }
                });

        requestQueue.add(stringRequest);
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}
