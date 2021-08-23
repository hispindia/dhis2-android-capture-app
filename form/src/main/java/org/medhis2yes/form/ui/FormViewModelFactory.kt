package org.medhis2yes.form.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.medhis2yes.form.data.FormRepository

@Suppress("UNCHECKED_CAST")
class FormViewModelFactory(private val repository: FormRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FormViewModel(repository) as T
    }
}
