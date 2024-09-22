package com.example.nextgenbank;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileSettingsActivity extends AppCompatActivity {

    private EditText currentPasswordEditText, newPasswordEditText, confirmNewPasswordEditText, newEmailEditText;
    private Button saveChangesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);


        currentPasswordEditText = findViewById(R.id.etCurrentPassword);
        newPasswordEditText = findViewById(R.id.etNewPassword);
        confirmNewPasswordEditText = findViewById(R.id.etConfirmNewPassword);
        newEmailEditText = findViewById(R.id.etNewEmail);
        saveChangesButton = findViewById(R.id.btnSaveChanges);

        saveChangesButton.setOnClickListener(v -> {
            String currentPassword = currentPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();
            String confirmNewPassword = confirmNewPasswordEditText.getText().toString();
            String newEmail = newEmailEditText.getText().toString();


            if (TextUtils.isEmpty(currentPassword)) {
                currentPasswordEditText.setError("Current password is required");
                return;
            }

            if (TextUtils.isEmpty(newPassword)) {
                newPasswordEditText.setError("New password is required");
                return;
            }

            if (!newPassword.equals(confirmNewPassword)) {
                confirmNewPasswordEditText.setError("Passwords do not match");
                return;
            }

            if (TextUtils.isEmpty(newEmail)) {
                newEmailEditText.setError("Email is required");
                return;
            }


            saveProfileChanges(currentPassword, newPassword, newEmail);
        });
    }


    private void saveProfileChanges(String currentPassword, String newPassword, String newEmail) {

        Toast.makeText(ProfileSettingsActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
    }
}
