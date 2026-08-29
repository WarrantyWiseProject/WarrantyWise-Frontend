package com.warrantywise.app.ui.warranty

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.warrantywise.app.R
import java.util.Calendar
import java.util.Locale

class AddWarrantyFragment : Fragment(R.layout.fragment_add_warranty) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etPurchaseDate =
            view.findViewById<TextInputEditText>(R.id.etPurchaseDate)

        etPurchaseDate.setOnClickListener {
            showDatePicker(etPurchaseDate)
        }
    }

    private fun showDatePicker(etPurchaseDate: TextInputEditText) {

        val calendar = Calendar.getInstance()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->

                val selectedDate = String.format(
                    Locale.getDefault(),
                    "%02d/%02d/%04d",
                    dayOfMonth,
                    month + 1,
                    year
                )

                etPurchaseDate.setText(selectedDate)
            },
            currentYear,
            currentMonth,
            currentDay
        )

        // Prevent selecting a future purchase date
        datePickerDialog.datePicker.maxDate =
            System.currentTimeMillis()

        datePickerDialog.show()
    }
}