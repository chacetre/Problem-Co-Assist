package fr.esilv.probleme_co_assist.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.esilv.probleme_co_assist.Models.Login;
import fr.esilv.probleme_co_assist.R;


public class ConnexionActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView mLoginView;
    private EditText mPasswordView;
    private Button mLoginButton;
    private String URL = "https://qbzewe93xe.execute-api.us-east-1.amazonaws.com/dev/log/login?userID=";
    private EditText mAccessible;
    private String token;
    Login log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        mLoginView = (AutoCompleteTextView) findViewById(R.id.login);
        mPasswordView = (EditText) findViewById(R.id.password);

        mLoginButton = (Button) findViewById(R.id.login_button);

        mLoginButton.setOnClickListener(this);
    }

    private void attemptLogin() {

        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();
        mPasswordView.setError(null);

        RequestGET(login, password);

        //Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_login));
        }
        // Check for a valid login.
        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(getString(R.string.error_field_required));
        }

    }

    public void RequestGET(String login, String password) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + login + "&password=" + password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            if (response.toString() != "\"errorMessage\": \"Process exited before completing request\"") {
                                Intent i = new Intent(getApplicationContext(), ListUsersActivity.class);
                                Login log = objectMapper.readValue(response, Login.class);

                                i.putExtra("token", log.getToken());
                                i.putExtra("login", log.getUser().getUserId());

                                Log.d("good", response.toString());
                                SharedPreferences pref = getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("login", log.getUser().getUserId());
                                editor.putString("token", log.getToken());
                                editor.commit();

                                startActivity(i);
                            } else {
                                mPasswordView.setError(getString(R.string.error_login));
                            }

                        } catch (Exception e) {

                            Log.d("error", e.getMessage());
                            mPasswordView.setError("Error on the password or userID");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPasswordView.setError(getString(R.string.error_login));
                Log.d("error", error.getMessage());
            }
        }
        );
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        if (isConnected()) {
            attemptLogin();
        } else {
            Toast t = Toast.makeText(this, "You are not connected to internet", Toast.LENGTH_LONG);
            t.show();
        }
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

