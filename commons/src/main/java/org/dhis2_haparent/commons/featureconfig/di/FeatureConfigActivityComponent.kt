package org.dhis2_haparent.commons.featureconfig.di

import dagger.Subcomponent
import org.dhis2_haparent.commons.di.dagger.PerActivity
import org.dhis2_haparent.commons.featureconfig.ui.FeatureConfigView

@PerActivity
@Subcomponent(modules = [FeatureConfigActivityModule::class])
interface FeatureConfigActivityComponent {
    fun inject(featureConfigView: FeatureConfigView)
}
