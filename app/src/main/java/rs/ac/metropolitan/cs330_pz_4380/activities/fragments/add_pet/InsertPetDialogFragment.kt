package rs.ac.metropolitan.cs330_pz_4380.activities.fragments.add_pet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import rs.ac.metropolitan.cs330_pz_4380.R
import rs.ac.metropolitan.cs330_pz_4380.data.PetDBAdapter

class InsertPetDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_insert_pet_dialog, container, false)

        val statusOptions = listOf("Lost", "Found")
        val editTextName = view.findViewById<EditText>(R.id.editTextName)
        val editTextDate = view.findViewById<EditText>(R.id.editTextDate)
        val editTextRace = view.findViewById<EditText>(R.id.editTextRace)
        val editTextPicture = view.findViewById<EditText>(R.id.editTextPicture)
        val spinnerStatus = view.findViewById<Spinner>(R.id.spinnerPetType)
        var selectedStatus: String? = null
        val adapterStatus =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, statusOptions)
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = adapterStatus


        val editTextFound = view.findViewById<EditText>(R.id.editTextFound)

        spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                selectedStatus = statusOptions[position] // This is the selected status
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }


        val buttonSave = view.findViewById<Button>(R.id.buttonSave)

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val date = editTextDate.text.toString()
            val race = editTextRace.text.toString()
            val picture = editTextPicture.text.toString()

//            val found = editTextFound.text.toString()

            val petDBAdapter = PetDBAdapter(requireContext())
            petDBAdapter.open()
            petDBAdapter.insertPet(name, race, date, picture, selectedStatus, false, 3)
            petDBAdapter.close()

            Toast.makeText(requireContext(), "Pet inserted successfully", Toast.LENGTH_SHORT).show()

            dismiss()
        }


        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
        }
    }
}
