package com.example.nextgenbank;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcomeName;
    private Button btnAccountBalance, btnFundTransfer, btnTransactionHistory, btnBillPayments, btnLoanManagement, btnProfileSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvWelcomeName = findViewById(R.id.tvWelcomeName);
        btnAccountBalance = findViewById(R.id.btnAccountBalance);
        btnFundTransfer = findViewById(R.id.btnFundTransfer);
        btnTransactionHistory = findViewById(R.id.btnTransactionHistory);
        btnBillPayments = findViewById(R.id.btnBillPayments);
        btnLoanManagement = findViewById(R.id.btnLoanManagement);
        btnProfileSettings = findViewById(R.id.btnProfileSettings);


        tvWelcomeName.setText("Welcome, Devarsh Bhatt!");



        btnAccountBalance.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, AccountBalanceActivity.class);
            startActivity(intent);
        });

        btnFundTransfer.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, FundTransferActivity.class);
            startActivity(intent);
        });

        btnTransactionHistory.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, TransactionHistoryActivity.class);
            startActivity(intent);
        });

        btnBillPayments.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, BillPaymentsActivity.class);
            startActivity(intent);
        });

        btnLoanManagement.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, LoanManagementActivity.class);
            startActivity(intent);
        });

        btnProfileSettings.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, ProfileSettingsActivity.class);
            startActivity(intent);
        });
    }
}
