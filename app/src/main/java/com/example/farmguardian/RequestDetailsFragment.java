package com.example.farmguardian;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class RequestDetailsFragment extends Fragment implements ConfirmationHireFragment.ConfirmationDialogListener {

    private String selectedCaretakerFullnames = "";
    private String selectedCaretakerContacts = "";

    private AcaretakerModel selectedCaretaker;
    private ListView listViewAcaretakersRDs, ListViewHIred;
    private AcaretakerAdapter adapter;
    private AcaretakerAdapter hiredAdapter;
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.requestdetails, container, false);

        // Initialize the ListViews
        ListViewHIred = view.findViewById(R.id.listViewAcaretakersHiredRD);
        listViewAcaretakersRDs = view.findViewById(R.id.listViewAcaretakersRD);
        retrieveSelectedCaretakersFromFirebase();
        retrieveHiredCaretakersFromFirebase();


        return view;
    }

    //Retrieve selected animal caretaker information from Firebase

    private void retrieveSelectedCaretakersFromFirebase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String loggedInUserId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(loggedInUserId)
                    .child("selectedCaretakers");
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    List<AcaretakerModel> caretakerList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AcaretakerModel caretaker = snapshot.getValue(AcaretakerModel.class);
                        caretakerList.add(caretaker);
                    }
                    // Create adapter if null or update existing adapter
                    if (adapter == null) {
                        adapter = new AcaretakerAdapter(requireContext(), R.layout.list_item_acaretaker, caretakerList);
                        listViewAcaretakersRDs.setAdapter(adapter);
                    } else {

                        adapter.clear();
                        adapter.addAll(caretakerList);
                        adapter.notifyDataSetChanged();
                    }listViewAcaretakersRDs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            AcaretakerModel selectedCaretaker = caretakerList.get(position);
                            selectedCaretakerFullnames = selectedCaretaker.getFullNames();
                            selectedCaretakerContacts = selectedCaretaker.getContact();
                            // Get the ID of the selected caretaker
                            String selectedCaretakerId = selectedCaretaker.getId();
                            if (selectedCaretakerId != null && !selectedCaretakerId.isEmpty()) {

                            }
                            showConfirmationDialog();
                        }
                    });



                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(mContext.getApplicationContext(), "data fetch cancelled", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
    public  void retrieveHiredCaretakersFromFirebase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String loggedInUserId = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(loggedInUserId)
                    .child("HiredCaretakers");
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<AcaretakerModel> hiredCaretakerList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AcaretakerModel hiredCaretaker = snapshot.getValue(AcaretakerModel.class);
                        hiredCaretakerList.add(hiredCaretaker);
                    }
                    // Create adapter if null or update existing adapter
                    if (hiredAdapter == null) {
                        hiredAdapter = new AcaretakerAdapter(requireContext(), R.layout.list_item_acaretaker, hiredCaretakerList);
                        ListViewHIred.setAdapter(hiredAdapter);
                    } else {
                        hiredAdapter.clear();
                        hiredAdapter.addAll(hiredCaretakerList);
                        hiredAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(mContext.getApplicationContext(), "Data fetch cancelled", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void showConfirmationDialog() {
        ConfirmationHireFragment confirmationDialog = new ConfirmationHireFragment();

// Pass the selected caretaker's full names to the fragment
        Bundle args = new Bundle();
        args.putString("selectedCaretakerFullnamesHire", selectedCaretakerFullnames);
        args.putString("selectedCaretakerContactsHIre", selectedCaretakerContacts);
        confirmationDialog.setArguments(args);
        confirmationDialog.setConfirmationDialogListener(this);
        confirmationDialog.show(getChildFragmentManager(), "ConfirmationDialog");

    }

    @Override
    public void onConfirmClick() {
        // Get selected caretaker details

        // Validate if the details are not empty
        if (!selectedCaretakerFullnames.isEmpty() && !selectedCaretakerContacts.isEmpty()) {
            // Create a new instance of AcaretakerModel with selected caretaker details
            AcaretakerModel selectedCaretaker = new AcaretakerModel(
                    selectedCaretakerFullnames,
                    "Na",
                    selectedCaretakerContacts,
                    "Na",
                    1
            );

            // Save selected animal caretakers to the DB
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                DatabaseReference HiredCaretakersRef = FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(currentUser.getUid())
                        .child("HiredCaretakers");
                HiredCaretakersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            // The "HiredCaretakers" node does not exist, create it and add selected caretaker
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users")
                                    .child(currentUser.getUid())
                                    .child("HiredCaretakers");
                            DatabaseReference pushedRef = usersRef.push();
                            String selectedCaretakerId = pushedRef.getKey();
                            pushedRef.setValue(selectedCaretaker);


                            // Remove the selected caretaker from selectedCaretakers node
                            assert selectedCaretakerId != null;
                            DatabaseReference selectedCaretakerRef = FirebaseDatabase.getInstance().getReference().child("users")
                                    .child(currentUser.getUid())
                                    .child("selectedCaretakers")
                                    .child(selectedCaretakerId);
                            selectedCaretakerRef.removeValue();

                            Toast.makeText(
                                    requireContext(),
                                    "candidate Hired.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        } else {
                            // The "selectedCaretakers" node already exists, add selected caretaker
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users")
                                    .child(currentUser.getUid())
                                    .child("HiredCaretakers");
                            DatabaseReference pushedRef = usersRef.push();
                            String selectedCaretakerId = pushedRef.getKey();
                            pushedRef.setValue(selectedCaretaker);

                            // Remove the selected caretaker from selectedCaretakers node
                            DatabaseReference selectedCaretakerRef = FirebaseDatabase.getInstance().getReference().child("users")
                                    .child(currentUser.getUid())
                                    .child("selectedCaretakers")
                                    .child(selectedCaretakerId);
                            selectedCaretakerRef.removeValue();
                            removeSelectedCaretaker(selectedCaretakerFullnames);

                            Toast.makeText(
                                    requireContext(),
                                    "candidate Hired.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(
                                requireContext(),
                                "Saving candidate cancelled.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
            } else {
                Toast.makeText(
                        requireContext(),
                        "Selected caretaker details are empty.",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }private void removeSelectedCaretaker(String fullName) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            DatabaseReference caretakersRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(currentUser.getUid())
                    .child("selectedCaretakers");

            // Query the database to find the caretaker with the matching full name
            caretakersRef.orderByChild("fullNames").equalTo(fullName)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Get the unique identifier associated with the matched caretaker
                                String selectedCaretakerId = snapshot.getKey();

                                // Remove the caretaker using the unique identifier
                                if (selectedCaretakerId != null) {
                                    DatabaseReference selectedCaretakerRef = FirebaseDatabase.getInstance().getReference()
                                            .child("users")
                                            .child(currentUser.getUid())
                                            .child("selectedCaretakers")
                                            .child(selectedCaretakerId);
                                    selectedCaretakerRef.removeValue(new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                            if(error != null) {

                                            // Failed to remove the selected caretaker
                                                Toast.makeText(mContext, "Failed to remove selected caretaker.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle error
                        }
                    });
        }
    }

}