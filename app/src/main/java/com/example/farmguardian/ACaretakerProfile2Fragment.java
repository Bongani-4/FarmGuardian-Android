package com.example.farmguardian;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.widget.EditText;
import android.widget.Toast;

public class ACaretakerProfile2Fragment extends Fragment {

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

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Contacts = contacts.getText().toString();
                String Location = location.getText().toString();
                String Fullnames = fullnames.getText().toString();
                String Expirience = experience.getText().toString();
                int isavailable = CBavailable.isChecked() ? 1 : 0;

                Database db = new Database(requireContext(), "FarmGuardian", null, 1);
                db.saveProfile(username, Contacts, Location, Fullnames, Expirience, isavailable);

                if (db.isDataExist(username)) {
                    Toast.makeText(requireContext(), "Data updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Data saved", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}
