package co.unumid.webwallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.unumid.unumidwebauth.WebWallet
import java.util.*


/**
 * An example of how to use the web wallet verification library.
 *
 * Step one: start a new verification request. This will start the verification flow and return
 * the results to the client app through a deep link.
 *
 * Step two: Get data from the deeplink and pass them to the web wallet library.
 */

@Suppress("KotlinConstantConditions")
class MainActivity : AppCompatActivity() {

    private var onSuccessCallback: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // These values will be provided to you by UnumID
        val setIssuer = "Your issuer DID"
        val setPR = "Your presentation request ID"
        val appId = "Your app ID"
        val appKey = "Your app key"
        val workflowId = "Your workflow ID"

        WebWallet.setStateValues(setIssuer, setPR, appId, appKey, workflowId)

        /**
         * Step 2:
         * After a verification attempt has been made, the results will be returned though a deep link.
         * These results can be sent back to the web wallet library. In the case of an error, the client
         * app can display the error or handle it in some other way.
         */
        val intent = intent
        val data = intent.data
        if (data != null) {
            if (data.toString().contains("://auth")) {
                val subjectDid = data.getQueryParameter("subjectDid")
                val error = data.getQueryParameter("error")
                // if no values are passed, it is a successful presentation request
                if (subjectDid?.isEmpty() == true && error?.isEmpty() == true) {
                    onSuccessCallback?.invoke()
                } else if (subjectDid?.isEmpty() == false) {
                    // let the web wallet handle the response
                    WebWallet.handleAuthResponse(data, this)
                } else {
                    // show an error
                }
            }
        }

    }

    /**
     * Step 1:
     * This is the method called to start the verification process
     */
    fun performVerification(verifiedCallback: () -> Unit) {
        // the callback the client app would like to use once the verification process has been completed
        onSuccessCallback = verifiedCallback

        val deeplink = "YourCustomDeeplinkScheme" // this should match the value in the manifest

        val appId = "Your app ID"
        val appKey = "Your app key"
        val workflowId = "Your workflow ID"
        val transactionId = "a unique transaction ID"

        // start the verification
        WebWallet.performVerification(
            appId,
            appKey,
            workflowId,
            transactionId,
            deeplink,
            this, // context
        )
    }
}