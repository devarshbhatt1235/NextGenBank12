package com.example.nextgenbank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AccountBalanceActivity extends AppCompatActivity {

    private TextView balanceAmountTextView;
    private Button refreshButton;
    private DBHelper dbHelper;
    private String userEmail;  // Logged-in user's email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_balance);

        // Initialize UI components
        balanceAmountTextView = findViewById(R.id.tvBalanceAmount);
        refreshButton = findViewById(R.id.btnRefresh);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Get the user's email passed from login activity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("userEmail")) {
            userEmail = intent.getStringExtra("userEmail");
        } else {
            Toast.makeText(this, "Error: User not logged in", Toast.LENGTH_SHORT).show();
            finish();  // Close the activity if no email is available
            return;
        }

        // Display the initial balance when the activity is first created
        updateBalanceDisplay();

        // Set a listener for the refresh button to get the updated balance
        refreshButton.setOnClickListener(v -> updateBalanceDisplay());
    }

    // Method to retrieve and update the balance in the TextView
    private void updateBalanceDisplay() {
        // Retrieve the balance from the database
        int balance = dbHelper.getUserBalance(userEmail);

        if (balance == -1) {
            Toast.makeText(this, "Error retrieving balance", Toast.LENGTH_SHORT).show();
        } else {
            // Update the balance on the UI
            String balanceFormatted = "₹" + balance + ".00";
            balanceAmountTextView.setText(balanceFormatted);
            Toast.makeText(this, "Balance updated", Toast.LENGTH_SHORT).show();
        }
    }
}
