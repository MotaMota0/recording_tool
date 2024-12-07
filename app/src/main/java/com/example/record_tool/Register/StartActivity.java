package com.example.record_tool.Register;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.MainActivity;
import com.example.record_tool.MainWindowActivity;
import com.example.record_tool.MasterReg.MasterActivity;
import com.example.record_tool.MasterReg.MasterListForClient;
import com.example.record_tool.MasterReg.UserRegPage;
import com.example.record_tool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

public class StartActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private EditText emailEditText, passwordEditText;
    private EditText nameUser;
    boolean isTheMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        nameUser = findViewById(R.id.nameEditText);


        findViewById(R.id.loginButton).setOnClickListener(v -> loginUser());
        findViewById(R.id.registerTextView).setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String userName = nameUser.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || userName.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }


        /*SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isTheMaster = sharedPreferences.getBoolean("isMaster", false);*/

        /*Intent intent = getIntent();
        isTheMaster = intent.getBooleanExtra("isMaster", false);*/






        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();

                        // Читаем данные пользователя из Firestore
                        firestore.collection("Users")
                                .document(userId)
                                .get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        boolean isMaster = documentSnapshot.getBoolean("master");
                                        String name = documentSnapshot.getString("name");

                                        if (name != null && userName.equals(name)) {
                                            // Сохраняем данные в SharedPreferences (опционально)
                                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putBoolean("isMaster", isMaster);
                                            editor.putString("name", name);
                                            editor.apply();

                                            // Переходим на соответствующий экран
                                            Class<?> nextActivity = isMaster ? MasterActivity.class : MasterListForClient.class;
                                            startActivity(new Intent(this, nextActivity));
                                            finish();
                                        } else {
                                            Toast.makeText(this, "Name does not match.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(this, "'isMaster' field is missing", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error loading user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(this, "Authentication failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });


    }
}




        /*map = new HashMap<>();

        ObjectDB obj = new ObjectDB(email,password);

        map.put("Email",emailText);
        map.put("Password",passwordText);
        firestore.collection("Firebase")
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG , "Document added with ID"+documentReference.getId());
                        Toast.makeText(StartActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG , "Err",e);
                        Toast.makeText(StartActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                    }
                });*/

