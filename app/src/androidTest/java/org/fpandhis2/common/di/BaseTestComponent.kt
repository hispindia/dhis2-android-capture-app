package org.fpandhis2.common.di

import dagger.Component
import javax.inject.Singleton
import org.fpandhis2.usescases.BaseTest

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
