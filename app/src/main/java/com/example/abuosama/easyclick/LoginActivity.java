package com.example.abuosama.easyclick;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abuosama.easyclick.Interfacee.APIService;
import com.example.abuosama.easyclick.Interfacee.ApiClient;
import com.example.abuosama.easyclick.Model.Config;


import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog pDialog;

    //@Bind(R.id.input_email) EditText _emailText;
   // @Bind(R.id.input_password) EditText _passwordText;
    //@Bind(R.id.btn_login) Button _loginButton;
  //  @Bind(R.id.link_signup) TextView _signupLink;

    @Nullable
    @BindView(R.id.input_email)EditText _emailText;
    @Nullable
    @BindView(R.id.btn_login)Button  _loginButton;
    @Nullable
     @BindView(R.id.input_password)EditText _passwordText;
//    @Nullable
//    @BindView(R.id.link_signup)TextView _signupLink;
//    @Nullable
//            @BindView(R.id.link_forget)TextView link_forget;

    String email,userName,uid,fname,sname,gernder;
    private boolean loggedIn = false;
    Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);




//        link_forget.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent=new Intent(LoginActivity.this,ForgetActivity1.class);
//                startActivity(intent);
//
//
//            }
//        });

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

//        _signupLink.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Start the Signup activity
//                Intent intent = new Intent(getApplicationContext(), SingnUpActivity1.class);
//                startActivityForResult(intent, REQUEST_SIGNUP);
//                finish();
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//            }
//        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (validate() == false) {
            onLoginFailed();
            return;
        }

        //_loginButton.setEnabled(false);

        loginByServer();
    }

    private void loginByServer() {
        pDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Login Please wait...");
        pDialog.setCancelable(false);

        showpDialog();


         email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


        APIService service = ApiClient.getClient().create(APIService.class);

        Call<MSG> userCall = service.userLogIn(email,password);

        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                //onSignupSuccess();

                Log.d("onresponse",response.toString());

                Log.d("onResponse", "" + response.body().getMessage());


                if(response.body().getSuccess() == 1) {



                      //showNetworkDialog(context);
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                            LoginActivity.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Login Successfully");

                    // Setting Dialog Message
                    alertDialog.setMessage("You have been logged in\n successfully");

                    // Setting Icon to Dialog
                    //alertDialog.setIcon(R.drawable.tick);

                    // Setting OK Button
                    userName = response.body().getUsertype();
                    uid      = response.body().getUid();
                    fname    = response.body().getFname();
                    sname    = response.body().getSname();
                    gernder  =response.body().getGender();

                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {



                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME,
                                    Context.MODE_PRIVATE);
//
//                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                            //Adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.EMAIL_SHARED_PREF, email);
                            editor.putString("cod",userName);
                            editor.putString("uid",uid);
                             editor.putString("fname", fname);
                            editor.putString("sname", sname);
                            editor.putString("gender", gernder);
//
                            //Saving values to editor
                            editor.commit();

                         //   Intent intent1=new Intent(LoginActivity.this, HomeActivity1.class);
                            //intent1.putExtra("usertype",userName);
                          //  startActivity(intent1);
                            finish();


                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                   // Toast.makeText(LoginActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();



                }else {
                    Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();




                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
           // Intent intent = new Intent(LoginActivity.this,HomeActivity1.class);
           // startActivity(intent);
        }
    }



    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _emailText.setError("Enter a valid Email or Phone ");
            requestFocus(_emailText);
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("Password is empty");
           // requestFocus(_passwordText);
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }


    public static void showNetworkDialog(Context context){
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Network Error");

        alertDialogBuilder
                .setMessage("Check Internet Connection!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}