package com.example.nextgenbank;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoanManagementActivity extends AppCompatActivity {

    private TextView loanAmountTextView, interestRateTextView, monthlyInstallmentTextView, loanTenureTextView;
    private Button payLoanButton;

    private double currentBalance = 100000.00;
    private double loanAmount = 1000000.00;
    private double monthlyInstallment = 15000.00;
    private double interestRate = 8.5;
    private String loanTenure = "10 years";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_management);


        loanAmountTextView = findViewById(R.id.tvLoanAmount);
        interestRateTextView = findViewById(R.id.tvInterestRate);
        monthlyInstallmentTextView = findViewById(R.id.tvMonthlyInstallment);
        loanTenureTextView = findViewById(R.id.tvLoanTenure);
        payLoanButton = findViewById(R.id.btnPayLoan);


        loanAmountTextView.setText("₹" + loanAmount);
        interestRateTextView.setText(interestRate + "%");
        monthlyInstallmentTextView.setText("₹" + monthlyInstallment);
        loanTenureTextView.setText(loanTenure);


        payLoanButton.setOnClickListener(v -> {
            if (currentBalance < monthlyInstallment) {
                Toast.makeText(LoanManagementActivity.this, "Insufficient funds to pay the monthly installment.", Toast.LENGTH_SHORT).show();
            } else {
                processLoanPayment();
                Toast.makeText(LoanManagementActivity.this, "Monthly Installment Paid! New Balance: ₹" + currentBalance, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void processLoanPayment() {
        currentBalance -= monthlyInstallment;


        updateAccountBalance(currentBalance);
    }


    private void updateAccountBalance(double updatedBalance) {

    }
}
