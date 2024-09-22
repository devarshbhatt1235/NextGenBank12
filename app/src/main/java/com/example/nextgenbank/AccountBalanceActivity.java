package com.example.nextgenbank;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AccountBalanceActivity extends AppCompatActivity {

    private TextView balanceAmountTextView;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_balance);


        balanceAmountTextView = findViewById(R.id.tvBalanceAmount);
        refreshButton = findViewById(R.id.btnRefresh);


        String initialBalance = "₹10,000.00";
        balanceAmountTextView.setText(initialBalance);


        refreshButton.setOnClickListener(v -> {

            String updatedBalance = "₹12,000.00";
            balanceAmountTextView.setText(updatedBalance);


            Toast.makeText(AccountBalanceActivity.this, "Balance updated", Toast.LENGTH_SHORT).show();
        });
    }
}
