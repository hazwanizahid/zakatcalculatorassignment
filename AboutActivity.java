package com.example.zakatcalculator;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;


public class AboutActivity extends AppCompatActivity {


    TextView aboutTextView;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        aboutTextView = findViewById(R.id.aboutTextView);
        String aboutText = "This is an application for estimating zakat payment for gold keeping.\n" +
                "The application provides an easy-to-use interface for calculating zakat, with different theme options.\n\n" +
                "\n" +
                "https://github.com/hazwanizahid/zakatcalculatorassignment/tree/main";


        aboutTextView.setText(aboutText);


        // Make the URL clickable
        Linkify.addLinks(aboutTextView, Linkify.WEB_URLS);
    }
}
