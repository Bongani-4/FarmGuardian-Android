package com.example.farmguardian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.List;

public class HireAnimalCaretakerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_hire_animal_caretaker, container, false);

        // Fetch data from the 'ACprofile' table
        Database db = new Database(requireContext(), "FarmGuardian", null, 1);
        List<AcaretakerModel> caretakerList = db.getAcaretakerList();

        // Create the adapter
        AcaretakerAdapter adapter = new AcaretakerAdapter(requireContext(), R.layout.list_item_acaretaker, caretakerList);

        // Attach the adapter to the ListView
        ListView listView = view.findViewById(R.id.listViewAcaretakers2);
        listView.setAdapter(adapter);

        return view;
    }

}
