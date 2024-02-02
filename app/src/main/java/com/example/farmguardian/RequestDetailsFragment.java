package com.example.farmguardian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class RequestDetailsFragment extends Fragment implements ConfirmationHireFragment.ConfirmationDialogListener {

    private String selectedCaretakerFullnames;
    private String selectedCaretakerContacts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.requestdetails, container, false);

        // Fetch data from the 'ACprofile' table
        Database db = new Database(requireContext(), "FarmGuardian", null, 1);
        List<AcaretakerModel> caretakerList = db.getAcaretakerList();

        // Create the adapter
        AcaretakerAdapter adapter = new AcaretakerAdapter(requireContext(), R.layout.list_item_acaretaker, caretakerList);

        // Attach the adapter to the ListView
        ListView listView = view.findViewById(R.id.listViewAcaretakersRD);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get the selected caretaker's full names
                selectedCaretakerFullnames = caretakerList.get(position).getFullNames();
                selectedCaretakerContacts   = caretakerList.get(position).getContact();

                showConfirmationDialog();
            }
        });

        return view;
    }

    private void showConfirmationDialog() {
        ConfirmationHireFragment confirmationDialog = new ConfirmationHireFragment();

        // Pass the selected caretaker's full names to the fragment
        Bundle args = new Bundle();
        args.putString("selectedCaretakerFullnames", selectedCaretakerFullnames);
        args.putString("selectedCaretakerContacts", selectedCaretakerContacts);
        confirmationDialog.setArguments(args);


        confirmationDialog.setConfirmationDialogListener(this);


        confirmationDialog.show(getChildFragmentManager(), "ConfirmationDialog");
    }

    @Override
    public void onConfirmClick() {
        //  logic applied elsewhere better,follow 'usage' to trace it
            }
}
