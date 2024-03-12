package com.example.farmguardian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
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
        //  logic applied elsewhere better,follow 'usage' to trace it
    }
}
