package com.example.farmguardian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.core.content.ContextCompat;

import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BecomeAnimalcaretakerFragment extends Fragment {

    EditText fullnames, experience, location, contacts;
    String username;

    CheckBox CBavailable;
    Button btnDone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_acaretaker_profile2, container, false);

        btnDone = view.findViewById(R.id.buttonSetACprofile);
        CBavailable = view.findViewById(R.id.checkBoxAVailable);
        contacts = view.findViewById(R.id.editTextContacts);
        location = view.findViewById(R.id.editTextLocation);
        fullnames = view.findViewById(R.id.editTextProfileNames);
        experience = view.findViewById(R.id.editTextExperiance);

        btnDone.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.DarkGreen));


        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Contacts = contacts.getText().toString();
                String Location = location.getText().toString();
                String Fullnames = fullnames.getText().toString();
                String Expirience = experience.getText().toString();
                int isavailable = CBavailable.isChecked() ? 1 : 0;

                if (Expirience.length() == 0 || Fullnames.length() == 0 || Contacts.length() ==0 || Location.length() ==0) {
                    Toast.makeText(requireContext(), "All details should be filled!", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        String userId = currentUser.getUid();

                        // Save profile to Firebase Realtime Database under "AcUser" node
                        DatabaseReference acUserRef = FirebaseDatabase.getInstance().getReference("ACUser").child(userId);
                        acUserRef.child("username").setValue(username);
                        acUserRef.child("contacts").setValue(Contacts);
                        acUserRef.child("location").setValue(Location);
                        acUserRef.child("fullnames").setValue(Fullnames);
                        acUserRef.child("experience").setValue(Expirience);
                        acUserRef.child("available").setValue(isavailable);

                        // Check if data exists
                        acUserRef.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Toast.makeText(requireContext(), "Data updated", Toast.LENGTH_SHORT).show();
                                    contacts.setText("");
                                  location.setText("");
                                   fullnames.setText("");
                                    experience.setText("");


                                } else {
                                    Toast.makeText(requireContext(), "Data saved", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(requireContext(), "Data save process canceled", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }

            }
        });

        return view;
    }
}
