package com.example.nextgenbank;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class TransactionHistoryActivity extends AppCompatActivity {

    private ListView transactionListView;
    private ArrayList<String> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);


        transactionListView = findViewById(R.id.lvTransactionHistory);

        transactionList = fetchTransactionHistory();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                transactionList
        );


        transactionListView.setAdapter(adapter);
    }


    private ArrayList<String> fetchTransactionHistory() {
        ArrayList<String> transactions = new ArrayList<>();


        transactions.add("Deposit: ₹5,000.00 on 2024-09-10");
        transactions.add("Withdrawal: ₹2,000.00 on 2024-09-12");
        transactions.add("Transfer: ₹1,500.00 on 2024-09-14");
        transactions.add("Deposit: ₹10,000.00 on 2024-09-15");
        transactions.add("Withdrawal: ₹3,000.00 on 2024-09-18");


        return transactions;
    }
}
