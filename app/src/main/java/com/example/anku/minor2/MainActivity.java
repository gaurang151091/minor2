
package com.example.anku.minor2;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.FacebookSdk;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;


import info.hoang8f.widget.FButton;

public class MainActivity extends ActionBarActivity {

    private FButton LoginBtn;
    private FButton SignupBtn;
    public static int APP_REQUEST_CODE = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountKit.initialize(getApplicationContext());
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        LoginBtn = (FButton) findViewById(R.id.login_button);
        SignupBtn = (FButton) findViewById(R.id.signup_button);



        AccessToken accessToken = AccountKit.getCurrentAccessToken();
        if (accessToken != null) {
            Intent i=new Intent(this,After.class);
            finish();
            startActivity(i);
        } else {

        }
        //    LoginBtn=(FButton) findViewById(R.id.button);
        LoginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Intent intent = new Intent(getApplicationContext(), AccountKitActivity.class);
                AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                        new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,
                                AccountKitActivity.ResponseType.TOKEN);

                //EditText phone_number;

                //name= (EditText) findViewById(R.id.editText);
                //phone_number=(EditText) findViewById(R.id.phonenumber);
                // PhoneNumber p=new PhoneNumber("91",phone_number.getText()+"","");
                // configurationBuilder.setInitialPhoneNumber(p);
                configurationBuilder.setReadPhoneStateEnabled(true);
                configurationBuilder.setReceiveSMS(true);
                intent.putExtra(
                        AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                        configurationBuilder.build());
                startActivityForResult(intent, APP_REQUEST_CODE);
            }
        });






    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                Toast.makeText(this,
                        "error",
                        Toast.LENGTH_LONG)
                        .show();
                //showErrorActivity(loginResult.getError());
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Login Successful";

                } else {
                    toastMessage = String.format(
                            "Login Successful"                        );
                }

                Intent nextScreen = new Intent(this , After.class);
                startActivity(nextScreen);
                Toast.makeText(this,
                        "Login successful",
                        Toast.LENGTH_LONG)
                        .show();
            }

            // Surface the result to your user in an appropriate way.
       /*     Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
       */ }
    }


}