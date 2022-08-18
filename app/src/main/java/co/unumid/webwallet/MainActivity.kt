package co.unumid.webwallet

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.hyperverge.hyperkyc.HyperKyc
import co.hyperverge.hyperkyc.data.models.HyperKycConfig
import co.hyperverge.hyperkyc.data.models.result.HyperKycResult
import co.unumid.unumidwebauth.WebWallet
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * Setup the ability to kick off the hyperverge SDK
         */
        val hyperKycLauncher = registerForActivityResult(HyperKyc.Contract()) { result ->
            when (result.status) {
                HyperKycResult.Status.SUCCESS -> {
                    Log.d("HyperVerge", "Success")
                    Log.d("HyperVerge", result.toString())

                    /*
                     * Once the hyperverge sdk has finished and given a success call. Hyperverge,
                     * as an issuer, needs to get a user code. This is what will eventually link
                     * the hypervege kyc data to the Unum ID user.
                     */
                    getUserCode(result.toString())
                }
                HyperKycResult.Status.CANCELLED -> {
                    Log.d("HyperVerge", "Cancelled")
                }
                HyperKycResult.Status.FAILURE -> {
                    Log.e("HyperVerge", "FAILURE")
                    Log.e("HyperVerge", result.reason ?: "No Reason given")
                }
            }
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            val config =
                HyperKycConfig(
                    "*******",
                    "**********",
                    "***********",
                    "***********"
                )

            Log.d("HyperVerge", "Starting")
            hyperKycLauncher.launch(config)
        }
    }

    private fun getUserCode(response: String) {
        val body = response.toRequestBody("text/plain".toMediaTypeOrNull())
        lifecycleScope.launch {
            val result = UnumRetrofitClient.getApiInterface().postHyperVergeData(body)
            result.body()?.userCode?.let {


                /*
                 * Once the user code has been created, this information can be passed to the Unum ID
                 * Web Wallet SDK.
                 */
                WebWallet.associate(
                    userCode = it, // the newly created user code
                    issuerDid = "********************", // your issuer DID
                    context = this@MainActivity, // context
//                    userEmail = "user@email.com" // optional email value to that will be pre-filled
//                    toolBarColor = "#202020" // optional toolbar color to help the web browser match the color of the app
                )



            }
        }
    }
}
