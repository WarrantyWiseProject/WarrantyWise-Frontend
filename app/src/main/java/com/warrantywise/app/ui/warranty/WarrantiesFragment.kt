package com.warrantywise.app.ui.warranty

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.warrantywise.app.R
import com.warrantywise.app.data.ApiClient
import kotlinx.coroutines.launch

class WarrantiesFragment : Fragment(R.layout.fragment_warranties) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val output = view.findViewById<android.widget.TextView>(R.id.tvWarranties)
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val items = ApiClient.api.getAllItems()
                output.text = if (items.isEmpty()) "No warranties yet" else items.joinToString("\n\n") {
                    "${it.item_name}\nPurchased: ${it.date_purchased}\nWarranty ends: ${it.warranty_date}"
                }
            } catch (_: Exception) {
                output.text = "Unable to load warranties. Check your login and connection."
            }
        }
    }
}
