package com.example.farmguardian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AcaretakerAdapter extends ArrayAdapter<AcaretakerModel> {

    public AcaretakerAdapter(Context context, List<AcaretakerModel> caretakerList) {
        super(context, 0, caretakerList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        AcaretakerModel caretaker = getItem(position);

        // inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_find_acaretaker, parent, false);
        }


        TextView textViewFullNames = convertView.findViewById(R.id.textViewFullNames);
        TextView textViewLocation = convertView.findViewById(R.id.textViewLocation);
        TextView textViewContact = convertView.findViewById(R.id.textViewContact);
        TextView textViewExperience = convertView.findViewById(R.id.textViewExperience);
        TextView textViewAvailable = convertView.findViewById(R.id.textViewAvailable);

        // Populate the data into the template view
        assert caretaker != null;
        textViewFullNames.setText(caretaker.getFullNames());
        textViewLocation.setText(caretaker.getLocation());
        textViewContact.setText(caretaker.getContact());
        textViewExperience.setText(caretaker.getExperience());
        textViewAvailable.setText(caretaker.isAvailable() == 1 ? "Yes" : "No");


        return convertView;
    }

    @Override
    public AcaretakerModel getItem(int position) {
        // Override getItem to return the item at the specified position
        return super.getItem(position);
    }

}
