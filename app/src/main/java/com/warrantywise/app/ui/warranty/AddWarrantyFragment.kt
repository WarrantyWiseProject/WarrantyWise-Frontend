package com.warrantywise.app.ui.warranty

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.warrantywise.app.R
import com.warrantywise.app.data.ApiClient
import com.warrantywise.app.data.ItemCreate
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

class AddWarrantyFragment : Fragment(R.layout.fragment_add_warranty) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etPurchaseDate =
            view.findViewById<TextInputEditText>(R.id.etPurchaseDate)
        val etProductName = view.findViewById<TextInputEditText>(R.id.etProductName)
        val etWarrantyDuration = view.findViewById<TextInputEditText>(R.id.etWarrantyDuration)
        val saveButton = view.findViewById<MaterialButton>(R.id.btnSaveWarranty)
        val productLayout = view.findViewById<TextInputLayout>(R.id.layoutProductName)
        val purchaseLayout = view.findViewById<TextInputLayout>(R.id.layoutPurchaseDate)
        val durationLayout = view.findViewById<TextInputLayout>(R.id.layoutWarrantyDuration)

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
                    "%02d-%02d-%04d",
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

        saveButton.setOnClickListener {
            val name = etProductName.text?.toString()?.trim().orEmpty()
            val purchaseDate = etPurchaseDate.text?.toString()?.trim().orEmpty()
            val duration = etWarrantyDuration.text?.toString()?.trim().orEmpty()
            productLayout.error = if (name.isEmpty()) "Enter a product name" else null
            purchaseLayout.error = if (purchaseDate.isEmpty()) "Select a purchase date" else null
            durationLayout.error = if (duration.isEmpty()) "Enter warranty duration" else null
            if (name.isEmpty() || purchaseDate.isEmpty() || duration.isEmpty()) return@setOnClickListener

            val warrantyDate = calculateWarrantyDate(purchaseDate, duration)
            lifecycleScope.launch {
                saveButton.isEnabled = false
                try {
                    ApiClient.api.createItem(ItemCreate(name, purchaseDate, warrantyDate))
                    findNavController().popBackStack()
                } catch (_: Exception) {
                    saveButton.isEnabled = true
                    durationLayout.error = "Could not save. Check your login and connection."
                }
            }
        }
    }

    private fun calculateWarrantyDate(purchaseDate: String, duration: String): String {
        val months = Regex("(\\d+)").find(duration)?.groupValues?.get(1)?.toLongOrNull() ?: 12L
        val parts = purchaseDate.split("-")
        val calendar = Calendar.getInstance().apply {
            set(parts[2].toInt(), parts[1].toInt() - 1, parts[0].toInt())
            add(Calendar.MONTH, months.toInt())
        }
        return String.format(Locale.US, "%02d-%02d-%04d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR))
    }
