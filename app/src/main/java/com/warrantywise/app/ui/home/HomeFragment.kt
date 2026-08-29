package com.warrantywise.app.ui.home

import android.os.Bundle
import android.view.View
import com.google.android.material.button.MaterialButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.warrantywise.app.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAddWarranty =
            view.findViewById<MaterialButton>(R.id.btnAddWarranty)

        btnAddWarranty.setOnClickListener {
            findNavController().navigate(R.id.nav_add_warranty)
        }
    }
}