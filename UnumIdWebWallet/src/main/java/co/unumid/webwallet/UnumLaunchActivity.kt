package co.unumid.webwallet

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import java.net.URLEncoder

class UnumLaunchActivity : AppCompatActivity() {

    val logTag = "UnumLaunchActivity"

    var openedBrowser = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unum_launch)

        var url = ""
        var userCode = ""
        var issuerDid = ""
        var color = ""
        var userEmail = ""

        intent.extras?.let {
            url = it.getString(URL_KEY) ?: ""
            userCode = it.getString(USER_CODE_KEY) ?: ""
            issuerDid = it.getString(ISSUER_DID_KEY) ?: ""
            color = it.getString(COLOR_KEY) ?: ""
            userEmail = it.getString(USER_EMAIL_KEY) ?: ""
        }

        val email = if (userEmail.isNotEmpty()) {
            "&email=${URLEncoder.encode(userEmail, "UTF-8")}"
        } else {
            ""
        }

        if (url.isNotEmpty()) {

            openBrowser(url, color)
        } else {
            val composed =
                "https://wallet.dev-unumid.co/authenticate?userCode=${userCode}&issuer=${issuerDid}${email}"
            openBrowser(composed, color)
        }
    }

    override fun onResume() {
        super.onResume()
        if (openedBrowser) {
            setResult(UNUM_ID_RESULT)
            finish()
        }
    }

    private fun openBrowser(url: String, color: String) {
        log("openBrowser - $url")
        try {
            val builder = CustomTabsIntent.Builder()
            if (color.isNotEmpty()) {
                val colorInt = Color.parseColor(color)
                val defaultColors = CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(colorInt)
                    .build()
                builder.setDefaultColorSchemeParams(defaultColors)
            }
            builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right)
            openedBrowser = true

            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(url))
        } catch (e: Exception) {
            Log.e(logTag, e.localizedMessage, e)
        }

    }

    private fun log(message: String) {
        Log.d(logTag, message)
    }
}