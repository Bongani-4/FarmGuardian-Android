package com.example.farmguardian.views;

import static kotlinx.coroutines.time.TimeKt.delay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.farmguardian.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmationHireFragment extends DialogFragment {

    private ConfirmationDialogListener listener;

    interface ConfirmationDialogListener {
        void onConfirmClick();
    }

    void setConfirmationDialogListener(ConfirmationDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Create a custom layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_confirmation_hire, null);

        // Set up views
        TextView dialogTitle = view.findViewById(R.id.dialogTitle);
        TextView dialogMessage = view.findViewById(R.id.dialogMessage);
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnNo = view.findViewById(R.id.btnNo);
        Button btncall = view.findViewById(R.id.btncall);


        btnNo.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.DarkGreen));
        btnYes.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.DarkGreen));
        btncall.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.DarkGreen));
        // Get the selected caretaker's name from arguments
        String caretakerName = getArguments().getString("selectedCaretakerFullnames");

        // Set the title and message
        dialogTitle.setText("Hire Confirmation");
        if(caretakerName != null) {
            dialogMessage.setText("What do you want to do with animal caretaker " + caretakerName + "?");
        }

        // Set up animations
        //Animation slideInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottoml);
       // view.setAnimation(slideInAnimation);

        // Set up button click listeners
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                assert listener != null;
                listener.onConfirmClick();

                assert getArguments() != null;
                String contacts = getArguments().getString("selectedCaretakerContacts");
                String caretakerName = getArguments().getString("selectedCaretakerFullnames");
                // Save hired caretaker to the "ToBeHired" node
                DatabaseReference toBeHiredCaretakerRef = FirebaseDatabase.getInstance().getReference("toBeHired").push();
                toBeHiredCaretakerRef.child("caretaker_name").setValue(caretakerName);
                toBeHiredCaretakerRef.child("contact").setValue(contacts);

                // Clear arguments
                getArguments().clear();

            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(view);

        return builder.create();
    }
}
