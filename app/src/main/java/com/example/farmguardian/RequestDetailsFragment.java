package com.example.farmguardian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Collections;
import java.util.List;

public class RequestDetailsFragment extends Fragment implements ConfirmationHireFragment.ConfirmationDialogListener {

    private AcaretakerModel selectedCaretaker;
    private ListView listViewAcaretakersRDs;
    private AcaretakerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.requestdetails, container, false);

        // Initialize the ListView
        listViewAcaretakersRDs = view.findViewById(R.id.listViewAcaretakersRD);

        // Initialize the adapter
        adapter = new AcaretakerAdapter(requireContext(), R.layout.list_item_acaretaker, Collections.emptyList());


               listViewAcaretakersRDs.setAdapter(adapter);



        // Check if selected caretaker is not null
        if (selectedCaretaker != null) {
            // Update the ListView with the selected caretaker
            updateListView(selectedCaretaker);
        }

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Call onConfirmClick when the fragment is created
        onConfirmClick();
    }

    // Method to update ListView with selected caretaker
    private void updateListView(AcaretakerModel caretaker) {
        // Create a new list with only the selected caretaker
        List<AcaretakerModel> selectedList = Collections.singletonList(caretaker);


        // Add the selected caretaker to the adapter
        adapter.addAll(selectedList);

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }

    // Setter method to update the selected caretaker
    public void setSelectedCaretaker(AcaretakerModel caretaker) {
        this.selectedCaretaker = caretaker;
        // Update the ListView with the new selected caretaker
        updateListView(selectedCaretaker);
    }

    private void showConfirmationDialog() {
    }

    @Override
    public void onConfirmClick() {

    }
}
