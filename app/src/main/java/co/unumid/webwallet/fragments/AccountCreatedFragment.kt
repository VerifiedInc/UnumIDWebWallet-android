package co.unumid.webwallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.unumid.webwallet.MainActivity
import co.unumid.webwallet.R
import co.unumid.webwallet.databinding.FragmentAccountCreatedBinding

class AccountCreatedFragment : Fragment() {

    private lateinit var binding: FragmentAccountCreatedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountCreatedBinding.inflate(inflater, container, false)

        /**
         * This is the button click theat will start the verification process.
         */
        binding.button.setOnClickListener {
            val activity = requireActivity() as? MainActivity
            activity?.performVerification(this::onCallback)
        }

        return binding.root
    }

    private fun onCallback() {
        findNavController().navigate(R.id.action_accountCreatedFragment_to_verifiedFragment)
    }
}