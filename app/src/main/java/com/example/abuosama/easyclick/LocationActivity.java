package com.example.abuosama.easyclick;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;

public class LocationActivity extends AppCompatActivity {

    private TextView varansi,ballia,gazi;
    private String lat="25.3176";
    private String lon="82.9739";

    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    public TextView mPlaceDetailsText,category;

    private TextView mPlaceAttribution;
    private boolean mAutoHighlight;
    Button searchhall;
    // EditText ;
    String latisearch,lat1,lon1,message;


    private android.app.DatePickerDialog fromDatePickerDialog;
    private android.app.DatePickerDialog toDatePickerDialog;
    private EditText fromDateEtxt;
    private EditText toDateEtxt;

    private SimpleDateFormat dateFormatter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        varansi = findViewById(R.id.varansi);
//        ballia  = findViewById(R.id.ballia);
//        gazi = findViewById(R.id.gazi);

        ButterKnife.bind(this);
        // getActionBar().show();
       // category=findViewById(R.id.category);
        //searchhall=findViewById(R.id.searchhall);
        mPlaceDetailsText = findViewById(R.id.open_button);
        mPlaceAttribution = (TextView) findViewById(R.id.place_attribution);
        final String location=mPlaceDetailsText.getText().toString();
        //final String cat=category.getText().toString();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

       // findViewsById();

       // setDateTimeField();

//        category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent=new Intent(getApplication(),CategoryList.class);
//                startActivityForResult(intent,2);
//                // finish();
//            }
//        });




        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

//

        mPlaceDetailsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAutocompleteActivity();
            }
        });





//
//
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.

            AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                    .setTypeFilter(Place.TYPE_COUNTRY)
                    .setCountry("IN")
                    .build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setFilter(autocompleteFilter)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e("", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("", "Place Selected: " + place.getName());

                // Format the place's details and display them in the TextView.
//                mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
//                        place.getId(), place.getAddress(), place.getPhoneNumber(),
//                        place.getWebsiteUri()));
                mPlaceDetailsText.setText(place.getAddress());
                latisearch= String.valueOf(place.getLatLng());


                String s = latisearch.toString();
                String[] latLng = s.substring(10, s.length() - 1).split(",");
                lat = latLng[0];
                lon = latLng[1];

                Intent intent1 = new Intent(LocationActivity.this,MainCategory.class);
                intent1.putExtra("lat",lat);
                intent1.putExtra("lon",lon);
                startActivity(intent1);

//                Bundle bundle = new Bundle();
//                bundle.putString("lat", lat);
//                bundle.putString("lon",lon);
//                MainCategoryFragment fragobj = new MainCategoryFragment();
//                fragobj.setArguments(bundle);


                // Display attributions if required.
                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
                } else {
                    mPlaceAttribution.setText("");
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e("", "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }

        }

        //new code changes here for the category crash.

        else if(null!=data)
        {
            Bundle bundle = data.getExtras();
            message = bundle.getString("MESSAGE");
            category.setText(message);

            //below are the old code for the category changes




        }
        else {

            finish();


        }


    }


}
