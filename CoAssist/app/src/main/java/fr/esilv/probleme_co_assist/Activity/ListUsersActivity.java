package fr.esilv.probleme_co_assist.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import fr.esilv.probleme_co_assist.Adapter.ListUsersRecylcerViewAdapter;
import fr.esilv.probleme_co_assist.Comparator.DateComparator;
import fr.esilv.probleme_co_assist.Comparator.NameComparator;
import fr.esilv.probleme_co_assist.Models.UserData;
import fr.esilv.probleme_co_assist.Models.UserList;
import fr.esilv.probleme_co_assist.R;

/**
 * Created by Charlotte on 29/02/2016.
 */
public class ListUsersActivity extends Activity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<UserData> userList;
    private String userID;
    private String token;
    private String URL = "https://qbzewe93xe.execute-api.us-east-1.amazonaws.com/dev/log/getlistusers?token=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        userList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_user_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ListUsersRecylcerViewAdapter(userList,this);
        mRecyclerView.setAdapter(mAdapter);

        Button but = (Button) findViewById(R.id.buttonDeconnected);
        but.setOnClickListener(this);

        Button butSortName = (Button) findViewById(R.id.buttonSortName);
        butSortName.setOnClickListener(this);

        Button butSortRegisterDate = (Button) findViewById(R.id.buttonSortRegisterDate);
        butSortRegisterDate.setOnClickListener(this);

        Intent i = getIntent();
        token = i.getStringExtra("token");
        userID = i.getStringExtra("login");

        if (!isConnected()) {

            Toast toast = Toast.makeText(this, " You are not connected", Toast.LENGTH_LONG);
            toast.show();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
                String defaultResult = sharedPref.getString("result", null);

                if (defaultResult != null) {
                    UserList log = objectMapper.readValue(defaultResult, UserList.class);
                    userList.addAll(Arrays.asList(log.getUser()));
                    mAdapter.notifyDataSetChanged();
                }


            } catch (IOException e) {
                Log.d("error", e.getMessage());
            }

        } else {
            RequestGETListUser();
        }

    }

    public void RequestGETListUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest req = new StringRequest(Request.Method.GET, "https://qbzewe93xe.execute-api.us-east-1.amazonaws.com/dev/log/getlistusers?userID=" + userID + "&token=" + token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("good1", response.toString());
                            ObjectMapper objectMapper = new ObjectMapper();
                            UserList log = objectMapper.readValue(response, UserList.class);
                            userList.addAll(Arrays.asList(log.getUser()));
                            mAdapter.notifyDataSetChanged();

                            String result = objectMapper.writeValueAsString(log);
                            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("result", result);
                            editor.commit();

                        } catch (Exception e) {
                            Log.d("error1", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("error2", error.getMessage());
            }
        }
        );
        requestQueue.add(req);
    }


    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonDeconnected:

                Intent i = new Intent(this, AccueilActivity.class);

                SharedPreferences pref = getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("login", null);
                editor.putString("token", null);
                editor.commit();

                finish();
                startActivity(i);

                break;

            case R.id.buttonSortName:
                Collections.sort(userList, new NameComparator());
                mAdapter.notifyDataSetChanged();
                break;

            case R.id.buttonSortRegisterDate:
                Collections.sort(userList, new DateComparator());
                mAdapter.notifyDataSetChanged();
                break;
        }

    }
}
