package com.example.record_tool.Dao;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.record_tool.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MasterActivity extends AppCompatActivity {
    private final static int PICK_IMAGE_REQUEST = 1;
    private FirebaseFirestore firestore;
    private Map<String, Object> listMaster;
    private TextView masterName;
    private EditText masterAdress;
    private EditText aboutMaster;
    private String listBooking;
    private TextView textViewNameMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_master);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        firestore = FirebaseFirestore.getInstance();
        // Запрос разрешений на доступ к хранилищу




        masterName = findViewById(R.id.textViewNameMaster);
        masterAdress = findViewById(R.id.idAdressMaster);
        aboutMaster = findViewById(R.id.idInfoMaster);
        textViewNameMaster = findViewById(R.id.textViewNameMaster);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String nameM = sharedPreferences.getString("name", "Default name");
        textViewNameMaster.setText(nameM);


        checkMasterExists(nameM); // Проверка наличия данных в Firestore


        String[] options = {"Техника", "Шаштараз", "Сұлулық салоны", "Клининг"};
        ListView listView = findViewById(R.id.listView);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                options
        );

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            listBooking = options[position];
            Toast.makeText(this, "Selected: " + listBooking, Toast.LENGTH_SHORT).show();
        });
        listBooking = options[0];


        ImageButton imgBtn = findViewById(R.id.imgInstallBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MasterActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
                Intent imgIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                imgIntent.setType("image/*");
                startActivityForResult(imgIntent, PICK_IMAGE_REQUEST);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {

            // Получаем изображение (например, выбранное пользователем)
            Uri uri = data.getData();
            ImageView imageView = findViewById(R.id.profileImage);
            imageView.setImageURI(uri);

            // Получаем ссылку на Firebase Storage
            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            // Указываем путь, по которому будет храниться изображение

            String fileUri = UUID.randomUUID().toString();
            StorageReference imgRef = storageReference.child("image/"+fileUri);

            // Загружаем файл
            imgRef.putFile(uri)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(this, "Сурет сакталды", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(task -> {
                                Toast.makeText(this, "Error" + task.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    );

        }
    }



    /**
     * Проверка наличия мастера в Firestore.
     */
    private void checkMasterExists(String masterName) {
        firestore.collection("Master")
                .whereEqualTo("Name", masterName) // Поиск по имени мастера
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        // Если данные существуют, сразу переходим в ListWithClient
                        Intent intent = new Intent(MasterActivity.this, ListWithClient.class);
                        startActivity(intent);
                        finish(); // Завершаем текущую Activity
                    } else {
                        // Если данные отсутствуют, оставляем текущую Activity
                        Toast.makeText(this, "Мастер деректерін енгізіңіз", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Деректерді тексеруде қате", Toast.LENGTH_SHORT).show();
                });
    }

    public void onClickEngizu(View view) {
        String infoMaster = aboutMaster.getText().toString();
        listMaster = new HashMap<>();
        listMaster.put("Name", masterName.getText().toString());
        listMaster.put("Adress", masterAdress.getText().toString());
        listMaster.put("Info", infoMaster);
        listMaster.put("Service", listBooking);

        firestore.collection("Master")
                .add(listMaster)
                .addOnCompleteListener(task -> {
                    Toast.makeText(MasterActivity.this, "Деректер сәтті енгізілді", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MasterActivity.this, ListWithClient.class);
                    startActivity(intent);
                    finish(); // Завершаем текущую Activity
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MasterActivity.this, "Деректерді енгізуде қателік орындалды", Toast.LENGTH_SHORT).show();
                });
    }

    public void OnViewList(View view) {
        Intent intent = new Intent(MasterActivity.this, ListWithClient.class);
        startActivity(intent);

       /* // Получаем ссылку на изображение
        StorageReference imageRef = storage.getReference().child("images/my_image.jpg");

        // Получаем ссылку для загрузки
        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
            // Загружаем изображение, используя URI
                Picasso.get().load(uri).into(imageView);
            }).addOnFailureListener(e -> {
            // Ошибка при получении изображения
                    Toast.makeText(this, "Ошибка при получении изображения", Toast.LENGTH_SHORT).show();
            });
*/
    }


}