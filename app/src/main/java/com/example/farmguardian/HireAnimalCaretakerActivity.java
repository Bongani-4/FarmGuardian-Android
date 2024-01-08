package com.example.farmguardian;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class HireAnimalCaretakerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hire_animal_caretaker);

        // Fetch data from the 'ACprofile' table
        Database db = new Database(getApplicationContext(), "FarmGuardian", null, 1);
        List<AcaretakerModel> caretakerList = db.getAcaretakerList();

        // Create the adapter
        AcaretakerAdapter adapter = new AcaretakerAdapter(this, R.layout.list_item_acaretaker, caretakerList);

        // Attach the adapter to the ListView
        ListView listView = findViewById(R.id.listViewAcaretakers);
        listView.setAdapter(adapter);
    }
}
