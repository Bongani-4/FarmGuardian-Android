package com.example.farmguardian;

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
import androidx.fragment.app.DialogFragment;

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

        // Get the selected caretaker's name from arguments
        String caretakerName = getArguments().getString("selectedCaretakerFullnames");

        // Set the title and message
        dialogTitle.setText("Hire Confirmation");
        dialogMessage.setText("Do you want to hire " + caretakerName + " as the Animal Caretaker?");

        // Set up animations
        //Animation slideInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_bottoml);
       // view.setAnimation(slideInAnimation);

        // Set up button click listeners
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onConfirmClick();
                }
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
