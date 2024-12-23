package com.example.record_tool.Dao;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.record_tool.model.UserRegPage;
import com.example.record_tool.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private EditText emailEditText, passwordEditText;
    private EditText nameUser;
    //private CheckBox checkBoxMaster,checkBoxClient;
    private RadioButton radioButtonMaster;




    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_activity);

        mAuth = FirebaseAuth.getInstance();

        firestore  = FirebaseFirestore.getInstance();


        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        nameUser = findViewById(R.id.nameEditText);

        radioButtonMaster = findViewById(R.id.radioButtonMaster);


        findViewById(R.id.registerButton).setOnClickListener(v -> registerUser());
    }

    private void registerUser() {

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String userName = nameUser.getText().toString().trim();



        if (email.isEmpty() || password.isEmpty() || userName.isEmpty() && email.matches("^[\\w.+\\-]+@gmail\\.com$")) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }




        boolean isMaster = radioButtonMaster.isChecked();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();

                        UserRegPage userRegPage = new UserRegPage(email,password,userName,isMaster,userId);
                        firestore.collection("Users")
                                        .document(userId).set(userRegPage)
                                        .addOnSuccessListener(aVoid ->{
                                            Toast.makeText(this,"Satty Reg", Toast.LENGTH_SHORT).show();

                                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putBoolean("isMaster", userRegPage.getMaster());
                                            editor.putString("name",userRegPage.getName());// Сохраняем значение
                                            editor.apply();
                                            Intent intent = new Intent(RegisterActivity.this,StartActivity.class);
                                            startActivity(intent);
                                        })
                                        .addOnFailureListener(e->{
                                            Toast.makeText(this,"Error saving data ",Toast.LENGTH_SHORT).show();
                                        });

                    } else {
                        Toast.makeText(this, "Auth failed", Toast.LENGTH_SHORT).show();
                    }
                });

        /*if (checkBoxClient.isChecked()){
            boolean isMaster = false;
            Intent intent = new Intent(this,StartActivity.class);
            intent.putExtra("isMaster",isMaster);
            startActivity(intent);

            UserRegPage userRegPage = new UserRegPage(email,password,userName,isMaster);
            firestore.collection("User")
                    .add(userRegPage)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RegisterActivity.this,"Reg Satty Otildi",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Kate!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else if (checkBoxMaster.isChecked()){
            boolean isMaster = true;
            Intent intent = new Intent(this, MasterRegPage.class);
            intent.putExtra("isMaster",isMaster);
            startActivity(intent);

            UserRegPage userRegPage = new UserRegPage(email,password,userName,isMaster);
            firestore.collection("User")
                    .add(userRegPage)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(RegisterActivity.this,"Reg Satty Otildi",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Kate!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }


*/
    }
}


