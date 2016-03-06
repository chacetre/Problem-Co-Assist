package fr.esilv.probleme_co_assist.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.esilv.probleme_co_assist.R;

/**
 * Created by Charlotte on 29/02/2016.
 */
public class AccueilActivity extends Activity implements View.OnClickListener {

    private Button mLogin;
    private Button mRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accueil);

        mLogin = (Button) findViewById(R.id.login_button);
        mRegistration = ((Button) this.findViewById(R.id.registration_button));
        mLogin.setOnClickListener(this);
        mRegistration.setOnClickListener(this);

        SharedPreferences pref = getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        String defaultToken = pref.getString("token", null);
        String defaultUserID = pref.getString("login", null);

        if (defaultToken != null || defaultUserID != null) {
            Intent i = new Intent(this, ListUsersActivity.class);

            i.putExtra("token", defaultToken);
            i.putExtra("login", defaultUserID);

            startActivity(i);
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registration_button:
                Intent z = new Intent(this, RegisterActivity.class);
                startActivity(z);
                break;
            case R.id.login_button:
                Intent y = new Intent(this, ConnexionActivity.class);
                startActivity(y);
                break;
        }
    }
}
