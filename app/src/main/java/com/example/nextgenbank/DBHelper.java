package com.example.nextgenbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NextGenBank.db";
    private static final int DATABASE_VERSION = 2;  // Updated version
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_ACCOUNT_NUMBER = "account_number";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_BALANCE = "balance";  // New column for balance

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table with a balance column
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FULL_NAME + " TEXT,"
                + COLUMN_ACCOUNT_NUMBER + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_BALANCE + " INTEGER" + ")";  // Added balance as INTEGER
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        // Create tables again
        onCreate(db);
    }

    // Insert user details with initial balance of ₹10,000
    public boolean insertUser(String fullName, String accountNumber, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FULL_NAME, fullName);
        contentValues.put(COLUMN_ACCOUNT_NUMBER, accountNumber);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_BALANCE, 10000);  // Set initial balance to ₹10,000
        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1; // If result == -1, insertion failed
    }

    // Check if email exists
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID}, COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Check user credentials
    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{email, password}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0; // Return true if a matching record is found
    }

    // Retrieve the user's balance
    public int getUserBalance(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_BALANCE},
                COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int balance = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BALANCE));
            cursor.close();
            return balance;
        }
        return -1;  // Return -1 if user is not found
    }

    // Retrieve balance by account number
    public int getUserBalanceByAccount(String accountNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_BALANCE},
                COLUMN_ACCOUNT_NUMBER + "=?", new String[]{accountNumber}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int balance = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BALANCE));
            cursor.close();
            return balance;
        }
        return -1;  // Return -1 if account is not found
    }

    // Check if account number exists
    public boolean checkAccountNumberExists(String accountNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID}, COLUMN_ACCOUNT_NUMBER + "=?",
                new String[]{accountNumber}, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;  // Return true if account number exists
    }

    // Update the user's balance by email
    public boolean updateUserBalance(String email, int newBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BALANCE, newBalance);
        int rowsAffected = db.update(TABLE_USERS, contentValues, COLUMN_EMAIL + "=?",
                new String[]{email});
        return rowsAffected > 0;  // Return true if update was successful
    }

    // Update the user's balance by account number
    public boolean updateUserBalanceByAccount(String accountNumber, int newBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BALANCE, newBalance);
        int rowsAffected = db.update(TABLE_USERS, contentValues, COLUMN_ACCOUNT_NUMBER + "=?",
                new String[]{accountNumber});
        return rowsAffected > 0;  // Return true if update was successful
    }

    // Process the fund transfer between accounts
    public boolean transferFunds(String senderEmail, String recipientAccountNumber, int transferAmount) {
        // Get sender's balance
        int senderBalance = getUserBalance(senderEmail);
        if (senderBalance == -1) {
            return false;  // Sender not found
        }

        // Check if recipient account exists
        if (!checkAccountNumberExists(recipientAccountNumber)) {
            return false;  // Recipient account does not exist
        }

        // Get recipient's balance
        int recipientBalance = getUserBalanceByAccount(recipientAccountNumber);

        // Deduct from sender's balance
        int newSenderBalance = senderBalance - transferAmount;
        if (newSenderBalance < 500) {
            return false;  // Insufficient balance (minimum ₹500 required)
        }

        // Add to recipient's balance
        int newRecipientBalance = recipientBalance + transferAmount;

        // Update both balances
        boolean senderUpdated = updateUserBalance(senderEmail, newSenderBalance);
        boolean recipientUpdated = updateUserBalanceByAccount(recipientAccountNumber, newRecipientBalance);

        return senderUpdated && recipientUpdated;
    }
}
