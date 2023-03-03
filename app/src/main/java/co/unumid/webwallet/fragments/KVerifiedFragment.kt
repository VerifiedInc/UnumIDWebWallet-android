package co.unumid.webwallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import co.unumid.webwallet.MainActivity
import co.unumid.webwallet.databinding.FragmentKVerifiedBinding

class KVerifiedFragment : Fragment() {

    private lateinit var binding: FragmentKVerifiedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKVerifiedBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            Toast.makeText(requireContext(), "Account Creation Successful", Toast.LENGTH_SHORT)
                .show()
        }

        binding.submitError.setOnClickListener {
            (requireActivity() as? MainActivity)?.showDialog()
        }

        return binding.root
    }

}