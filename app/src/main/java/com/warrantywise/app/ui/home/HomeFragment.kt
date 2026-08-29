package com.warrantywise.app.ui.home

import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.lifecycleScope
import com.warrantywise.app.R
import com.warrantywise.app.data.ApiClient
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddWarranty =
            view.findViewById<MaterialButton>(R.id.btnAddWarranty)

        btnAddWarranty.setOnClickListener {
            findNavController().navigate(R.id.nav_add_warranty)
        }

        val total = view.findViewById<android.widget.TextView>(R.id.tvTotalWarranties)
        val active = view.findViewById<android.widget.TextView>(R.id.tvActiveCount)
        val expiring = view.findViewById<android.widget.TextView>(R.id.tvExpiringCount)
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val items = ApiClient.api.getAllItems()
                total.text = items.size.toString()
                val today = Date()
                val activeItems = items.filter { parseDate(it.warranty_date)?.after(today) == true }
                val expiringItems = activeItems.filter {
                    val days = TimeUnit.MILLISECONDS.toDays(parseDate(it.warranty_date)!!.time - today.time)
                    days in 0..30
                }
                active.text = "${activeItems.size} Active"
                expiring.text = "${expiringItems.size} Expiring Soon"
            } catch (_: Exception) {
                total.text = "—"
                active.text = "Sign in to load"
                expiring.text = ""
            }
        }
    }

    private fun parseDate(value: String): Date? =
        runCatching { SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(value) }.getOrNull()
}
