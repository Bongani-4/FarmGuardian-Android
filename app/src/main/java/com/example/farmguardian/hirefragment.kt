package com.example.farmguardian

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


public class hirefrgament : Fragment(), ConfirmationHireFragment.ConfirmationDialogListener {

    private var listener: ConfirmationDialogListener? = null
    private var selectedCaretakerFullnames = ""
    private var selectedCaretakerContacts = ""

    interface ConfirmationDialogListener {
        fun onConfirmClick()
    }

    fun setConfirmationDialogListener(listener: ConfirmationDialogListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_hire_animal_caretaker, container, false)

        val listView: ListView = view.findViewById(R.id.listViewAcaretakers2)

        // launching lifecycleScope to perform database operation in a coroutine in IO dispatchers
        lifecycleScope.launch(Dispatchers.IO) {
            try {


                val db = Database(requireContext(), "FarmGuardian", null, 1)
                val caretakerList = db.getAcaretakerList()

                // switch to the main dispatcher before updating the UI
                withContext(Dispatchers.Main) {
                    val adapter = AcaretakerAdapter(
                        requireContext(),
                        R.layout.list_item_acaretaker,
                        caretakerList
                    )
                    listView.adapter = adapter


                    listView.setOnItemClickListener { _, _, position, _ ->
                        selectedCaretakerFullnames = caretakerList[position].fullNames
                        selectedCaretakerContacts = caretakerList[position].contact
                        showConfirmationDialog()
                    }
                }
            }catch (e: Exception) {

                withContext(Dispatchers.Main) {
                    //  an error toast for exceptions handling db queries
                    Toast.makeText(requireContext(), "Error loading caretaker data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }


    private fun showConfirmationDialog() {
        val confirmationDialog = ConfirmationHireFragment()

        // Pass the selsected caretaker's full names to the fragment
        val args = Bundle()
        args.putString("selectedCaretakerFullnames", selectedCaretakerFullnames)
        args.putString("selectedCaretakerContacts", selectedCaretakerContacts)
        confirmationDialog.arguments = args
        confirmationDialog.setConfirmationDialogListener(this)
        confirmationDialog.show(childFragmentManager, "ConfirmationDialog")

    }

    override fun onConfirmClick() {


        // Validate if the details are not empty
        if (selectedCaretakerFullnames.isNotEmpty() && selectedCaretakerContacts.isNotEmpty()) {
            // Create a new node for the logged-in user in the Realtime Database
            createNewNodeForLoggedInUser(selectedCaretakerFullnames, selectedCaretakerContacts)




        } else {
            // Handle the case where selected caretaker details are empty
            Toast.makeText(requireContext(), "Selected caretaker details are empty.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNewNodeForLoggedInUser(selectedCaretakerFullnames: String, selectedCaretakerContacts: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val userNode = FirebaseDatabase.getInstance().getReference("users").child(currentUser.uid)

            // Create an instance of AcaretakerModel with selected caretaker details
            val acaretakerModel = AcaretakerModel(selectedCaretakerFullnames, "Default Location", selectedCaretakerContacts, "Default Experience", 1)

            // Set the AcaretakerModel as a child node under the logged-in user
            userNode.child("acaretaker").setValue(acaretakerModel)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("hirefrgament", "Node creation for logged-in user successful")
                        // Handle success if needed
                    } else {
                        Log.e("hirefrgament", "Node creation failed: ${task.exception}")
                        // Handle failure if needed
                    }
                }
        }
    }

}
