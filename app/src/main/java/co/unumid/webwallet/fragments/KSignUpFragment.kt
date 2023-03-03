package co.unumid.webwallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.unumid.unumidverified.UnumID
import co.unumid.unumidverified.network.requests.HasMatchingCredentialsRequest
import co.unumid.webwallet.MainActivity
import co.unumid.webwallet.R
import co.unumid.webwallet.databinding.FragmentKSignUpBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class KSignUpFragment : Fragment() {

    private lateinit var binding: FragmentKSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKSignUpBinding.inflate(inflater, container, false)

        /**
         * Check to see if there are matching credentials for the user.
         */
        binding.button.setOnClickListener {
            UnumID.hasMatchingCredentials(
                "hooli", true, object : UnumID.UnumDialogComplete {
                    override fun onSuccess() {
                        onCallback()
                    }

                    override fun onError() {
                        // do something on error
                    }
                }, HasMatchingCredentialsRequest(
                    email = "test@unumid.co",
                    phone = "1234567890",
                    emptyList()
                )
            )
        }

        binding.submitError.setOnClickListener {
            (requireActivity() as? MainActivity)?.showDialog()
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        (activity as? MainActivity)?.let {
            if (it.hasError) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Error")
                    .setMessage(it.errorMessage)
                    .setPositiveButton("OK") { dialog, _ ->
                        it.hasError = false
                        it.errorMessage = ""
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }


    private fun onCallback() {
        findNavController().navigate(R.id.action_KSignUpFragment_to_KVerifiedFragment)
    }
}