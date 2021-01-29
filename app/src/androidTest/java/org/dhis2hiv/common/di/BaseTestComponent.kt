package org.dhis2hiv.common.di

import dagger.Component
import javax.inject.Singleton
import org.dhis2hiv.usescases.BaseTest

@Singleton
@Component(modules = [BaseTestModule::class])
interface BaseTestComponent {

    @Component.Builder
    interface Builder {
        fun baseTestModule(baseTestModule: BaseTestModule): Builder
        fun build(): BaseTestComponent
    }

    fun inject(test: BaseTest)
}
