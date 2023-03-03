package co.unumid.webwallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.unumid.webwallet.R
import co.unumid.webwallet.databinding.FragmentAccountCreatedBinding
import co.unumid.unumidverified.UnumID
import co.unumid.unumidverified.network.requests.CredentialData
import co.unumid.unumidverified.network.requests.IssueCredentialsRequest
import co.unumid.unumidverified.network.responses.IssueCredentialsData

class AccountCreatedFragment : Fragment() {

    private lateinit var binding: FragmentAccountCreatedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountCreatedBinding.inflate(inflater, container, false)

        /**
         * This is the button click that will start the verification process.
         */
        binding.button.setOnClickListener {
            val request = IssueCredentialsRequest(
                email = "test@unumid.co",
                phone = "1234567890",
                otp = 123456,
                credentials = listOf(
                    CredentialData(
                        type = "TestCredential",
                        date = IssueCredentialsData(ssn = "111-22-3333"),
                        expirationDate = 1701483024054
                    )
                )
            )
            UnumID.launchActivate(request, "hooli", object : UnumID.UnumDialogComplete {
                override fun onSuccess() {
                    onCallback()
                }

                override fun onError() {
                    // do something on error
                }
            })
        }

        return binding.root
    }

    private fun onCallback() {
        findNavController().navigate(R.id.action_accountCreatedFragment_to_verifiedFragment)
    }
}