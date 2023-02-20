package org.dhis2_haparent.commons.featureconfig.di

interface FeatureConfigComponentProvider {

    fun provideFeatureConfigActivityComponent(): FeatureConfigActivityComponent?
}
