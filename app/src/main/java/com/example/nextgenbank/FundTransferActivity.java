package com.example.nextgenbank;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer);


        accountNumberEditText = findViewById(R.id.etAccountNumber);
        recipientNameEditText = findViewById(R.id.etRecipientName);
        transferAmountEditText = findViewById(R.id.etTransferAmount);
        transferButton = findViewById(R.id.btnTransfer);


        currentBalance = fetchCurrentBalance();


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


            processFundTransfer(transferAmount);


            Toast.makeText(FundTransferActivity.this, "Transfer Successful! New Balance: ₹" + currentBalance, Toast.LENGTH_SHORT).show();
        });
    }


    private double fetchCurrentBalance() {
        return 10000.00;
    }


    private void processFundTransfer(double transferAmount) {
        currentBalance -= transferAmount;


        updateAccountBalance(currentBalance);
    }


    private void updateAccountBalance(double updatedBalance) {

    }
}
