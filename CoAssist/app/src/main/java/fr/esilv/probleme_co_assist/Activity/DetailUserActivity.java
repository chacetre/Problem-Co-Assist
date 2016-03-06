package fr.esilv.probleme_co_assist.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import fr.esilv.probleme_co_assist.R;
import fr.esilv.probleme_co_assist.Models.UserData;

/**
 * Created by Charlotte on 29/02/2016.
 */
public class DetailUserActivity extends Activity {

    private TextView mFirstName;
    private TextView mLastName;
    private TextView mPasseword;
    private TextView mEmail;
    private TextView mAddress;
    private TextView mUserId;
    private TextView mRegisterDate;
    private ImageView mImage;

    private UserData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_user_activity);
        mFirstName = (TextView) this.findViewById(R.id.firstNameDetail);
        mLastName = (TextView) this.findViewById(R.id.lastNameDetail);
        mAddress = (TextView) this.findViewById(R.id.addressDetail);
        mEmail = (TextView) this.findViewById(R.id.emailDetail);
        mImage = (ImageView) this.findViewById(R.id.imageDetail);
        mPasseword = (TextView) this.findViewById(R.id.passewordDetail);
        mUserId = (TextView) this.findViewById(R.id.userIdDetail);
        mRegisterDate = (TextView) this.findViewById(R.id.registerDateDetail);

        Intent i = getIntent();
        user = (UserData) i.getSerializableExtra("user");

        initInformations();
    }

    private void initInformations() {

        mFirstName.setText(user.getFirstname());
        mRegisterDate.setText(user.getRegisterDate());
        mUserId.setText(user.getUserId());
        mAddress.setText(user.getAddress());
        mPasseword.setText(user.getPwd());
        mEmail.setText(user.getEmail_adress());
        mLastName.setText(user.getLastname());
        Glide
                .with(this)
                .load(user.getImage())
                .centerCrop()
                .crossFade()
                .into(mImage);

    }


}

