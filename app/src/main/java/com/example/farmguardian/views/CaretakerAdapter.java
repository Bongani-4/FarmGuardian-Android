package com.example.farmguardian.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.farmguardian.Models.AcaretakerModel;
import com.example.farmguardian.R;

import java.util.List;
public class CaretakerAdapter extends ArrayAdapter<AcaretakerModel> {

    // Constructor
    public CaretakerAdapter(Context context, int resource, List<AcaretakerModel> caretakerList) {
        super(context, resource, caretakerList);
    }

    // Get view
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        // Get caretaker object
        AcaretakerModel caretaker = getItem(position);

        // Inflate layout if convertView is null
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_acaretaker, parent, false);
        }

        // Initialize TextViews
        TextView textViewFullNames = convertView.findViewById(R.id.textViewFullNames);
        TextView textViewLocation = convertView.findViewById(R.id.textViewLocation);
        TextView textViewContact = convertView.findViewById(R.id.textViewContact);
        TextView textViewExperience = convertView.findViewById(R.id.textViewExperience);
        TextView textViewAvailable = convertView.findViewById(R.id.textViewAvailable);

        // Populate TextViews with caretaker data
        if (caretaker != null) {
            textViewFullNames.setText(caretaker.getFullNames());
            textViewLocation.setText("Location: " + caretaker.getLocation());
            textViewContact.setText("Contact: " + caretaker.getContact());
            textViewExperience.setText("Experience: " + caretaker.getExperience() + " years");
            textViewAvailable.setText("Available: " + (caretaker.isAvailable() == 1 ? "Yes" : "No"));
        }

        // Return the populated view
        return convertView;
    }
}
