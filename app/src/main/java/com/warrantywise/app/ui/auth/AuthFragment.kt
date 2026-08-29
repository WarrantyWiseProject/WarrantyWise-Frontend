package com.warrantywise.app.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.warrantywise.app.R
import com.warrantywise.app.data.ApiClient
import com.warrantywise.app.data.LoginRequest
import com.warrantywise.app.data.RegisterRequest
import com.warrantywise.app.data.SessionStore
import kotlinx.coroutines.launch

class AuthFragment : Fragment(R.layout.fragment_auth) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!SessionStore.token(requireContext()).isNullOrBlank()) {
            findNavController().navigate(R.id.nav_home)
            return
        }
        var registerMode = false
        val nameLayout = view.findViewById<TextInputLayout>(R.id.layoutAuthName)
        val name = view.findViewById<TextInputEditText>(R.id.etAuthName)
        val email = view.findViewById<TextInputEditText>(R.id.etAuthEmail)
        val password = view.findViewById<TextInputEditText>(R.id.etAuthPassword)
        val submit = view.findViewById<MaterialButton>(R.id.btnAuthSubmit)
        val mode = view.findViewById<MaterialButton>(R.id.btnAuthMode)
        val error = view.findViewById<TextView>(R.id.tvAuthError)

        mode.setOnClickListener {
            registerMode = !registerMode
            nameLayout.visibility = if (registerMode) View.VISIBLE else View.GONE
            submit.text = if (registerMode) "Create account" else "Sign in"
            mode.text = if (registerMode) "Already have an account" else "Create an account"
            error.text = ""
        }
        submit.setOnClickListener {
            lifecycleScope.launch {
                submit.isEnabled = false
                try {
                    val response = if (registerMode) {
                        ApiClient.api.register(RegisterRequest(name.text.toString().trim(), email.text.toString().trim(), password.text.toString()))
                    } else {
                        ApiClient.api.login(LoginRequest(email.text.toString().trim(), password.text.toString()))
                    }
                    SessionStore.saveToken(requireContext(), response.access_token)
                    findNavController().navigate(R.id.nav_home)
                } catch (_: Exception) {
                    error.text = "Could not connect or authenticate. Check your details."
                    submit.isEnabled = true
                }
            }
        }
    }
}
