package com.example.nextgenbank;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BillPaymentsActivity extends AppCompatActivity {

    private EditText billerNameEditText, accountNumberEditText, paymentAmountEditText;
    private Button payBillButton;

    private double currentBalance = 10000.00;
    private static final double MINIMUM_BALANCE = 500.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_payments);


        billerNameEditText = findViewById(R.id.etBillerName);
        accountNumberEditText = findViewById(R.id.etAccountNumber);
        paymentAmountEditText = findViewById(R.id.etPaymentAmount);
        payBillButton = findViewById(R.id.btnPayBill);


        payBillButton.setOnClickListener(v -> {
            String billerName = billerNameEditText.getText().toString();
            String accountNumber = accountNumberEditText.getText().toString();
            String paymentAmountStr = paymentAmountEditText.getText().toString();


            if (TextUtils.isEmpty(billerName)) {
                billerNameEditText.setError("Biller name is required");
                return;
            }

            if (TextUtils.isEmpty(accountNumber)) {
                accountNumberEditText.setError("Account number is required");
                return;
            }

            if (TextUtils.isEmpty(paymentAmountStr)) {
                paymentAmountEditText.setError("Payment amount is required");
                return;
            }

            double paymentAmount = Double.parseDouble(paymentAmountStr);


            if ((currentBalance - paymentAmount) < MINIMUM_BALANCE) {
                Toast.makeText(BillPaymentsActivity.this, "Insufficient balance. Minimum balance of ₹500 required.", Toast.LENGTH_SHORT).show();
                return;
            }


            processBillPayment(paymentAmount);

            // Show success message
            Toast.makeText(BillPaymentsActivity.this, "Payment Successful! New Balance: ₹" + currentBalance, Toast.LENGTH_SHORT).show();
        });
    }


    private void processBillPayment(double paymentAmount) {

        currentBalance -= paymentAmount;


        updateAccountBalance(currentBalance);
    }


    private void updateAccountBalance(double updatedBalance) {

    }
}
