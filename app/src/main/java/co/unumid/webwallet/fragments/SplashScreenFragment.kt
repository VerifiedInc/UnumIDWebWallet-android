package co.unumid.webwallet.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.unumid.webwallet.BuildConfig
import co.unumid.webwallet.MainActivity
import co.unumid.webwallet.R


@SuppressLint("CustomSplashScreen")
class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.let {
            if (it.hasError) {
                val action = if (BuildConfig.FLAVOR == "kredita") {
                    R.id.action_splashScreenFragment_to_KSignUpFragment
                } else {
                    R.id.action_splashScreenFragment_to_homeFragment
                }

                findNavController().navigate(action)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val action = if (BuildConfig.FLAVOR == "kredita") {
            R.id.action_splashScreenFragment_to_KSignUpFragment
        } else {
            R.id.action_splashScreenFragment_to_homeFragment
        }

        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(action)
        }, 2000)
    }
}