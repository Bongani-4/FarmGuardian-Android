package com.example.farmguardian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
public class AcaretakerAdapter extends ArrayAdapter<AcaretakerModel> {

    public AcaretakerAdapter(Context context, int resource, List<AcaretakerModel> caretakerList) {
        super(context, resource, caretakerList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        AcaretakerModel caretaker = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_acaretaker, parent, false);
        }

        TextView textViewFullNames = convertView.findViewById(R.id.textViewFullNames);
        TextView textViewLocation = convertView.findViewById(R.id.textViewLocation);
        TextView textViewContact = convertView.findViewById(R.id.textViewContact);
        TextView textViewExperience = convertView.findViewById(R.id.textViewExperience);
        TextView textViewAvailable = convertView.findViewById(R.id.textViewAvailable);

        if (caretaker != null) {
            textViewFullNames.setText(caretaker.getFullNames());
            textViewLocation.setText(caretaker.getLocation());
            textViewContact.setText(caretaker.getContact());
            textViewExperience.setText(caretaker.getExperience());
            textViewAvailable.setText(caretaker.isAvailable() == 1 ? "Yes" : "No");
        }

        return convertView;
    }
}
