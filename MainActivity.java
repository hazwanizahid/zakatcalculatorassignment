package com.example.zakatcalculator;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


    EditText goldWeightEditText, goldValueEditText;
    RadioGroup typeRadioGroup;
    Button calculateButton, aboutButton, themeButton, shareButton;  // Added shareButton
    TextView totalValueTextView, zakatPayableTextView, totalZakatTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Apply the theme before setting the layout
        applySavedTheme();


        setContentView(R.layout.activity_main);


        goldWeightEditText = findViewById(R.id.goldWeightEditText);
        goldValueEditText = findViewById(R.id.goldValueEditText);
        typeRadioGroup = findViewById(R.id.typeRadioGroup);
        calculateButton = findViewById(R.id.calculateButton);
        aboutButton = findViewById(R.id.aboutButton);
        themeButton = findViewById(R.id.themeButton);
        shareButton = findViewById(R.id.shareButton);  // Initialize the shareButton
        totalValueTextView = findViewById(R.id.totalValueTextView);
        zakatPayableTextView = findViewById(R.id.zakatPayableTextView);
        totalZakatTextView = findViewById(R.id.totalZakatTextView);


        calculateButton.setOnClickListener(v -> calculateZakat());


        aboutButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });


        themeButton.setOnClickListener(v -> changeTheme());


        // Set onClickListener for the share button
        shareButton.setOnClickListener(v -> shareApp());
    }


    private void applySavedTheme() {
        int currentTheme = getSharedPreferences("themePrefs", MODE_PRIVATE)
                .getInt("theme", R.style.Theme_Zakatcalculator); // Default theme is light
        setTheme(currentTheme);
    }


    private void changeTheme() {
        int currentTheme = getSharedPreferences("themePrefs", MODE_PRIVATE)
                .getInt("theme", R.style.Theme_Zakatcalculator);


        if (currentTheme == R.style.Theme_Zakatcalculator) {
            setTheme(R.style.Theme_Zakatcalculator_Dark);  // Switch to dark theme
        } else {
            setTheme(R.style.Theme_Zakatcalculator);  // Switch to light theme
        }


        // Save the new theme in SharedPreferences
        getSharedPreferences("themePrefs", MODE_PRIVATE).edit()
                .putInt("theme", currentTheme == R.style.Theme_Zakatcalculator ?
                        R.style.Theme_Zakatcalculator_Dark : R.style.Theme_Zakatcalculator)
                .apply();


        // Recreate the activity to apply the theme immediately
        recreate();
    }


    @SuppressLint("SetTextI18n")
    private void calculateZakat() {
        String weightStr = goldWeightEditText.getText().toString();
        String valueStr = goldValueEditText.getText().toString();


        if (weightStr.isEmpty() || valueStr.isEmpty()) {
            Toast.makeText(this, "Please enter all the values", Toast.LENGTH_SHORT).show();
            return;
        }


        double goldWeight = Double.parseDouble(weightStr);
        double goldValue = Double.parseDouble(valueStr);


        int selectedTypeId = typeRadioGroup.getCheckedRadioButtonId();
        double X = 0;
        if (selectedTypeId == R.id.keepRadioButton) {
            X = 85;
        } else if (selectedTypeId == R.id.wearRadioButton) {
            X = 200;
        }


        double goldValueTotal = goldWeight * goldValue;
        double zakatPayable = (goldWeight - X) > 0 ? (goldWeight - X) * goldValue : 0;
        double totalZakat = zakatPayable * 0.025;


        totalValueTextView.setText("Total Value: RM " + goldValueTotal);
        zakatPayableTextView.setText("Zakat Payable: RM " + zakatPayable);
        totalZakatTextView.setText("Total Zakat: RM " + totalZakat);
    }


    // Method to share the app
    private void shareApp() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "Check out this awesome app for calculating zakat: " +
                "https://github.com/your-github-link";  // Replace with your actual app link
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Zakat Calculator App");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);


        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}
