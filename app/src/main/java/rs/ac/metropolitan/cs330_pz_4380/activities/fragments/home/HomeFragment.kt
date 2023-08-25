package rs.ac.metropolitan.cs330_pz_4380.activities.fragments.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import rs.ac.metropolitan.cs330_pz_4380.R
import rs.ac.metropolitan.cs330_pz_4380.databinding.FragmentHomeBinding
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val section1LinearLayout = root.findViewById<LinearLayout>(R.id.section1LinearLayout)
        val section2LinearLayout = root.findViewById<LinearLayout>(R.id.section2LinearLayout)
        val section3LinearLayout = root.findViewById<LinearLayout>(R.id.section3LinearLayout)

        // Set background colors for each section
        section1LinearLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        section2LinearLayout.setBackgroundColor(Color.parseColor("#242424"))
        section3LinearLayout.setBackgroundColor(Color.parseColor("#FFA458"))

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
