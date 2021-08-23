package org.medhis2yes.form

import org.medhis2yes.form.data.FormRepository
import org.medhis2yes.form.ui.FormViewModelFactory

object Injector {

    fun provideFormViewModelFactory(repository: FormRepository): FormViewModelFactory {
        return FormViewModelFactory(repository)
    }
}
