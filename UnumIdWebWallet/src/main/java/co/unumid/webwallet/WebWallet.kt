@file:Suppress("unused")

package co.unumid.webwallet

import android.content.Context
import android.content.Intent

object WebWallet {

    /**
     * Method for launching the web wallet and performing the user DID association flow.
     * @param userCode The issuer created user code.
     * @param issuerDid The current issuer DID.
     * @param userEmail An optional value of the current user email to pre-fil the email input.
     * @param context A provided context to launch the chrome activity.
     * @param toolBarColor An optional value to set the color of the chrome tool bar. This should be in hex form "#202020".
     */
    fun associate(
        userCode: String,
        issuerDid: String,
        userEmail: String = "",
        context: Context,
        toolBarColor: String = ""
    ) {
        startAssociationActivity(userCode, issuerDid, userEmail, context, toolBarColor)
    }

    /**
     * Method for launching a provided url in a chrome custom tab.
     * @param url The url to be launched.
     * @param context A provided context to launch the chrome activity.
     * @param toolBarColor An optional value to set the color of the chrome tool bar. This should be in hex form "#202020".
     */
    fun launch(url: String, context: Context, toolBarColor: String = "") {
        val intent = Intent(context, UnumLaunchActivity::class.java)
        intent.putExtra(URL_KEY, url)
        intent.putExtra(COLOR_KEY, toolBarColor)
        context.startActivity(intent)
    }

    private fun startAssociationActivity(
        userCode: String,
        issuerDid: String,
        userEmail: String = "",
        context: Context,
        toolBarColor: String = ""
    ) {
        val intent = Intent(context, UnumLaunchActivity::class.java)
        intent.putExtra(USER_CODE_KEY, userCode)
        intent.putExtra(ISSUER_DID_KEY, issuerDid)
        intent.putExtra(COLOR_KEY, toolBarColor)
        intent.putExtra(USER_EMAIL_KEY, userEmail)
        context.startActivity(intent)
    }


}