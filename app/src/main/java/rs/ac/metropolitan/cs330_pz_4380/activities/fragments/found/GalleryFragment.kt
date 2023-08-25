package rs.ac.metropolitan.cs330_pz_4380.activities.fragments.found

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment
import rs.ac.metropolitan.cs330_pz_4380.R
import rs.ac.metropolitan.cs330_pz_4380.data.PetDBAdapter
import rs.ac.metropolitan.cs330_pz_4380.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("Range")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val db = PetDBAdapter(requireContext())
        db.open()

//        db.insertPets()

        val userList: ArrayList<HashMap<String, String>> = ArrayList()
        val cursor = db.getPetsByStatus("Found")

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val petMap = HashMap<String, String>()
                petMap[PetDBAdapter.KEY_ROWID] = cursor.getString(cursor.getColumnIndex(PetDBAdapter.KEY_ROWID))
                petMap[PetDBAdapter.KEY_NAME] = cursor.getString(cursor.getColumnIndex(PetDBAdapter.KEY_NAME))
                petMap[PetDBAdapter.KEY_DATE] = cursor.getString(cursor.getColumnIndex(PetDBAdapter.KEY_DATE))
                petMap[PetDBAdapter.KEY_RACE] = cursor.getString(cursor.getColumnIndex(PetDBAdapter.KEY_RACE))
                petMap[PetDBAdapter.KEY_PICTURE] = cursor.getString(cursor.getColumnIndex(PetDBAdapter.KEY_PICTURE))
                petMap[PetDBAdapter.KEY_FOUND] = cursor.getString(cursor.getColumnIndex(PetDBAdapter.KEY_FOUND))
                userList.add(petMap)
            } while (cursor.moveToNext())

            cursor.close()
        }

        db.close()

        val lv = binding.userList
        val adapter: ListAdapter = SimpleAdapter(
            requireContext(),
            userList,
            R.layout.list_row,
            arrayOf(PetDBAdapter.KEY_NAME, PetDBAdapter.KEY_DATE, PetDBAdapter.KEY_RACE, PetDBAdapter.KEY_PICTURE),
            intArrayOf(R.id.name, R.id.date, R.id.race)
        )
        lv.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
