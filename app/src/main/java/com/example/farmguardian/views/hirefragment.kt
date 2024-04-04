package com.example.farmguardian.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.farmguardian.Database
import com.example.farmguardian.views.CaretakerAdapter
import com.example.farmguardian.Models.AcaretakerModel
import com.example.farmguardian.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


public class hirefrgament : Fragment(), ConfirmationHireFragment.ConfirmationDialogListener {

     private var selectedCaretakerFullnames = ""
    private var selectedCaretakerContacts = ""

    private lateinit var usersRef: DatabaseReference





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize usersRef reference
        usersRef = FirebaseDatabase.getInstance().getReference("users")


        val view = inflater.inflate(R.layout.activity_hire_animal_caretaker, container, false)
        val listView: ListView = view.findViewById(R.id.listViewAcaretakers2)
        val progressBar: ProgressBar = view.findViewById(R.id.Barsprogress)

        // Set the progress tint color to green
        progressBar.indeterminateTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.DarkGreen)


        // Show the progress bar
        progressBar.visibility = View.VISIBLE

        // Initialize Database
        val database = Database()

        // launching lifecycleScope to perform database operation in a coroutine in IO dispatchers
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Retrieve list of animal caretakers from Firebase
                val caretakerList = database.getAcaretakerList()


                /** to be  only to be executed once
                 * database.initializeDatabase() **/

                // switch to the main dispatcher before updating the UI
                withContext(Dispatchers.Main) {
                    // Hide the progress bar
                    progressBar.visibility = View.GONE
                    val adapter =
                        CaretakerAdapter(
                            requireContext(),
                            R.layout.list_item_acaretaker,
                            caretakerList
                        )
                    listView.adapter = adapter

                    listView.setOnItemClickListener { _, _, position, _ ->
                        val selectedCaretaker = caretakerList[position]
                        selectedCaretakerFullnames = selectedCaretaker.fullNames
                        selectedCaretakerContacts = selectedCaretaker.contact
                        showConfirmationDialog()
                    }
                }
            } catch (e: Exception) {
                // Handle exceptions
                withContext(Dispatchers.Main) {
                    // Show error toast for exceptions handling database queries
                    Toast.makeText(
                        requireContext(),
                        "Error loading caretaker data: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Hide the progress bar
                    progressBar.visibility = View.GONE
                }
            }
        }
        return view
    }


    private fun showConfirmationDialog() {
        val confirmationDialog =
            ConfirmationHireFragment()

        // Pass the selsected caretaker's full names to the fragment
        val args = Bundle()
        args.putString("selectedCaretakerFullnames", selectedCaretakerFullnames)
        args.putString("selectedCaretakerContacts", selectedCaretakerContacts)
        confirmationDialog.arguments = args
        confirmationDialog.setConfirmationDialogListener(this)
        confirmationDialog.show(childFragmentManager, "ConfirmationDialog")

    }


    override fun onConfirmClick() {
        // Get selected caretaker details from arguments

        // Validate if the details are not empty
        if (selectedCaretakerFullnames.isNotEmpty() && selectedCaretakerContacts.isNotEmpty()) {
            // Create a new instance of AcaretakerModel with selected caretaker details
            val selectedCaretaker =
                AcaretakerModel(
                    selectedCaretakerFullnames,
                    "Na",
                    selectedCaretakerContacts,
                    "Na",
                    1
                )


            //save selected animal caretakers to the DB
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                val selectedCaretakersRef = FirebaseDatabase.getInstance().reference
                    .child("users")
                    .child(currentUser.uid)
                    .child("selectedCaretakers")
                selectedCaretakersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            // The "selectedCaretakers" node does not exist, create it and add selected caretaker
                            usersRef = FirebaseDatabase.getInstance().reference.child("users")
                                .child(currentUser.uid)
                                .child("selectedCaretakers")
                            usersRef.push().setValue(selectedCaretaker)

                            Toast.makeText(
                                requireContext(),
                                "candidate saved, to be hired.",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            // The "selectedCaretakers" node already exists, add selected caretaker
                            selectedCaretakersRef.push().setValue(selectedCaretaker)
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(
                            requireContext(),
                            "Saving candidate cancelled.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            } else {

                Toast.makeText(
                    requireContext(),
                    "Selected caretaker details are empty.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }
}
