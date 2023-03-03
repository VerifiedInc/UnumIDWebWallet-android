package co.unumid.webwallet

import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import co.unumid.unumidwebauth.WebWallet
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

    var hasError = false
    var errorMessage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
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

    fun showDialog() {
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle("Submit Error")

        val input = EditText(this)
        input.hint = "Enter Error message"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, _ ->
            // do something with error
            dialog.cancel()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}