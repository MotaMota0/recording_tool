package com.example.record_tool;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.record_tool.service.InfoPageAbtMaster;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class testLoadMasterData {

    private FirebaseFirestore mockFirestore;
    private QuerySnapshot mockSnapshot;
    private QueryDocumentSnapshot mockDocument;
    private InfoPageAbtMaster activity;

    @Before
    public void setUp() {
        // Mock Firestore и QuerySnapshot
        mockFirestore = mock(FirebaseFirestore.class);
        mockSnapshot = mock(QuerySnapshot.class);
        mockDocument = mock(QueryDocumentSnapshot.class);

        when(mockDocument.getString("Name")).thenReturn("Test Master");
        when(mockDocument.getString("Info")).thenReturn("Test Info");
        when(mockSnapshot.isEmpty()).thenReturn(false);
        when(mockSnapshot.getDocuments()).thenReturn(Collections.singletonList(mockDocument));
        when(mockFirestore.collection("Master").whereEqualTo("Name", "Test Master").get())
                .thenReturn(Tasks.forResult(mockSnapshot));

        // Создаем экземпляр активности
        activity = new InfoPageAbtMaster();
        activity.firestore = mockFirestore;
    }

    @Test
    public void testLoadMasterData() {
        // Добавьте метод loadMasterData в InfoPageAbtMaster
        activity.loadMAsterData("Test Master");

        // Проверка данных (потребуется реализация метода)
        assertEquals("Test Master", activity.masterName.getText().toString());
        assertEquals("Test Info", activity.infoMaster.getText().toString());
    }
}

