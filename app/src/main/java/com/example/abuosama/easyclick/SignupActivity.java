package com.example.abuosama.easyclick;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abuosama.easyclick.Interfacee.APIService;
import com.example.abuosama.easyclick.Interfacee.ApiClient;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private ProgressDialog pDialog;
    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;
    @Nullable
    @BindView(R.id.input_phone)EditText input_phone;
//    @Nullable
//    @BindView(R.id.day)Spinner day;
//    @Nullable
//    @BindView(R.id.month)Spinner month;
//    @Nullable
//    @BindView(R.id.year)Spinner year;
//    @Nullable
//    @BindView(R.id.male)RadioButton male;
//    @Nullable
//    @BindView(R.id.female)RadioButton female;
    @Nullable
    @BindView(R.id.input_name1)EditText input_name1;
//    @Nullable
//    @BindView(R.id.radiogroup)RadioGroup radiogroup;
    RadioButton radioButton;
//    @Nullable
//    @BindView(R.id.userrole)Spinner userrole;
//    @Nullable
//    @BindView(R.id.gendererror)TextView gendererror;
//    @Nullable
//    @BindView(R.id.test)Spinner test;
    private String userspinnertext,dayspinnertext,monthspinnertext,yearspinnertext;
    private  String gender;
    Context context;



    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
//        startActivity(intent);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        






 
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
 
//        _loginLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Finish the<a class="Eyqj4pXnY " style="z-index: 2147483647;" title="Click to Continue > by Advertise" href="#2293366"> registration<img src="http://cdncache-a.akamaihd.net/items/it/img/arrow-10x10.png" /></a> screen and return to the Login activity
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//                finish();
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
//            }
//        });
    }
 
    public void signup() {
        Log.d(TAG, "Signup");
 
        if (validate() == false) {
            onSignupFailed();

            return;
        }
 
        saveToServerDB();
 
    }
 
 
    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }
 
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Registration Failed", Toast.LENGTH_LONG).show();
 
        _signupButton.setEnabled(true);
    }
 
    public boolean validate() {
        boolean valid = true;
 
        String name = _nameText.getText().toString();
        String fname= input_name1.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String phonenum        = input_phone.getText().toString();



        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            requestFocus(_nameText);
            valid = false;
        } else {
            _nameText.setError(null);
        }



        if (fname.isEmpty() || fname.length() < 2) {
            input_name1.setError("at least 2 characters");
            valid = false;
        } else {
            input_name1.setError(null);
        }





//        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _emailText.setError("enter a valid email address");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }

//        if (radiogroup.getCheckedRadioButtonId()==-1)
//        {
//            valid = false;
//           // male.setError("please select");
//            //radioButton.setError("please set gender");
//           // Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
//
//        }
//          else{
//
//           // male.setError("select gender");
//           // female.setError("select gender");
//        }
 
 
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
 
        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        if (phonenum.isEmpty() || phonenum.length()<10) {


            input_phone.setError("enter a valid phone number");
            // return  !Patterns.PHONE.matcher(phonenum).matches();
            valid = false;
        } else {
            input_phone.setError(null);
        }
 
        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void saveToServerDB() {
        pDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);
 
        showpDialog();
 
        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String Last = input_name1.getText().toString();
        String dob  = dayspinnertext+monthspinnertext+yearspinnertext;
        String phone = input_phone.getText().toString();

 
 
        APIService service = ApiClient.getClient().create(APIService.class);
        //User user = new User(name, email, password);
 
 
        Call<MSG> userCall = service.userSignUp(name, email, password,Last,dob,gender,userspinnertext,phone);
 
        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                //onSignupSuccess();
                Log.d("onResponse", "" + response.body().getMessage());
 
 
               if(response.body().getSuccess() == 0) {




                   Toast.makeText(SignupActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();


 
                   // finish();
               }else if(response.body().getSuccess()==1){

                   AlertDialog alertDialog = new AlertDialog.Builder(
                           SignupActivity.this).create();

                   // Setting Dialog Title
                   alertDialog.setTitle("Successfully Account Created");

                   // Setting Dialog Message
                   alertDialog.setMessage("Sccessfully Created");

                   // Setting Icon to Dialog
                   //alertDialog.setIcon(R.drawable.tick);

                   // Setting OK Button
                   alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           // Write your code here to execute after dialog closed
                          // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                          /// Toast.makeText(SignupActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                       }
                   });

                   // Showing Alert Message
                   alertDialog.show();

                   //Toast.makeText(SignupActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                   //startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                }
            }
 
            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
               // Utils.showNetworkDialog(context);
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
 
}