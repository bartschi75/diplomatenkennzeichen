package com.sbartsch.diplomatenkennzeichen;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.content.Intent;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnInitListener {

    private WebView mWebView;
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private ImageView mImageView4;
    private ImageView mImageView5;
    private ImageView mImageView6;
    private ImageView mImageView7;
    private ImageView mImageView8;
    private ImageView mImageView9;
    private ImageView mImageViewFlag;
    private TextView mTextView;
    private Spinner mSpinner;
    private TextToSpeech mTTS;
    private boolean bFirstCall = true;
    private String[] scountries;
    private String[] scountryCodes;
    private String[] sPlaces;
    private String[] sSpeeches;
    int iCountryCode;
    private Boolean bPrefSpeech = true;
    private Boolean bPrefConnection = true;


    //used for TTS
    private int MY_DATA_CHECK_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get preferences (probably not the right place, because they are not being updated)
        SharedPreferences sPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Boolean bPrefSpeech = sPrefs.getBoolean(getString(R.string.pref_speech), true);
        Boolean bPrefConnection = sPrefs.getBoolean(getString(R.string.pref_connection), true);

        //Strings
        Resources res = getResources();
        scountries = res.getStringArray(R.array.string_array_countries);
        scountryCodes = res.getStringArray(R.array.string_array_country_codes);
        sPlaces = res.getStringArray(R.array.string_array_places);
        sSpeeches = res.getStringArray(R.array.string_array_speeches);

        //Spinner
        mSpinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.string_array_country_codes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        //imageViews
        mImageView1 = (ImageView) findViewById(R.id.imageViewNumberPlate1);
        mImageView1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSpinner.setVisibility(View.VISIBLE);
                mSpinner.performClick();
            }
        });
        mImageView2 = (ImageView) findViewById(R.id.imageViewNumberPlate2);
        mImageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSpinner.setVisibility(View.VISIBLE);
                mSpinner.performClick();
            }
        });
        mImageView3 = (ImageView) findViewById(R.id.imageViewNumberPlate3);
        mImageView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // call settings
                startActivity(new Intent(MainActivity.this, EinstellungenActivity.class));
            }
        });
        mImageView4 = (ImageView) findViewById(R.id.imageViewNumberPlate4);
        mImageView4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSpinner.setVisibility(View.VISIBLE);
                mSpinner.performClick();
            }
        });
        mImageView5 = (ImageView) findViewById(R.id.imageViewNumberPlate5);
        mImageView5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSpinner.setVisibility(View.VISIBLE);
                mSpinner.performClick();
            }
        });
        mImageView6 = (ImageView) findViewById(R.id.imageViewNumberPlate6);
        mImageView6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSpinner.setVisibility(View.VISIBLE);
                mSpinner.performClick();
            }
        });
        mImageView7 = (ImageView) findViewById(R.id.imageViewNumberPlate7);
        mImageView7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSpinner.setVisibility(View.VISIBLE);
                mSpinner.performClick();
            }
        });
        mImageView8 = (ImageView) findViewById(R.id.imageViewNumberPlate8);
        mImageView8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSpinner.setVisibility(View.VISIBLE);
                mSpinner.performClick();
            }
        });
        mImageView9 = (ImageView) findViewById(R.id.imageViewNumberPlate9);
        mImageView9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mSpinner.setVisibility(View.VISIBLE);
                mSpinner.performClick();
            }
        });
        mImageViewFlag = (ImageView) findViewById(R.id.imageViewFlag);

        //TextView
        mTextView = (TextView) findViewById(R.id.textView);

        // webView
        mWebView = (WebView) findViewById(R.id.webView);
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("file:///android_asset/anleitung.html");

        //Text-to-speech
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }

    @SuppressWarnings("deprecation")
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        //parent.getItemAtPosition(pos);
        //TextView myTextView = (TextView) view;

        if (bFirstCall) {
            bFirstCall = false;
        }
        else
        {
            //webView
            if (bPrefConnection) mWebView.loadUrl("https://www.google.de/maps/place/" + sPlaces[pos]);

            //TTS
            if (bPrefSpeech) {
                if (Build.VERSION.RELEASE.startsWith("5")) {
                    mTTS.speak(sSpeeches[pos], TextToSpeech.QUEUE_FLUSH, null, null);
                }
                else {
                    mTTS.speak(sSpeeches[pos], TextToSpeech.QUEUE_FLUSH, null);
                }
            }

            //text
            mTextView.setText(scountries[pos]);

            //flag
            mImageViewFlag.setImageResource(getResources().getIdentifier("@drawable/i_flag_" + scountryCodes[pos], null, getPackageName()));

            //number plate digits
            iCountryCode= Integer.parseInt(scountryCodes[pos]);
            if (iCountryCode >= 100) {
                mImageView4.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummer"+scountryCodes[pos].substring(0, 1), null, getPackageName()));
                mImageView5.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummer"+scountryCodes[pos].substring(1, 2), null, getPackageName()));
                mImageView6.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummer"+scountryCodes[pos].substring(2, 3), null, getPackageName()));
                mImageView7.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummer_strich", null, getPackageName()));
                mImageView8.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummer1", null, getPackageName()));
            }
            else if (iCountryCode >= 10) {
                mImageView4.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummerleer.png", null, getPackageName()));
                mImageView5.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummer"+scountryCodes[pos].substring(0, 1), null, getPackageName()));
                mImageView6.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummer"+scountryCodes[pos].substring(1, 2), null, getPackageName()));
                mImageView7.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummer_strich", null, getPackageName()));
                mImageView8.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummer1", null, getPackageName()));
            }
            else {
                mImageView4.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummerleer.png", null, getPackageName()));
                mImageView5.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummerleer.png", null, getPackageName()));
                mImageView6.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummer"+scountryCodes[pos].substring(0, 1), null, getPackageName()));
                mImageView7.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummerleer.png", null, getPackageName()));
                mImageView8.setImageResource(getResources().getIdentifier("@drawable/i_digit_nummerleer.png", null, getPackageName()));
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    //for TTS
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                mTTS = new TextToSpeech(this, this);
            }
            else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }

    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            if(mTTS.isLanguageAvailable(Locale.GERMAN)==TextToSpeech.LANG_AVAILABLE) {
                mTTS.setLanguage(Locale.GERMAN);
            }
        }
        else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Ups! Sprache konnte nicht ausgegeben werden...", Toast.LENGTH_LONG).show();
        }
    }
}
