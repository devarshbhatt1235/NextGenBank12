package com.example.nextgenbank;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FundTransferActivity extends AppCompatActivity {

    private EditText accountNumberEditText, recipientNameEditText, transferAmountEditText;
    private Button transferButton;

    private double currentBalance;
    private static final double MINIMUM_BALANCE = 500.00;

    // To store the sender's email
    private String senderEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer);

        // Fetch sender's email from Intent
        Intent intent = getIntent();
        senderEmail = intent.getStringExtra("SENDER_EMAIL");

        accountNumberEditText = findViewById(R.id.etAccountNumber);
        recipientNameEditText = findViewById(R.id.etRecipientName);
        transferAmountEditText = findViewById(R.id.etTransferAmount);
        transferButton = findViewById(R.id.btnTransfer);

        // Fetch the current balance of the sender
        currentBalance = fetchCurrentBalance(senderEmail);

        transferButton.setOnClickListener(v -> {
            String accountNumber = accountNumberEditText.getText().toString();
            String recipientName = recipientNameEditText.getText().toString();
            String transferAmountStr = transferAmountEditText.getText().toString();

            if (TextUtils.isEmpty(accountNumber)) {
                accountNumberEditText.setError("Account number is required");
                return;
            }

            if (TextUtils.isEmpty(recipientName)) {
                recipientNameEditText.setError("Recipient name is required");
                return;
            }

            if (TextUtils.isEmpty(transferAmountStr)) {
                transferAmountEditText.setError("Transfer amount is required");
                return;
            }

            double transferAmount = Double.parseDouble(transferAmountStr);

            if ((currentBalance - transferAmount) < MINIMUM_BALANCE) {
                Toast.makeText(FundTransferActivity.this, "Insufficient balance. Minimum balance of ₹500 required.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if the account number exists in the database
            DBHelper dbHelper = new DBHelper(this);
            if (!dbHelper.checkAccountNumberExists(accountNumber)) {
                Toast.makeText(FundTransferActivity.this, "Account number does not exist.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Process the fund transfer
            processFundTransfer(transferAmount, accountNumber);

            Toast.makeText(FundTransferActivity.this, "Transfer Successful! New Balance: ₹" + currentBalance, Toast.LENGTH_SHORT).show();
        });
    }

    // Fetch current balance for the sender
    private double fetchCurrentBalance(String email) {
        DBHelper dbHelper = new DBHelper(this);
        return dbHelper.getUserBalance(email);
    }

    private void processFundTransfer(double transferAmount, String accountNumber) {
        // Deduct the transfer amount from the sender's balance
        currentBalance -= transferAmount;

        // Update the sender's account balance in the database
        updateAccountBalance(currentBalance);

        // Update the recipient's balance in the database
        DBHelper dbHelper = new DBHelper(this);
        double recipientNewBalance = dbHelper.getUserBalance(accountNumber) + transferAmount;
        dbHelper.updateUserBalance(accountNumber, (int) recipientNewBalance);
    }

    private void updateAccountBalance(double updatedBalance) {
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.updateUserBalance(senderEmail, (int) updatedBalance);
    }
}
