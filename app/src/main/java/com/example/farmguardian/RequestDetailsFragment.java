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

    private AcaretakerModel selectedCaretaker;
    private ListView listViewAcaretakersRDs;
    private AcaretakerAdapter adapter;
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.requestdetails, container, false);

        // Initialize the ListView
        listViewAcaretakersRDs = view.findViewById(R.id.listViewAcaretakersRD);
        retrieveSelectedCaretakersFromFirebase();





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
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(mContext.getApplicationContext(), "data fetch cancelled", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }



    private void showConfirmationDialog() {
    }

    @Override
    public void onConfirmClick() {

        }

    }

